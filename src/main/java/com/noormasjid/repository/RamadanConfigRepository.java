package com.noormasjid.repository;

import com.noormasjid.entity.cms.RamadanConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RamadanConfigRepository extends JpaRepository<RamadanConfig, Long> {
    Optional<RamadanConfig> findByMasjidId(Long masjidId);
}
