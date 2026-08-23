package com.noormasjid.repository;

import com.noormasjid.entity.cms.GeneralAnnouncement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneralAnnouncementRepository extends JpaRepository<GeneralAnnouncement, Long> {
    @Query("SELECT a FROM GeneralAnnouncement a JOIN FETCH a.masjid WHERE a.masjid.id = :masjidId AND a.isDeleted = :isDeleted ORDER BY a.createdAt DESC")
    List<GeneralAnnouncement> findByMasjidIdAndIsDeletedOrderByCreatedAtDesc(@Param("masjidId") Long masjidId, @Param("isDeleted") Integer isDeleted);
}
