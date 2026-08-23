package com.noormasjid.controller;

import com.noormasjid.dto.request.AssignRoleRequest;
import com.noormasjid.dto.response.MessageResponse;
import com.noormasjid.service.MasjidService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/masjids/{masjidId}")
public class MasjidAdminController {

    private final MasjidService masjidService;

    public MasjidAdminController(MasjidService masjidService) {
        this.masjidService = masjidService;
    }

    @PutMapping("/users/{userId}/role")
    @PreAuthorize("@masjidSecurity.hasPermission('member:manage')")
    public ResponseEntity<MessageResponse> assignRole(
            @PathVariable Long masjidId,
            @PathVariable Long userId,
            @Valid @RequestBody AssignRoleRequest request) {
        masjidService.assignRole(masjidId, userId, request.getRoleName());
        return ResponseEntity.ok(new MessageResponse("Role assigned successfully"));
    }
}
