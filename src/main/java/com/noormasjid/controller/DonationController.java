package com.noormasjid.controller;

import com.noormasjid.entity.donation.Donation;
import com.noormasjid.security.UserDetailsImpl;
import com.noormasjid.service.DonationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @GetMapping("/donations")
    public ResponseEntity<List<Donation>> getDonations(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId) {
        return ResponseEntity.ok(donationService.getDonations(masjidId));
    }

    @PostMapping("/donations")
    public ResponseEntity<Map<String, String>> createDonation(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId,
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestBody Donation donation) {
        Long userId = user != null ? user.getId() : null;
        donationService.createDonation(masjidId, userId, donation);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}
