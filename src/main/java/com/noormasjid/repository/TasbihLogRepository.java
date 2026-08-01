package com.noormasjid.repository;

import com.noormasjid.entity.tasbih.TasbihLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasbihLogRepository extends JpaRepository<TasbihLog, Long> {
    List<TasbihLog> findByUserIdOrderBySessionDateDesc(Long userId);
}
