package com.noormasjid.security;

import com.noormasjid.repository.UserMasjidRoleRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("masjidSecurity")
public class MasjidSecurity {

    private final UserMasjidRoleRepository userMasjidRoleRepository;

    public MasjidSecurity(UserMasjidRoleRepository userMasjidRoleRepository) {
        this.userMasjidRoleRepository = userMasjidRoleRepository;
    }

    public boolean hasPermission(String permissionName) {
        Long userId = SecurityUtil.getCurrentUserId();
        Long masjidId = SecurityUtil.getCurrentMasjidId();
        if (userId == null || masjidId == null) return false;

        List<String> permissions = userMasjidRoleRepository
                .findPermissionNamesByUserIdAndMasjidId(userId, masjidId);
        return permissions.contains(permissionName);
    }

    public boolean hasAnyRole(String... roleNames) {
        Long userId = SecurityUtil.getCurrentUserId();
        Long masjidId = SecurityUtil.getCurrentMasjidId();
        if (userId == null || masjidId == null) return false;

        String userRole = userMasjidRoleRepository
                .findRoleNameByUserIdAndMasjidId(userId, masjidId).orElse(null);
        if (userRole == null) return false;

        for (String role : roleNames) {
            if (role.equalsIgnoreCase(userRole)) return true;
        }
        return false;
    }

    public boolean hasRole(String roleName) {
        return hasAnyRole(roleName);
    }
}
