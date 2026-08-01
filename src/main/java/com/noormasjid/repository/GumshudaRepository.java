package com.noormasjid.repository;

import com.noormasjid.entity.cms.Gumshuda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GumshudaRepository extends JpaRepository<Gumshuda, Long> {
    List<Gumshuda> findByMasjidIdAndIsDeletedOrderByCreatedAtDesc(Long masjidId, Integer isDeleted);
    List<Gumshuda> findByMasjidIdAndIsDeletedAndActive(Long masjidId, Integer isDeleted, Boolean active);
}
