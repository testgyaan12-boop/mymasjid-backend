package com.noormasjid.controller;

import com.noormasjid.dto.request.LoginRequest;
import com.noormasjid.dto.request.RefreshTokenRequest;
import com.noormasjid.dto.request.SignupRequest;
import com.noormasjid.dto.request.UpdateProfileRequest;
import com.noormasjid.dto.response.AuthResponse;
import com.noormasjid.dto.response.UserProfileResponse;
import com.noormasjid.security.UserDetailsImpl;
import com.noormasjid.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refresh(request));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> profile(@AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(authService.getProfile(user.getId()));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateProfile(
            @AuthenticationPrincipal UserDetailsImpl user,
            @Valid @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(authService.updateProfile(user.getId(), request));
    }
}
