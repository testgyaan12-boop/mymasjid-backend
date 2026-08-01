package com.noormasjid.service;

import com.noormasjid.entity.donation.Donation;
import com.noormasjid.repository.DonationRepository;
import com.noormasjid.repository.MasjidRepository;
import com.noormasjid.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final MasjidRepository masjidRepository;
    private final UserRepository userRepository;

    public DonationService(DonationRepository donationRepository,
                           MasjidRepository masjidRepository,
                           UserRepository userRepository) {
        this.donationRepository = donationRepository;
        this.masjidRepository = masjidRepository;
        this.userRepository = userRepository;
    }

    public List<Donation> getDonations(Long masjidId) {
        return donationRepository.findByMasjidIdAndIsDeleted(masjidId, 0);
    }

    public Donation createDonation(Long masjidId, Long userId, Donation donation) {
        donation.setMasjid(masjidRepository.getReferenceById(masjidId));
        if (userId != null) {
            donation.setUser(userRepository.getReferenceById(userId));
        }
        return donationRepository.save(donation);
    }
}
