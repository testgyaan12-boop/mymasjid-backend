package com.noormasjid.controller;

import com.noormasjid.entity.sunnah.ActiveSunnahBroadcast;
import com.noormasjid.entity.sunnah.Sunnah;
import com.noormasjid.entity.sunnah.UserSavedSunnah;
import com.noormasjid.security.UserDetailsImpl;
import com.noormasjid.service.SunnahService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SunnahController {

    private final SunnahService sunnahService;

    public SunnahController(SunnahService sunnahService) {
        this.sunnahService = sunnahService;
    }

    @GetMapping("/cms/sunnahs")
    public ResponseEntity<List<Sunnah>> getSunnahs(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId) {
        return ResponseEntity.ok(sunnahService.getSunnahs(masjidId));
    }

    @PostMapping("/cms/sunnahs")
    public ResponseEntity<Map<String, String>> createSunnah(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId,
            @RequestBody Sunnah sunnah) {
        sunnahService.saveSunnah(masjidId, sunnah);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    @DeleteMapping("/cms/sunnahs/{id}")
    public ResponseEntity<Map<String, String>> deleteSunnah(@PathVariable Long id) {
        sunnahService.deleteSunnah(id);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    @GetMapping("/cms/sunnah-broadcast")
    public ResponseEntity<ActiveSunnahBroadcast> getActiveBroadcast(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId) {
        ActiveSunnahBroadcast broadcast = sunnahService.getActiveBroadcast(masjidId);
        if (broadcast == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(broadcast);
    }

    @PostMapping("/cms/sunnah-broadcast")
    public ResponseEntity<Map<String, String>> setActiveBroadcast(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId,
            @RequestBody Map<String, Long> body) {
        sunnahService.setActiveBroadcast(masjidId, body.get("sunnahId"));
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    @GetMapping("/user/saved-sunnahs")
    public ResponseEntity<List<UserSavedSunnah>> getSavedSunnahs(
            @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(sunnahService.getSavedSunnahs(user.getId()));
    }

    @PostMapping("/user/saved-sunnahs/{sunnahId}")
    public ResponseEntity<?> saveSunnah(
            @AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable Long sunnahId) {
        sunnahService.saveSunnahForUser(user.getId(), sunnahId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user/saved-sunnahs/{sunnahId}")
    public ResponseEntity<?> unsaveSunnah(
            @AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable Long sunnahId) {
        sunnahService.unsaveSunnah(user.getId(), sunnahId);
        return ResponseEntity.ok().build();
    }
}
