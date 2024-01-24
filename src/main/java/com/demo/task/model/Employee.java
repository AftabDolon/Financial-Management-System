package com.demo.task.model;

import com.demo.task.model.enumFile.BloodGroup;
import com.demo.task.model.enumFile.Department;
import com.demo.task.model.enumFile.Designation;
import com.demo.task.model.enumFile.SalaryGrade;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "SEC_EMPLOYEES")
public class Employee extends BaseEntity {
    @Column(name = "EMP_ID", unique = true, nullable = false, length = 4)
    private String empId;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Column(name = "DEPARTMENT")
    @Enumerated(EnumType.STRING)
    private Department department;
    @Column(name = "DESIGNATION")
    @Enumerated(EnumType.STRING)
    private Designation designation;
    @Column(name = "GENDER")
    private String gender;
    @Column(name = "NID")
    private String nid;
    @Column(name = "BLOOD_GROUP")
    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;
    @Column(name = "SALARY_GRADE")
    @Enumerated(EnumType.STRING)
    private SalaryGrade salaryGrade;
    @Column(name = "HIRE_DATE")
    private String hireDate;
    @Column(name = "EMP_IMAGE")
    private String image;
    @Column(name = "BANK_ID")
    private String bankId;
    @Column(name = "INSERT_BY")
    private String insertBy;
    @Column(name = "INSERT_DATE")
    private String insertDate;
    @Column(name = "UPDATED_BY")
    private String updatedBy;


    // getter method for the Designation enum
    @Transient
    public String getDesignationValue() {
        return designation != null ? designation.toString() : null;
    }

    // getter method for the BloodGroup enum
    @Transient
    public String getBloodGroupValue() {
        return bloodGroup != null ? bloodGroup.toString() : null;
    }

    @Transient
    public String getSalaryGradeValue() {
        return salaryGrade != null ? salaryGrade.toString() : null;
    }
}
