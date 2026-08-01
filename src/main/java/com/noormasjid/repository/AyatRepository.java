package com.noormasjid.repository;

import com.noormasjid.entity.quran.Ayat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AyatRepository extends JpaRepository<Ayat, Long> {
    List<Ayat> findBySurahIdOrderByAyatNumberAsc(Long surahId);
}
