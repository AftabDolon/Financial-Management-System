package com.demo.task.repository;

import com.demo.task.model.Employee;
import com.demo.task.model.SalaryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryTransactionRepository extends JpaRepository<SalaryTransaction, Long> {
    @Query(value = "SELECT COUNT(*) FROM task.salary_transactions WHERE EMPLOYEE_ID = :employee AND YEAR(DATE) = :year AND MONTH(DATE) = :month", nativeQuery = true)
    int countSalaryTransactionsForMonth(@Param("employee") Long employee, @Param("year") int year, @Param("month") int month);
}
