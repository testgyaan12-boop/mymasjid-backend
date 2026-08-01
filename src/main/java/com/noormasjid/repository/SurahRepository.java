package com.noormasjid.repository;

import com.noormasjid.entity.quran.Surah;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurahRepository extends JpaRepository<Surah, Long> {
    java.util.Optional<Surah> findBySurahNumber(Integer surahNumber);
}
