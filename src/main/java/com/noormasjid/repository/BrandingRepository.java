package com.noormasjid.repository;

import com.noormasjid.entity.cms.Branding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandingRepository extends JpaRepository<Branding, Long> {
    Optional<Branding> findByMasjidId(Long masjidId);
}
