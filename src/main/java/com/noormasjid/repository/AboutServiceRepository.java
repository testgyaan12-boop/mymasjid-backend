package com.noormasjid.repository;

import com.noormasjid.entity.cms.AboutService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AboutServiceRepository extends JpaRepository<AboutService, Long> {
    List<AboutService> findByMasjidIdAndIsDeleted(Long masjidId, Integer isDeleted);
}
