package com.noormasjid.repository;

import com.noormasjid.entity.notification.DeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {

    List<DeviceToken> findByMasjidIdAndIsDeleted(Long masjidId, Integer isDeleted);

    List<DeviceToken> findByUserIdAndIsDeleted(Long userId, Integer isDeleted);

    List<DeviceToken> findAllByIsDeleted(Integer isDeleted);

    Optional<DeviceToken> findByToken(String token);

    void deleteByToken(String token);
}