package com.noormasjid.repository;

import com.noormasjid.entity.cms.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByMasjidIdAndIsDeleted(Long masjidId, Integer isDeleted);
}
