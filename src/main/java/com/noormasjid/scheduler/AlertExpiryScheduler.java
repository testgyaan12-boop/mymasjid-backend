package com.noormasjid.scheduler;

import com.noormasjid.entity.cms.GeneralAnnouncement;
import com.noormasjid.entity.cms.Gumshuda;
import com.noormasjid.entity.cms.Janazah;
import com.noormasjid.repository.GeneralAnnouncementRepository;
import com.noormasjid.repository.GumshudaRepository;
import com.noormasjid.repository.JanazahRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AlertExpiryScheduler {

    private static final Logger log = LoggerFactory.getLogger(AlertExpiryScheduler.class);

    private final JanazahRepository janazahRepository;
    private final GumshudaRepository gumshudaRepository;
    private final GeneralAnnouncementRepository announcementRepository;

    public AlertExpiryScheduler(JanazahRepository janazahRepository,
                                GumshudaRepository gumshudaRepository,
                                GeneralAnnouncementRepository announcementRepository) {
        this.janazahRepository = janazahRepository;
        this.gumshudaRepository = gumshudaRepository;
        this.announcementRepository = announcementRepository;
    }

    @Scheduled(fixedDelay = 3600000, initialDelay = 30000)
    @Transactional
    public void deactivateExpiredAlerts() {
        LocalDateTime cutoff = LocalDateTime.now().minusHours(24);
        deactivateJanazahs(cutoff);
        deactivateGumshudas(cutoff);
        deactivateAnnouncements(cutoff);
    }

    private void deactivateJanazahs(LocalDateTime cutoff) {
        try {
            List<Janazah> expired = janazahRepository
                    .findByIsDeletedAndActiveAndActivatedAtBefore(0, true, cutoff);
            for (Janazah j : expired) {
                j.setActive(false);
                janazahRepository.save(j);
            }
            if (!expired.isEmpty()) {
                log.info("Auto-deactivated {} janazah alert(s) active since before {}", expired.size(), cutoff);
            }
        } catch (Exception e) {
            log.warn("Failed to expire janazah alerts: {}", e.getMessage());
        }
    }

    private void deactivateGumshudas(LocalDateTime cutoff) {
        try {
            List<Gumshuda> expired = gumshudaRepository
                    .findByIsDeletedAndActiveAndActivatedAtBefore(0, true, cutoff);
            for (Gumshuda g : expired) {
                g.setActive(false);
                gumshudaRepository.save(g);
            }
            if (!expired.isEmpty()) {
                log.info("Auto-deactivated {} gumshuda alert(s) active since before {}", expired.size(), cutoff);
            }
        } catch (Exception e) {
            log.warn("Failed to expire gumshuda alerts: {}", e.getMessage());
        }
    }

    private void deactivateAnnouncements(LocalDateTime cutoff) {
        try {
            List<GeneralAnnouncement> expired = announcementRepository
                    .findByIsDeletedAndActiveAndActivatedAtBefore(0, true, cutoff);
            for (GeneralAnnouncement a : expired) {
                a.setActive(false);
                announcementRepository.save(a);
            }
            if (!expired.isEmpty()) {
                log.info("Auto-deactivated {} announcement(s) active since before {}", expired.size(), cutoff);
            }
        } catch (Exception e) {
            log.warn("Failed to expire announcements: {}", e.getMessage());
        }
    }
}