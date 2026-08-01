package com.noormasjid.service;

import com.noormasjid.dto.request.LoginRequest;
import com.noormasjid.dto.request.RefreshTokenRequest;
import com.noormasjid.dto.request.SignupRequest;
import com.noormasjid.dto.request.UpdateProfileRequest;
import com.noormasjid.dto.response.AuthResponse;
import com.noormasjid.dto.response.UserProfileResponse;
import com.noormasjid.entity.auth.SystemRole;
import com.noormasjid.entity.auth.User;
import com.noormasjid.entity.masjid.Masjid;
import com.noormasjid.repository.MasjidRepository;
import com.noormasjid.repository.UserRepository;
import com.noormasjid.security.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final MasjidRepository masjidRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthService(UserRepository userRepository,
                       MasjidRepository masjidRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.masjidRepository = masjidRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    public AuthResponse signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setSystemRole(SystemRole.USER);
        user = userRepository.save(user);

        return buildAuthResponse(user);
    }

    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid email or password");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        return buildAuthResponse(user);
    }

    public AuthResponse refresh(RefreshTokenRequest request) {
        if (!jwtProvider.validateToken(request.getRefreshToken())) {
            throw new IllegalArgumentException("Invalid or expired refresh token");
        }

        String email = jwtProvider.getEmailFromToken(request.getRefreshToken());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return buildAuthResponse(user);
    }

    public UserProfileResponse getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String masjidName = null;
        if (user.getCurrentMasjidId() != null) {
            masjidName = masjidRepository.findById(user.getCurrentMasjidId())
                    .map(Masjid::getName).orElse(null);
        }

        return UserProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .avatar(user.getAvatar())
                .systemRole(user.getSystemRole().name())
                .currentMasjidId(user.getCurrentMasjidId())
                .currentMasjidName(masjidName)
                .build();
    }

    public UserProfileResponse updateProfile(Long userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (request.getName() != null) user.setName(request.getName());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getAvatar() != null) user.setAvatar(request.getAvatar());

        user = userRepository.save(user);

        String masjidName = null;
        if (user.getCurrentMasjidId() != null) {
            masjidName = masjidRepository.findById(user.getCurrentMasjidId())
                    .map(Masjid::getName).orElse(null);
        }

        return UserProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .avatar(user.getAvatar())
                .systemRole(user.getSystemRole().name())
                .currentMasjidId(user.getCurrentMasjidId())
                .currentMasjidName(masjidName)
                .build();
    }

    private AuthResponse buildAuthResponse(User user) {
        String accessToken = jwtProvider.generateAccessToken(user.getId(), user.getEmail());
        String refreshToken = jwtProvider.generateRefreshToken(user.getId(), user.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .systemRole(user.getSystemRole().name())
                .currentMasjidId(user.getCurrentMasjidId())
                .build();
    }
}
