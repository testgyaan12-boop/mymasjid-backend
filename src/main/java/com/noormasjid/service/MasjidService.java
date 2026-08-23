package com.noormasjid.service;

import com.noormasjid.dto.response.MasjidResponse;
import com.noormasjid.entity.auth.Role;
import com.noormasjid.entity.auth.User;
import com.noormasjid.entity.auth.UserMasjidRole;
import com.noormasjid.entity.masjid.Masjid;
import com.noormasjid.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MasjidService {

    private final MasjidRepository masjidRepository;
    private final UserRepository userRepository;
    private final UserMasjidRoleRepository userMasjidRoleRepository;
    private final RoleRepository roleRepository;

    public MasjidService(MasjidRepository masjidRepository,
                         UserRepository userRepository,
                         UserMasjidRoleRepository userMasjidRoleRepository,
                         RoleRepository roleRepository) {
        this.masjidRepository = masjidRepository;
        this.userRepository = userRepository;
        this.userMasjidRoleRepository = userMasjidRoleRepository;
        this.roleRepository = roleRepository;
    }

    public List<MasjidResponse> search(String query) {
        String trimmed = query == null ? "" : query.trim();
        if (trimmed.isEmpty()) {
            return masjidRepository.findByIsDeleted(0).stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
        }

        java.util.LinkedHashSet<Masjid> results = new java.util.LinkedHashSet<>();
        boolean isNumeric = trimmed.chars().allMatch(Character::isDigit);
        if (isNumeric) {
            results.addAll(masjidRepository.findByPincodeAndIsDeleted(trimmed, 0));
        }
        results.addAll(masjidRepository.findByNameContainingIgnoreCaseAndIsDeleted(trimmed, 0));

        return results.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private MasjidResponse toResponse(Masjid m) {
        return MasjidResponse.builder()
                .id(m.getId())
                .name(m.getName())
                .address(m.getAddress())
                .pincode(m.getPincode())
                .city(m.getCity())
                .state(m.getState())
                .country(m.getCountry())
                .phone(m.getPhone())
                .email(m.getEmail())
                .logo(m.getLogo())
                .website(m.getWebsite())
                .build();
    }

    public MasjidResponse getMasjidById(Long masjidId) {
        Masjid masjid = masjidRepository.findById(masjidId)
                .orElseThrow(() -> new IllegalArgumentException("Masjid not found"));
        return toResponse(masjid);
    }

    @Transactional
    public void setCurrentMasjid(Long userId, Long masjidId) {
        Masjid masjid = masjidRepository.findById(masjidId)
                .orElseThrow(() -> new IllegalArgumentException("Masjid not found"));
        if (!userMasjidRoleRepository.existsByUserIdAndMasjidId(userId, masjidId)) {
            joinMasjid(userId, masjidId);
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setCurrentMasjidId(masjidId);
        userRepository.save(user);
    }

    public List<MasjidResponse> getUserMasjids(Long userId) {
        return userMasjidRoleRepository.findByUserId(userId).stream()
                .map(umr -> {
                    Masjid m = umr.getMasjid();
                    return MasjidResponse.builder()
                            .id(m.getId())
                            .name(m.getName())
                            .address(m.getAddress())
                            .pincode(m.getPincode())
                            .city(m.getCity())
                            .state(m.getState())
                            .country(m.getCountry())
                            .phone(m.getPhone())
                            .email(m.getEmail())
                            .logo(m.getLogo())
                            .website(m.getWebsite())
                            .userRole(umr.getRole().getName())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void joinMasjid(Long userId, Long masjidId) {
        if (userMasjidRoleRepository.existsByUserIdAndMasjidId(userId, masjidId)) {
            throw new IllegalArgumentException("Already a member of this masjid");
        }

        Role memberRole = roleRepository.findByName("MEMBER")
                .orElseThrow(() -> new IllegalArgumentException("Default MEMBER role not found"));

        UserMasjidRole umr = new UserMasjidRole();
        umr.setUser(userRepository.getReferenceById(userId));
        umr.setMasjid(masjidRepository.getReferenceById(masjidId));
        umr.setRole(memberRole);
        userMasjidRoleRepository.save(umr);
    }

    @Transactional
    public void assignRole(Long masjidId, Long userId, String roleName) {
        Role role = roleRepository.findByName(roleName.toUpperCase())
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));

        UserMasjidRole umr = userMasjidRoleRepository
                .findByUserIdAndMasjidId(userId, masjidId)
                .orElseThrow(() -> new IllegalArgumentException("User is not a member of this masjid"));

        umr.setRole(role);
        userMasjidRoleRepository.save(umr);
    }

    public String getUserRole(Long userId, Long masjidId) {
        return userMasjidRoleRepository
                .findRoleNameByUserIdAndMasjidId(userId, masjidId)
                .orElse(null);
    }
}
