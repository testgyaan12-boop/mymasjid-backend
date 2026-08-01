package com.noormasjid.repository;

import com.noormasjid.entity.donation.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByMasjidIdAndIsDeleted(Long masjidId, Integer isDeleted);
    List<Donation> findByUserId(Long userId);
}
