package com.noormasjid.repository;

import com.noormasjid.entity.cms.MonthlyDonation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyDonationRepository extends JpaRepository<MonthlyDonation, Long> {
    List<MonthlyDonation> findByMasjidIdAndIsDeleted(Long masjidId, Integer isDeleted);
}
