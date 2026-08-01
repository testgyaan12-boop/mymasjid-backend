package com.noormasjid.repository;

import com.noormasjid.entity.cms.HomeAnnouncement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HomeAnnouncementRepository extends JpaRepository<HomeAnnouncement, Long> {
    Optional<HomeAnnouncement> findByMasjidId(Long masjidId);
}
