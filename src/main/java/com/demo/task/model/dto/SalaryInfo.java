package com.demo.task.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalaryInfo {
    private Integer amount;
    private String date;
    private String txnId;
    private String salaryGrade;
    private String employeeName;
    private String bankId;
    private String gender;
    private String designation;
    private String department;
    private String phoneNumber;
}
