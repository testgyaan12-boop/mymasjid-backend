package com.noormasjid.repository;

import com.noormasjid.entity.sunnah.ActiveSunnahBroadcast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ActiveSunnahBroadcastRepository extends JpaRepository<ActiveSunnahBroadcast, Long> {
    Optional<ActiveSunnahBroadcast> findByMasjidIdAndBroadcastDate(Long masjidId, LocalDate broadcastDate);
}
