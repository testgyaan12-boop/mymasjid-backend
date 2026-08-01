package com.noormasjid.repository;

import com.noormasjid.entity.cms.PrayerTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrayerTimeRepository extends JpaRepository<PrayerTime, Long> {
    List<PrayerTime> findByMasjidIdOrderBySortOrderAsc(Long masjidId);
    void deleteByMasjidId(Long masjidId);
}
