package com.noormasjid.repository;

import com.noormasjid.entity.sunnah.Sunnah;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SunnahRepository extends JpaRepository<Sunnah, Long> {
    List<Sunnah> findByMasjidIdAndIsDeleted(Long masjidId, Integer isDeleted);
}
