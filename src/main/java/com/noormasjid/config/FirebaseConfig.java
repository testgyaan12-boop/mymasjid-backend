package com.noormasjid.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class FirebaseConfig {

    private static final Logger log = LoggerFactory.getLogger(FirebaseConfig.class);

    @Value("${app.firebase.credentials:}")
    private String credentialsJson;

    @Value("${app.firebase.credentials-file:}")
    private String credentialsFile;

    @PostConstruct
    public void init() {
        try {
            String json = resolveCredentialsJson();
            if (json == null || json.isBlank()) {
                log.warn("FIREBASE_CREDENTIALS_JSON not set and no credentials file configured - push notifications disabled");
                return;
            }
            try (InputStream in = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(in))
                        .build();
                if (FirebaseApp.getApps().isEmpty()) {
                    FirebaseApp.initializeApp(options);
                    log.info("Firebase Admin SDK initialized");
                }
            }
        } catch (Exception e) {
            log.error("Failed to initialize Firebase Admin SDK: {}", e.getMessage());
        }
    }

    private String resolveCredentialsJson() throws Exception {
        if (credentialsJson != null && !credentialsJson.isBlank()) {
            return credentialsJson;
        }
        if (credentialsFile != null && !credentialsFile.isBlank()) {
            Path path = Path.of(credentialsFile);
            if (Files.exists(path)) {
                return Files.readString(path, StandardCharsets.UTF_8);
            }
            log.warn("Firebase credentials file not found: {}", credentialsFile);
        }
        return null;
    }
}
