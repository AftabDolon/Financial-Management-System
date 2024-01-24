package com.demo.task.service;

import com.demo.task.model.CompanyAccount;
import com.demo.task.model.dto.SalaryInfo;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

public interface SalaryService {
    CompanyAccount saveCompanyBalance(CompanyAccount companyAccount, HttpServletRequest request);

    CompanyAccount updateCompanyBalance(CompanyAccount companyAccount, HttpServletRequest request);

    List<CompanyAccount> getData();

    Map<String, Object> getBalanceInformation();

    List<SalaryInfo> getSalaryReports(String fromDate, String toDate);

    void transferSalaryToEmployees(int month, HttpServletRequest request);
}
