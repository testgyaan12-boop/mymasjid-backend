package com.noormasjid.controller;

import com.noormasjid.dto.request.SetMasjidRequest;
import com.noormasjid.dto.response.MasjidResponse;
import com.noormasjid.dto.response.MessageResponse;
import com.noormasjid.security.UserDetailsImpl;
import com.noormasjid.service.MasjidService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserMasjidController {

    private final MasjidService masjidService;

    public UserMasjidController(MasjidService masjidService) {
        this.masjidService = masjidService;
    }

    @GetMapping("/masjids")
    public ResponseEntity<List<MasjidResponse>> getUserMasjids(
            @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(masjidService.getUserMasjids(user.getId()));
    }

    @PostMapping("/masjid")
    public ResponseEntity<MessageResponse> setCurrentMasjid(
            @AuthenticationPrincipal UserDetailsImpl user,
            @Valid @RequestBody SetMasjidRequest request) {
        masjidService.setCurrentMasjid(user.getId(), request.getMasjidId());
        return ResponseEntity.ok(new MessageResponse("Current masjid updated"));
    }

    @GetMapping("/masjid/{masjidId}/role")
    public ResponseEntity<?> getRoleInMasjid(
            @AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable Long masjidId) {
        String role = masjidService.getUserRole(user.getId(), masjidId);
        if (role == null) {
            return ResponseEntity.ok(new MessageResponse("Not a member"));
        }
        return ResponseEntity.ok(new java.util.HashMap<>(java.util.Map.of("role", role)));
    }
}
