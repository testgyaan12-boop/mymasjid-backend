package com.noormasjid.repository;

import com.noormasjid.entity.tasbih.CustomAdhkar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomAdhkarRepository extends JpaRepository<CustomAdhkar, Long> {
    List<CustomAdhkar> findByUserId(Long userId);
}
