package com.noormasjid.repository;

import com.noormasjid.entity.cms.Janazah;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JanazahRepository extends JpaRepository<Janazah, Long> {
    List<Janazah> findByMasjidIdAndIsDeletedOrderByCreatedAtDesc(Long masjidId, Integer isDeleted);
    List<Janazah> findByMasjidIdAndIsDeletedAndActive(Long masjidId, Integer isDeleted, Boolean active);
    List<Janazah> findByIsDeletedAndActiveAndActivatedAtBefore(Integer isDeleted, Boolean active, LocalDateTime activatedAt);
}
