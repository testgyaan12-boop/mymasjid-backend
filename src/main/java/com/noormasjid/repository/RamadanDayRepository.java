package com.noormasjid.repository;

import com.noormasjid.entity.cms.RamadanDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RamadanDayRepository extends JpaRepository<RamadanDay, Long> {
    List<RamadanDay> findByMasjidIdAndIsDeletedOrderByDayNoAsc(Long masjidId, Integer isDeleted);
    void deleteByMasjidId(Long masjidId);
}