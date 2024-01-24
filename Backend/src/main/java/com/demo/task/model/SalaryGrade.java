package com.demo.task.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "SEC_SALARY_GRADE")
public class SalaryGrade extends BaseEntity {
    @Column(name = "SALARY_GRADE")
    private String salaryGrade;
    @Column(name = "AMOUNT")
    private Integer amount;
}
