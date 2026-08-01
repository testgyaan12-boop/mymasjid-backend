package com.noormasjid.controller;

import com.noormasjid.dto.request.SetMasjidRequest;
import com.noormasjid.dto.response.MasjidResponse;
import com.noormasjid.dto.response.MessageResponse;
import com.noormasjid.security.UserDetailsImpl;
import com.noormasjid.service.MasjidService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/masjids")
public class MasjidController {

    private final MasjidService masjidService;

    public MasjidController(MasjidService masjidService) {
        this.masjidService = masjidService;
    }

    @GetMapping
    public ResponseEntity<List<MasjidResponse>> search(@RequestParam String q) {
        return ResponseEntity.ok(masjidService.search(q));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MasjidResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(masjidService.getMasjidById(id));
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<MessageResponse> joinMasjid(
            @AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable Long id) {
        masjidService.joinMasjid(user.getId(), id);
        return ResponseEntity.ok(new MessageResponse("Joined masjid as MEMBER"));
    }
}
