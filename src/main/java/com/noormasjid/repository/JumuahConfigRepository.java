package com.noormasjid.repository;

import com.noormasjid.entity.cms.JumuahConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JumuahConfigRepository extends JpaRepository<JumuahConfig, Long> {
    Optional<JumuahConfig> findByMasjidId(Long masjidId);
}
