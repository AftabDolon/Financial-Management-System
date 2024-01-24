package com.demo.task.repository;

import com.demo.task.model.CompanyAccount;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public interface CompanyBalanceRepository extends JpaRepository<CompanyAccount, Long> {
    @Query(value = "SELECT \n" +
            "    BANK_ACC,\n" +
            "    date,\n" +
            "    description,\n" +
            "    TXN_ID, amount, CURRENT_AMOUNT, \n" +
            "    CASE\n" +
            "        WHEN transactionTypeId = 1 THEN 'ADD AMOUNT'\n" +
            "        ELSE 'DEDUCT AMOUNT'\n" +
            "    END AS TRANSACTION_TYPE\n" +
            "FROM\n" +
            "    task.sec_company_balance", nativeQuery = true)
    List<Tuple> getData();

    @Query(value = "SELECT c.CURRENT_AMOUNT FROM task.sec_company_balance c ORDER BY c.date DESC LIMIT 1", nativeQuery = true)
    Integer findLatestCurrentAmount();


    @Modifying
    @Transactional
    @Query(value = "UPDATE CompanyAccount cb SET cb.currentAmount = :newAmount WHERE cb.id = :id")
    void updateCurrentAmount(@Param("id") Long id, @Param("newAmount") int newAmount);

    @Query(value = "SELECT MAX(cbi.id) FROM CompanyAccount cbi")
    Long findLatestId();

    @Query(value = "SELECT SUM(AMOUNT)TOTAL_AMOUNT, (SELECT CURRENT_AMOUNT FROM task.sec_company_balance\n" +
            "WHERE ID = (SELECT MAX(id) FROM task.sec_company_balance))CURRENT_AMOUNT, BANK_ACC FROM task.sec_company_balance", nativeQuery = true)
    Map<String, Object> getBalanceInformation();

    @Query(value = "SELECT \n" +
            "    ST.AMOUNT, \n" +
            "    ST.DATE, \n" +
            "    ST.TXN_ID, \n" +
            "    E.SALARY_GRADE, \n" +
            "    CONCAT(E.FIRST_NAME, ' ', E.LAST_NAME) AS EMPLOYEE_NAME,\n" +
            "    E.BANK_ID, \n" +
            "    E.GENDER, \n" +
            "    E.DESIGNATION, \n" +
            "    E.DEPARTMENT, \n" +
            "    E.PHONE_NUMBER\n" +
            "FROM \n" +
            "    task.salary_transactions ST\n" +
            "JOIN \n" +
            "    task.sec_employees E ON ST.EMPLOYEE_ID = E.ID \n" +
            "WHERE \n" +
            "    ST.DATE BETWEEN IFNULL(:fromDate, DATE_FORMAT(CURDATE(), '%Y-%m-01')) AND IFNULL(:toDate, LAST_DAY(CURDATE()))", nativeQuery = true)
    List<Object[]> getSalaryReport(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
}
