package com.noormasjid.repository;

import com.noormasjid.entity.cms.GeneralAnnouncement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GeneralAnnouncementRepository extends JpaRepository<GeneralAnnouncement, Long> {
    List<GeneralAnnouncement> findByMasjidIdAndIsDeletedOrderByCreatedAtDesc(Long masjidId, Integer isDeleted);
    List<GeneralAnnouncement> findByIsDeletedAndActiveAndActivatedAtBefore(Integer isDeleted, Boolean active, LocalDateTime activatedAt);
}
