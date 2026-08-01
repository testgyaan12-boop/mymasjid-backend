package com.noormasjid.service;

import com.noormasjid.entity.auth.User;
import com.noormasjid.entity.masjid.Masjid;
import com.noormasjid.entity.sunnah.ActiveSunnahBroadcast;
import com.noormasjid.entity.sunnah.Sunnah;
import com.noormasjid.entity.sunnah.UserSavedSunnah;
import com.noormasjid.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class SunnahService {

    private final SunnahRepository sunnahRepository;
    private final ActiveSunnahBroadcastRepository broadcastRepository;
    private final UserSavedSunnahRepository userSavedSunnahRepository;
    private final MasjidRepository masjidRepository;
    private final UserRepository userRepository;

    public SunnahService(SunnahRepository sunnahRepository,
                         ActiveSunnahBroadcastRepository broadcastRepository,
                         UserSavedSunnahRepository userSavedSunnahRepository,
                         MasjidRepository masjidRepository,
                         UserRepository userRepository) {
        this.sunnahRepository = sunnahRepository;
        this.broadcastRepository = broadcastRepository;
        this.userSavedSunnahRepository = userSavedSunnahRepository;
        this.masjidRepository = masjidRepository;
        this.userRepository = userRepository;
    }

    public List<Sunnah> getSunnahs(Long masjidId) {
        return sunnahRepository.findByMasjidIdAndIsDeleted(masjidId, 0);
    }

    public Sunnah saveSunnah(Long masjidId, Sunnah sunnah) {
        sunnah.setMasjid(masjidRepository.getReferenceById(masjidId));
        return sunnahRepository.save(sunnah);
    }

    public void deleteSunnah(Long id) {
        Sunnah s = sunnahRepository.findById(id).orElseThrow();
        s.setIsDeleted(1);
        sunnahRepository.save(s);
    }

    public ActiveSunnahBroadcast getActiveBroadcast(Long masjidId) {
        return broadcastRepository
                .findByMasjidIdAndBroadcastDate(masjidId, LocalDate.now())
                .orElse(null);
    }

    public ActiveSunnahBroadcast setActiveBroadcast(Long masjidId, Long sunnahId) {
        LocalDate today = LocalDate.now();
        ActiveSunnahBroadcast broadcast = broadcastRepository
                .findByMasjidIdAndBroadcastDate(masjidId, today)
                .orElse(new ActiveSunnahBroadcast());

        broadcast.setMasjid(masjidRepository.getReferenceById(masjidId));
        broadcast.setSunnah(sunnahRepository.getReferenceById(sunnahId));
        broadcast.setBroadcastDate(today);
        return broadcastRepository.save(broadcast);
    }

    public List<UserSavedSunnah> getSavedSunnahs(Long userId) {
        return userSavedSunnahRepository.findByUserId(userId);
    }

    @Transactional
    public void saveSunnahForUser(Long userId, Long sunnahId) {
        if (!userSavedSunnahRepository.existsByUserIdAndSunnahId(userId, sunnahId)) {
            UserSavedSunnah uss = new UserSavedSunnah();
            uss.setUser(userRepository.getReferenceById(userId));
            uss.setSunnah(sunnahRepository.getReferenceById(sunnahId));
            userSavedSunnahRepository.save(uss);
        }
    }

    @Transactional
    public void unsaveSunnah(Long userId, Long sunnahId) {
        userSavedSunnahRepository.deleteByUserIdAndSunnahId(userId, sunnahId);
    }
}
