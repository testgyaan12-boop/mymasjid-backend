package com.noormasjid.repository;

import com.noormasjid.entity.cms.Janazah;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JanazahRepository extends JpaRepository<Janazah, Long> {
    @Query("SELECT j FROM Janazah j JOIN FETCH j.masjid WHERE j.masjid.id = :masjidId AND j.isDeleted = :isDeleted ORDER BY j.createdAt DESC")
    List<Janazah> findByMasjidIdAndIsDeletedOrderByCreatedAtDesc(@Param("masjidId") Long masjidId, @Param("isDeleted") Integer isDeleted);
    @Query("SELECT j FROM Janazah j JOIN FETCH j.masjid WHERE j.masjid.id = :masjidId AND j.isDeleted = :isDeleted AND j.active = :active")
    List<Janazah> findByMasjidIdAndIsDeletedAndActive(@Param("masjidId") Long masjidId, @Param("isDeleted") Integer isDeleted, @Param("active") Boolean active);
}
