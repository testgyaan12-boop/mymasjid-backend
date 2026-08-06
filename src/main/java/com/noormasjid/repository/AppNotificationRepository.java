package com.noormasjid.repository;

import com.noormasjid.entity.notification.AppNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppNotificationRepository extends JpaRepository<AppNotification, Long> {

    List<AppNotification> findByMasjidIdAndIsDeletedOrderByCreatedAtDesc(Long masjidId, Integer isDeleted);

    long countByMasjidIdAndIsReadFalseAndIsDeleted(Long masjidId, Integer isDeleted);

    @Modifying
    @Query("update AppNotification n set n.isRead = true, n.readAt = current_timestamp where n.masjid.id = :masjidId and n.isRead = false")
    int markAllRead(@Param("masjidId") Long masjidId);
}
