package com.noormasjid.repository;

import com.noormasjid.entity.cms.Gumshuda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GumshudaRepository extends JpaRepository<Gumshuda, Long> {
    @Query("SELECT g FROM Gumshuda g JOIN FETCH g.masjid WHERE g.masjid.id = :masjidId AND g.isDeleted = :isDeleted ORDER BY g.createdAt DESC")
    List<Gumshuda> findByMasjidIdAndIsDeletedOrderByCreatedAtDesc(@Param("masjidId") Long masjidId, @Param("isDeleted") Integer isDeleted);
    List<Gumshuda> findByMasjidIdAndIsDeletedAndActive(Long masjidId, Integer isDeleted, Boolean active);
    @Query("SELECT g FROM Gumshuda g JOIN FETCH g.masjid WHERE g.isDeleted = :isDeleted AND g.active = :active AND g.found = :found")
    List<Gumshuda> findByIsDeletedAndActiveAndFound(@Param("isDeleted") Integer isDeleted, @Param("active") Boolean active, @Param("found") Boolean found);
    boolean existsByContactAndActiveAndIsDeletedAndFound(String contact, Boolean active, Integer isDeleted, Boolean found);
    boolean existsByIsDeletedAndActiveAndFoundAndContact(Integer isDeleted, Boolean active, Boolean found, String contact);
}
