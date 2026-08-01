package com.noormasjid.repository;

import com.noormasjid.entity.cms.DonationCause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationCauseRepository extends JpaRepository<DonationCause, Long> {
    List<DonationCause> findByMasjidIdAndIsDeleted(Long masjidId, Integer isDeleted);
}
