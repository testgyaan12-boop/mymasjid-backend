package com.noormasjid.controller;

import com.noormasjid.entity.quran.Ayat;
import com.noormasjid.entity.quran.ReadingProgress;
import com.noormasjid.entity.quran.Surah;
import com.noormasjid.security.UserDetailsImpl;
import com.noormasjid.service.QuranService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuranController {

    private final QuranService quranService;

    public QuranController(QuranService quranService) {
        this.quranService = quranService;
    }

    @GetMapping("/quran/surahs")
    public ResponseEntity<List<Surah>> getAllSurahs() {
        return ResponseEntity.ok(quranService.getAllSurahs());
    }

    @GetMapping("/quran/surahs/{id}")
    public ResponseEntity<Surah> getSurah(@PathVariable Long id) {
        Surah surah = quranService.getSurah(id);
        if (surah == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(surah);
    }

    @GetMapping("/quran/surahs/{id}/ayats")
    public ResponseEntity<List<Ayat>> getAyats(@PathVariable Long id) {
        return ResponseEntity.ok(quranService.getAyatsBySurah(id));
    }

    @GetMapping("/user/reading-progress")
    public ResponseEntity<ReadingProgress> getProgress(
            @AuthenticationPrincipal UserDetailsImpl user) {
        ReadingProgress progress = quranService.getProgress(user.getId());
        if (progress == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(progress);
    }

    @PutMapping("/user/reading-progress")
    public ResponseEntity<ReadingProgress> saveProgress(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestBody ReadingProgress progress) {
        return ResponseEntity.ok(quranService.saveProgress(user.getId(), progress));
    }
}
