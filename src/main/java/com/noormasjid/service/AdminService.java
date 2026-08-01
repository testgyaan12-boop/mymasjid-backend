package com.noormasjid.service;

import com.noormasjid.entity.auth.*;
import com.noormasjid.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final UserMasjidRoleRepository userMasjidRoleRepository;

    public AdminService(RoleRepository roleRepository,
                        PermissionRepository permissionRepository,
                        RolePermissionRepository rolePermissionRepository,
                        UserMasjidRoleRepository userMasjidRoleRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.userMasjidRoleRepository = userMasjidRoleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id).orElseThrow();
        role.setIsDeleted(1);
        roleRepository.save(role);
    }

    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    public Permission createPermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Transactional
    public void assignPermissionToRole(Long roleId, Long permissionId) {
        RolePermission rp = new RolePermission();
        rp.setRole(roleRepository.getReferenceById(roleId));
        rp.setPermission(permissionRepository.getReferenceById(permissionId));
        rolePermissionRepository.save(rp);
    }

    public List<String> getRolePermissions(Long roleId) {
        return rolePermissionRepository.findPermissionNamesByRoleId(roleId);
    }

    @Transactional
    public void assignUserRole(Long masjidId, Long userId, String roleName) {
        Role role = roleRepository.findByName(roleName.toUpperCase())
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));

        UserMasjidRole umr = userMasjidRoleRepository
                .findByUserIdAndMasjidId(userId, masjidId)
                .orElseThrow(() -> new IllegalArgumentException("User is not a member of this masjid"));

        umr.setRole(role);
        userMasjidRoleRepository.save(umr);
    }
}
