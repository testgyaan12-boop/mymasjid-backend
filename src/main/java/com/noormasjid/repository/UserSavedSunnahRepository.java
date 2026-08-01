package com.noormasjid.repository;

import com.noormasjid.entity.sunnah.UserSavedSunnah;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSavedSunnahRepository extends JpaRepository<UserSavedSunnah, Long> {
    List<UserSavedSunnah> findByUserId(Long userId);
    Optional<UserSavedSunnah> findByUserIdAndSunnahId(Long userId, Long sunnahId);
    boolean existsByUserIdAndSunnahId(Long userId, Long sunnahId);
    void deleteByUserIdAndSunnahId(Long userId, Long sunnahId);
}
