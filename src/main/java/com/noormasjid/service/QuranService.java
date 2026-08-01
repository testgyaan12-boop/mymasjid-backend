package com.noormasjid.service;

import com.noormasjid.entity.quran.Ayat;
import com.noormasjid.entity.quran.ReadingProgress;
import com.noormasjid.entity.quran.Surah;
import com.noormasjid.repository.AyatRepository;
import com.noormasjid.repository.ReadingProgressRepository;
import com.noormasjid.repository.SurahRepository;
import com.noormasjid.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuranService {

    private final SurahRepository surahRepository;
    private final AyatRepository ayatRepository;
    private final ReadingProgressRepository readingProgressRepository;
    private final UserRepository userRepository;

    public QuranService(SurahRepository surahRepository,
                        AyatRepository ayatRepository,
                        ReadingProgressRepository readingProgressRepository,
                        UserRepository userRepository) {
        this.surahRepository = surahRepository;
        this.ayatRepository = ayatRepository;
        this.readingProgressRepository = readingProgressRepository;
        this.userRepository = userRepository;
    }

    public List<Surah> getAllSurahs() {
        return surahRepository.findAll();
    }

    public Surah getSurah(Long id) {
        return surahRepository.findById(id).orElse(null);
    }

    public List<Ayat> getAyatsBySurah(Long surahId) {
        return ayatRepository.findBySurahIdOrderByAyatNumberAsc(surahId);
    }

    public ReadingProgress getProgress(Long userId) {
        return readingProgressRepository.findByUserId(userId).orElse(null);
    }

    public ReadingProgress saveProgress(Long userId, ReadingProgress progress) {
        ReadingProgress existing = readingProgressRepository.findByUserId(userId)
                .orElse(new ReadingProgress());
        existing.setUser(userRepository.getReferenceById(userId));
        existing.setCompletedAyats(progress.getCompletedAyats());
        existing.setCompletedJuzs(progress.getCompletedJuzs());
        existing.setLastReadSurah(progress.getLastReadSurah());
        existing.setLastReadJuz(progress.getLastReadJuz());
        existing.setLastReadPage(progress.getLastReadPage());
        existing.setLastReadMode(progress.getLastReadMode());
        existing.setSurahCheckpoints(progress.getSurahCheckpoints());
        existing.setJuzCheckpoints(progress.getJuzCheckpoints());
        existing.setSessionTime(progress.getSessionTime());
        return readingProgressRepository.save(existing);
    }
}
