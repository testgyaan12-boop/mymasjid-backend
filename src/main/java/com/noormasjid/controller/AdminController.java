package com.noormasjid.controller;

import com.noormasjid.entity.auth.Permission;
import com.noormasjid.entity.auth.Role;
import com.noormasjid.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(adminService.getAllRoles());
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        return ResponseEntity.ok(adminService.createRole(role));
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        adminService.deleteRole(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<Permission>> getAllPermissions() {
        return ResponseEntity.ok(adminService.getAllPermissions());
    }

    @PostMapping("/permissions")
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission) {
        return ResponseEntity.ok(adminService.createPermission(permission));
    }

    @PostMapping("/roles/{roleId}/permissions/{permissionId}")
    public ResponseEntity<?> assignPermissionToRole(
            @PathVariable Long roleId,
            @PathVariable Long permissionId) {
        adminService.assignPermissionToRole(roleId, permissionId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/roles/{roleId}/permissions")
    public ResponseEntity<List<String>> getRolePermissions(@PathVariable Long roleId) {
        return ResponseEntity.ok(adminService.getRolePermissions(roleId));
    }
}
