package com.noormasjid.controller;

import com.noormasjid.entity.tasbih.CustomAdhkar;
import com.noormasjid.entity.tasbih.TasbihLog;
import com.noormasjid.security.UserDetailsImpl;
import com.noormasjid.service.TasbihService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class TasbihController {

    private final TasbihService tasbihService;

    public TasbihController(TasbihService tasbihService) {
        this.tasbihService = tasbihService;
    }

    @GetMapping("/tasbih-logs")
    public ResponseEntity<List<TasbihLog>> getLogs(@AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(tasbihService.getLogs(user.getId()));
    }

    @PostMapping("/tasbih-logs")
    public ResponseEntity<TasbihLog> saveLog(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestBody TasbihLog log) {
        return ResponseEntity.ok(tasbihService.saveLog(user.getId(), log));
    }

    @GetMapping("/adhkars")
    public ResponseEntity<List<CustomAdhkar>> getAdhkars(
            @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(tasbihService.getAdhkars(user.getId()));
    }

    @PostMapping("/adhkars")
    public ResponseEntity<CustomAdhkar> saveAdhkar(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestBody CustomAdhkar adhkar) {
        return ResponseEntity.ok(tasbihService.saveAdhkar(user.getId(), adhkar));
    }

    @DeleteMapping("/adhkars/{id}")
    public ResponseEntity<?> deleteAdhkar(@PathVariable Long id) {
        tasbihService.deleteAdhkar(id);
        return ResponseEntity.ok().build();
    }
}
