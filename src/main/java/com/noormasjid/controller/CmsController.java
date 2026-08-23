package com.noormasjid.controller;

import com.noormasjid.entity.cms.*;
import com.noormasjid.service.CmsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/cms")
public class CmsController {

    private final CmsService cmsService;

    public CmsController(CmsService cmsService) {
        this.cmsService = cmsService;
    }

    @GetMapping("/branding")
    public ResponseEntity<Branding> getBranding(@RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId) {
        return ResponseEntity.ok(cmsService.getBranding(masjidId));
    }

    @PutMapping("/branding")
    public ResponseEntity<Map<String, String>> saveBranding(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId,
            @RequestBody Branding branding) {
        cmsService.saveBranding(masjidId, branding);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    @GetMapping("/home-announcement")
    public ResponseEntity<HomeAnnouncement> getHomeAnnouncement(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId) {
        return ResponseEntity.ok(cmsService.getHomeAnnouncement(masjidId));
    }

    @PutMapping("/home-announcement")
    public ResponseEntity<Map<String, String>> saveHomeAnnouncement(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId,
            @RequestBody HomeAnnouncement ha) {
        cmsService.saveHomeAnnouncement(masjidId, ha);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    @GetMapping("/prayer-times")
    public ResponseEntity<List<PrayerTime>> getPrayerTimes(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId) {
        return ResponseEntity.ok(cmsService.getPrayerTimes(masjidId));
    }

    @PutMapping("/prayer-times")
    public ResponseEntity<Map<String, String>> savePrayerTimes(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId,
            @RequestBody List<PrayerTime> times) {
        cmsService.savePrayerTimes(masjidId, times);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    @GetMapping("/jumuah")
    public ResponseEntity<JumuahConfig> getJumuah(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId) {
        return ResponseEntity.ok(cmsService.getJumuah(masjidId));
    }

    @PutMapping("/jumuah")
    public ResponseEntity<Map<String, String>> saveJumuah(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId,
            @RequestBody JumuahConfig config) {
        cmsService.saveJumuah(masjidId, config);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    @GetMapping("/ramadan")
    public ResponseEntity<RamadanConfig> getRamadan(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId) {
        return ResponseEntity.ok(cmsService.getRamadan(masjidId));
    }

    @PutMapping("/ramadan")
    public ResponseEntity<Map<String, String>> saveRamadan(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId,
            @RequestBody RamadanConfig config) {
        cmsService.saveRamadan(masjidId, config);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    @GetMapping("/janazahs")
    public ResponseEntity<List<Janazah>> getJanazahs(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId) {
        return ResponseEntity.ok(cmsService.getJanazahs(masjidId));
    }

    @PostMapping("/janazahs")
    public ResponseEntity<Map<String, String>> createJanazah(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId,
            @RequestBody Janazah janazah) {
        cmsService.saveJanazah(masjidId, janazah);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    @DeleteMapping("/janazahs/{id}")
    public ResponseEntity<Map<String, String>> deleteJanazah(@PathVariable Long id) {
        cmsService.deleteJanazah(id);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    @GetMapping("/gumshudas")
    public ResponseEntity<List<Gumshuda>> getGumshudas(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId) {
        return ResponseEntity.ok(cmsService.getGumshudas(masjidId));
    }

    @PostMapping("/gumshudas")
    public ResponseEntity<Map<String, String>> createGumshuda(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId,
            @RequestBody Gumshuda gumshuda) {
        try {
            cmsService.saveGumshuda(masjidId, gumshuda);
            return ResponseEntity.ok(Map.of("status", "ok"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getClass().getName() + ": " + e.getMessage()));
        }
    }

    @DeleteMapping("/gumshudas/{id}")
    public ResponseEntity<Map<String, String>> deleteGumshuda(@PathVariable Long id) {
        cmsService.deleteGumshuda(id);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    @GetMapping("/announcements")
    public ResponseEntity<List<GeneralAnnouncement>> getAnnouncements(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId) {
        return ResponseEntity.ok(cmsService.getAnnouncements(masjidId));
    }

    @PostMapping("/announcements")
    public ResponseEntity<Map<String, String>> createAnnouncement(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId,
            @RequestBody GeneralAnnouncement a) {
        cmsService.saveAnnouncement(masjidId, a);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    @DeleteMapping("/announcements/{id}")
    public ResponseEntity<Map<String, String>> deleteAnnouncement(@PathVariable Long id) {
        cmsService.deleteAnnouncement(id);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    @GetMapping("/donation-causes")
    public ResponseEntity<List<DonationCause>> getDonationCauses(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId) {
        return ResponseEntity.ok(cmsService.getDonationCauses(masjidId));
    }

    @PostMapping("/donation-causes")
    public ResponseEntity<Map<String, String>> createDonationCause(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId,
            @RequestBody DonationCause dc) {
        cmsService.saveDonationCause(masjidId, dc);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    @DeleteMapping("/donation-causes/{id}")
    public ResponseEntity<Map<String, String>> deleteDonationCause(@PathVariable Long id) {
        cmsService.deleteDonationCause(id);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    @GetMapping("/monthly-donations")
    public ResponseEntity<List<MonthlyDonation>> getMonthlyDonations(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId) {
        return ResponseEntity.ok(cmsService.getMonthlyDonations(masjidId));
    }

    @PostMapping("/monthly-donations")
    public ResponseEntity<Map<String, String>> createMonthlyDonation(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId,
            @RequestBody MonthlyDonation md) {
        cmsService.saveMonthlyDonation(masjidId, md);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    @DeleteMapping("/monthly-donations/{id}")
    public ResponseEntity<Map<String, String>> deleteMonthlyDonation(@PathVariable Long id) {
        cmsService.deleteMonthlyDonation(id);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    @GetMapping("/expenses")
    public ResponseEntity<List<Expense>> getExpenses(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId) {
        return ResponseEntity.ok(cmsService.getExpenses(masjidId));
    }

    @PostMapping("/expenses")
    public ResponseEntity<Map<String, String>> createExpense(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId,
            @RequestBody Expense expense) {
        cmsService.saveExpense(masjidId, expense);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Map<String, String>> deleteExpense(@PathVariable Long id) {
        cmsService.deleteExpense(id);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    @GetMapping("/services")
    public ResponseEntity<List<AboutService>> getServices(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId) {
        return ResponseEntity.ok(cmsService.getServices(masjidId));
    }

    @PostMapping("/services")
    public ResponseEntity<Map<String, String>> createService(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId,
            @RequestBody AboutService service) {
        cmsService.saveService(masjidId, service);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    @DeleteMapping("/services/{id}")
    public ResponseEntity<Map<String, String>> deleteService(@PathVariable Long id) {
        cmsService.deleteService(id);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    @GetMapping("/team-members")
    public ResponseEntity<List<TeamMember>> getTeamMembers(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId) {
        return ResponseEntity.ok(cmsService.getTeamMembers(masjidId));
    }

    @PostMapping("/team-members")
    public ResponseEntity<Map<String, String>> createTeamMember(
            @RequestHeader(name = "X-Masjid-Id", defaultValue = "1") Long masjidId,
            @RequestBody TeamMember tm) {
        cmsService.saveTeamMember(masjidId, tm);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    @DeleteMapping("/team-members/{id}")
    public ResponseEntity<Map<String, String>> deleteTeamMember(@PathVariable Long id) {
        cmsService.deleteTeamMember(id);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}
