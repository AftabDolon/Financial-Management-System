package com.demo.task.repository;

import com.demo.task.model.SalaryGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryGradeRepository extends JpaRepository<SalaryGrade, Long> {
    @Query("SELECT s.amount FROM SalaryGrade s WHERE s.salaryGrade = :grade")
    Integer findAmountByGrade(@Param("grade") String grade);
}
