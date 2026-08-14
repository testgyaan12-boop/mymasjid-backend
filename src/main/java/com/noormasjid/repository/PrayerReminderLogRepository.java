package com.noormasjid.repository;

import com.noormasjid.entity.notification.PrayerReminderLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PrayerReminderLogRepository extends JpaRepository<PrayerReminderLog, Long> {

    boolean existsByMasjidIdAndPrayerNameAndReminderDate(Long masjidId, String prayerName, LocalDate reminderDate);
}