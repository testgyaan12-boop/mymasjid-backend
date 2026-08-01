package com.noormasjid.repository;

import com.noormasjid.entity.masjid.Masjid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasjidRepository extends JpaRepository<Masjid, Long> {
    List<Masjid> findByPincode(String pincode);
    List<Masjid> findByPincodeAndIsDeleted(String pincode, Integer isDeleted);
    List<Masjid> findByNameContainingIgnoreCaseAndIsDeleted(String name, Integer isDeleted);
    List<Masjid> findByIsDeleted(Integer isDeleted);
}
