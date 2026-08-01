package com.noormasjid.repository;

import com.noormasjid.entity.zakat.ZakatCalculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZakatCalculationRepository extends JpaRepository<ZakatCalculation, Long> {
    List<ZakatCalculation> findByUserIdOrderByCalculationDateDesc(Long userId);
}
