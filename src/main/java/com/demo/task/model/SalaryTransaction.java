package com.demo.task.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "SALARY_TRANSACTIONS")
public class SalaryTransaction extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")  // Adjust the column name based on your actual database schema
    private Employee employee;

    @Column(name = "AMOUNT")
    private Integer amount;

    @Column(name = "DATE")
    private String date;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TXN_ID", unique = true)
    private String txnId;

    @Column(name = "INSERT_BY")
    private String insertBy;

    @Column(name = "INSERT_DATE")
    private String insertDate;

    // Additional properties or methods as needed
}
