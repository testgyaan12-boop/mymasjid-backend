package com.noormasjid.controller;

import com.noormasjid.dto.request.RegisterDeviceRequest;
import com.noormasjid.dto.request.SendNotificationRequest;
import com.noormasjid.entity.auth.User;
import com.noormasjid.entity.masjid.Masjid;
import com.noormasjid.entity.notification.AppNotification;
import com.noormasjid.entity.notification.DeviceToken;
import com.noormasjid.repository.AppNotificationRepository;
import com.noormasjid.repository.DeviceTokenRepository;
import com.noormasjid.repository.MasjidRepository;
import com.noormasjid.repository.UserRepository;
import com.noormasjid.security.SecurityUtil;
import com.noormasjid.service.PushNotificationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final DeviceTokenRepository deviceTokenRepository;
    private final UserRepository userRepository;
    private final MasjidRepository masjidRepository;
    private final PushNotificationService pushNotificationService;
    private final AppNotificationRepository appNotificationRepository;

    public NotificationController(DeviceTokenRepository deviceTokenRepository,
                                  UserRepository userRepository,
                                  MasjidRepository masjidRepository,
                                  PushNotificationService pushNotificationService,
                                  AppNotificationRepository appNotificationRepository) {
        this.deviceTokenRepository = deviceTokenRepository;
        this.userRepository = userRepository;
        this.masjidRepository = masjidRepository;
        this.pushNotificationService = pushNotificationService;
        this.appNotificationRepository = appNotificationRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(
            @Valid @RequestBody RegisterDeviceRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Not authenticated"));
        }
        User user = userRepository.findById(userId).orElseThrow();
        Masjid masjid = masjidRepository.findById(request.getMasjidId())
                .orElseThrow(() -> new IllegalArgumentException("Masjid not found"));

        DeviceToken token = deviceTokenRepository.findByToken(request.getToken())
                .orElse(new DeviceToken());
        token.setToken(request.getToken());
        token.setUser(user);
        token.setMasjid(masjid);
        token.setPlatform(request.getPlatform() == null ? "android" : request.getPlatform());
        deviceTokenRepository.save(token);
        return ResponseEntity.ok(Map.of("status", "registered"));
    }

    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> send(
            @Valid @RequestBody SendNotificationRequest request) {
        String type = request.getType() == null ? "alert" : request.getType();

        if (request.getUserId() != null) {
            pushNotificationService.sendToUser(request.getUserId(), request.getTitle(),
                    request.getMessage(), type);
        } else if (request.getMasjidId() != null) {
            pushNotificationService.sendToMasjid(request.getMasjidId(), request.getTitle(),
                    request.getMessage(), type);
        } else {
            pushNotificationService.sendToAll(request.getTitle(), request.getMessage(), type);
        }
        return ResponseEntity.ok(Map.of("status", "sent"));
    }

    @PostMapping("/unregister")
    public ResponseEntity<Map<String, String>> unregister(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        if (token != null && !token.isBlank()) {
            deviceTokenRepository.deleteByToken(token);
        }
        return ResponseEntity.ok(Map.of("status", "unregistered"));
    }

    @GetMapping
    public List<Map<String, Object>> list(@RequestParam Long masjidId) {
        return appNotificationRepository
                .findByMasjidIdAndIsDeletedOrderByCreatedAtDesc(masjidId, 0)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @GetMapping("/unread-count")
    public ResponseEntity<Map<String, Long>> unreadCount(@RequestParam Long masjidId) {
        long count = appNotificationRepository
                .countByMasjidIdAndIsReadFalseAndIsDeleted(masjidId, 0);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @PostMapping("/read-all")
    public ResponseEntity<Map<String, String>> markAllRead(@RequestParam Long masjidId) {
        appNotificationRepository.markAllRead(masjidId);
        return ResponseEntity.ok(Map.of("status", "read"));
    }

    @PostMapping("/{id}/read")
    public ResponseEntity<Map<String, String>> markRead(@PathVariable Long id) {
        appNotificationRepository.findById(id).ifPresent(n -> {
            n.setIsRead(true);
            n.setReadAt(java.time.LocalDateTime.now());
            appNotificationRepository.save(n);
        });
        return ResponseEntity.ok(Map.of("status", "read"));
    }

    private Map<String, Object> toDto(AppNotification n) {
        return Map.of(
                "id", n.getId(),
                "title", n.getTitle() == null ? "" : n.getTitle(),
                "message", n.getMessage() == null ? "" : n.getMessage(),
                "type", n.getType() == null ? "alert" : n.getType(),
                "isRead", n.getIsRead() == null ? false : n.getIsRead(),
                "createdAt", n.getCreatedAt() == null ? "" : n.getCreatedAt().toString()
        );
    }
}