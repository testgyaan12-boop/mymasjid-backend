package com.noormasjid.scheduler;

import com.noormasjid.entity.cms.PrayerTime;
import com.noormasjid.entity.masjid.Masjid;
import com.noormasjid.entity.notification.PrayerReminderLog;
import com.noormasjid.repository.AppNotificationRepository;
import com.noormasjid.repository.MasjidRepository;
import com.noormasjid.repository.PrayerReminderLogRepository;
import com.noormasjid.repository.PrayerTimeRepository;
import com.noormasjid.service.PushNotificationService;
import com.noormasjid.entity.notification.AppNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class PrayerTimeReminderScheduler {

    private static final Logger log = LoggerFactory.getLogger(PrayerTimeReminderScheduler.class);
    private static final int REMIND_BEFORE_MINUTES = 10;

    private final MasjidRepository masjidRepository;
    private final PrayerTimeRepository prayerTimeRepository;
    private final PrayerReminderLogRepository reminderLogRepository;
    private final AppNotificationRepository appNotificationRepository;
    private final PushNotificationService pushNotificationService;

    public PrayerTimeReminderScheduler(MasjidRepository masjidRepository,
                                       PrayerTimeRepository prayerTimeRepository,
                                       PrayerReminderLogRepository reminderLogRepository,
                                       AppNotificationRepository appNotificationRepository,
                                       PushNotificationService pushNotificationService) {
        this.masjidRepository = masjidRepository;
        this.prayerTimeRepository = prayerTimeRepository;
        this.reminderLogRepository = reminderLogRepository;
        this.appNotificationRepository = appNotificationRepository;
        this.pushNotificationService = pushNotificationService;
    }

    @Scheduled(fixedDelay = 60000, initialDelay = 20000)
    public void checkIqamahReminders() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        List<Masjid> masjids = masjidRepository.findByIsDeleted(0);

        for (Masjid masjid : masjids) {
            List<PrayerTime> times = prayerTimeRepository.findByMasjidIdOrderBySortOrderAsc(masjid.getId());
            for (PrayerTime pt : times) {
                LocalTime iqamah = parseTime(pt.getPrayerTime());
                if (iqamah == null) continue;

                LocalTime remindAt = iqamah.minusMinutes(REMIND_BEFORE_MINUTES);
                if (now.isBefore(remindAt) || !now.isBefore(remindAt.plusSeconds(60))) {
                    continue;
                }

                String prayerName = pt.getPrayerName();
                if (reminderLogRepository.existsByMasjidIdAndPrayerNameAndReminderDate(
                        masjid.getId(), prayerName, today)) {
                    continue;
                }

                sendReminder(masjid, prayerName, iqamah, today);
            }
        }
    }

    private void sendReminder(Masjid masjid, String prayerName, LocalTime iqamah, LocalDate today) {
        String display = formatNice(iqamah);
        String title = prayerName + " Iqamah in 10 minutes";
        String body = "Iqamah for " + prayerName + " is at " + display;
        String type = "prayer-reminder";

        try {
            AppNotification n = new AppNotification();
            n.setMasjid(masjid);
            n.setTitle(title);
            n.setMessage(body);
            n.setType(type);
            n.setIsRead(false);
            appNotificationRepository.save(n);
        } catch (Exception e) {
            log.warn("Failed to persist prayer reminder for masjid {}: {}", masjid.getId(), e.getMessage());
        }

        try {
            pushNotificationService.sendToMasjid(masjid.getId(), title, body, type);
        } catch (Exception e) {
            log.warn("Failed to push prayer reminder for masjid {}: {}", masjid.getId(), e.getMessage());
        }

        PrayerReminderLog logRow = new PrayerReminderLog();
        logRow.setMasjid(masjid);
        logRow.setPrayerName(prayerName);
        logRow.setReminderDate(today);
        logRow.setIqamahTime(iqamah.toString());
        logRow.setSentAt(LocalDateTime.now());
        reminderLogRepository.save(logRow);

        log.info("Sent {} iqamah reminder for masjid {} at {}", prayerName, masjid.getId(), iqamah);
    }

    private String formatNice(LocalTime t) {
        String h = t.getHour() % 12 == 0 ? "12" : String.valueOf(t.getHour() % 12);
        return h + ":" + String.format("%02d", t.getMinute())
                + (t.getHour() < 12 ? " AM" : " PM");
    }

    protected LocalTime parseTime(String raw) {
        if (raw == null || raw.isBlank()) return null;
        String s = raw.trim().toUpperCase();
        try {
            if (s.contains("PM") || s.contains("AM")) {
                DateTimeFormatter fmt = s.contains(":")
                        ? DateTimeFormatter.ofPattern("h:mm a")
                        : DateTimeFormatter.ofPattern("h a");
                return LocalTime.parse(s, fmt);
            }
            return LocalTime.parse(s);
        } catch (Exception e) {
            log.debug("Cannot parse prayer time '{}'", raw);
            return null;
        }
    }
}