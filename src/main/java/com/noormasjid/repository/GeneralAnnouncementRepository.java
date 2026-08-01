package com.noormasjid.repository;

import com.noormasjid.entity.cms.GeneralAnnouncement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneralAnnouncementRepository extends JpaRepository<GeneralAnnouncement, Long> {
    List<GeneralAnnouncement> findByMasjidIdAndIsDeletedOrderByCreatedAtDesc(Long masjidId, Integer isDeleted);
}
