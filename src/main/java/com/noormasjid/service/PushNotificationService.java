package com.noormasjid.service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.*;
import com.noormasjid.entity.notification.DeviceToken;
import com.noormasjid.repository.DeviceTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PushNotificationService {

    private static final Logger log = LoggerFactory.getLogger(PushNotificationService.class);

    private final DeviceTokenRepository deviceTokenRepository;

    public PushNotificationService(DeviceTokenRepository deviceTokenRepository) {
        this.deviceTokenRepository = deviceTokenRepository;
    }

    public void sendToMasjid(Long masjidId, String title, String body, String type) {
        try {
            if (!isFirebaseReady()) return;
            List<DeviceToken> tokens = deviceTokenRepository.findByMasjidIdAndIsDeleted(masjidId, 0);
            sendToTokens(tokens, title, body, type, masjidId);
        } catch (FirebaseMessagingException e) {
            log.error("FCM push failed for masjid {}: {}", masjidId, e.getMessage());
        }
    }

    public void sendToUser(Long userId, String title, String body, String type) {
        try {
            if (!isFirebaseReady()) return;
            List<DeviceToken> tokens = deviceTokenRepository.findByUserIdAndIsDeleted(userId, 0);
            sendToTokens(tokens, title, body, type, null);
        } catch (FirebaseMessagingException e) {
            log.error("FCM push failed for user {}: {}", userId, e.getMessage());
        }
    }

    public void sendToAll(String title, String body, String type) {
        try {
            if (!isFirebaseReady()) return;
            List<DeviceToken> tokens = deviceTokenRepository.findAllByIsDeleted(0);
            sendToTokens(tokens, title, body, type, null);
        } catch (FirebaseMessagingException e) {
            log.error("FCM push failed (broadcast): {}", e.getMessage());
        }
    }

    private boolean isFirebaseReady() {
        if (FirebaseApp.getApps().isEmpty()) {
            log.debug("Firebase not initialized - skipping push");
            return false;
        }
        return true;
    }

    private void sendToTokens(List<DeviceToken> tokens, String title, String body, String type, Long masjidId)
            throws FirebaseMessagingException {
        if (tokens.isEmpty()) return;

        List<String> registrationTokens = tokens.stream()
                .map(DeviceToken::getToken)
                .filter(t -> t != null && !t.isBlank())
                .distinct()
                .toList();
        if (registrationTokens.isEmpty()) return;

        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();
        MulticastMessage.Builder messageBuilder = MulticastMessage.builder()
                .setNotification(notification)
                .putData("type", type == null ? "alert" : type);
        if (masjidId != null) {
            messageBuilder.putData("masjidId", String.valueOf(masjidId));
        }
        MulticastMessage message = messageBuilder.addAllTokens(registrationTokens).build();

        BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);
        log.info("FCM push sent: success={}, failure={}", response.getSuccessCount(), response.getFailureCount());
    }
}