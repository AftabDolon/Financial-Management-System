package com.demo.task.serviceImpl;

import com.demo.task.auth.JwtTokenUtils;
import com.demo.task.auth.dto.SecurityUser;
import com.demo.task.exception.InvalidOperationException;
import com.demo.task.model.CompanyAccount;
import com.demo.task.model.Employee;
import com.demo.task.model.SalaryTransaction;
import com.demo.task.model.dto.SalaryInfo;
import com.demo.task.repository.*;
import com.demo.task.service.SalaryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SalaryServiceImpl implements SalaryService {
    @Autowired
    private CompanyBalanceRepository companyBalanceRepository;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private SecurityUserRepository userRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private SalaryGradeRepository salaryGradeRepository;
    @Autowired
    private SalaryTransactionRepository salaryTransactionRepository;

    @Override
    public CompanyAccount saveCompanyBalance(CompanyAccount companyAccount, HttpServletRequest request) {
        companyAccount.setInsertBy(String.valueOf(getUserId(request)));
        companyAccount.setInsertDate(String.valueOf(System.currentTimeMillis()));
        Integer latestCurrentAmount = companyBalanceRepository.findLatestCurrentAmount();
        if (latestCurrentAmount == null) {
            companyAccount.setCurrentAmount(companyAccount.getAmount());
        } else {
            companyAccount.setCurrentAmount(latestCurrentAmount + companyAccount.getAmount());
        }
        return companyBalanceRepository.save(companyAccount);
    }

    @Override
    public CompanyAccount updateCompanyBalance(CompanyAccount companyAccount, HttpServletRequest request) {
        return null;
    }

    @Override
    public List<CompanyAccount> getData() {
        return companyBalanceRepository.getData().stream().map(CompanyAccount::new).collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getBalanceInformation() {
        return companyBalanceRepository.getBalanceInformation();
    }

    @Override
    public List<SalaryInfo> getSalaryReports(String fromDate, String toDate) {
        List<Object[]> getSalaryInfo = companyBalanceRepository.getSalaryReport(fromDate, toDate);
        return getSalaryInfo.stream().map(row -> new SalaryInfo((Integer) row[0], (String) row[1], (String) row[2],
                        (String) row[3], (String) row[4], (String) row[5], (String) row[6], (String) row[7], (String) row[8], (String) row[9]))
                .collect(Collectors.toList());
    }

    @Override
    public void transferSalaryToEmployees(int month, HttpServletRequest request) {
        List<Employee> employees = employeeRepository.findAll();
        LocalDate currentDate = LocalDate.now();
        for (Employee employee : employees) {
            int existingTransactions = salaryTransactionRepository.countSalaryTransactionsForMonth(employee.getId(), currentDate.getYear(), currentDate.getMonthValue());

            // Rest of the logic remains the same
            int totalSalary = calculateTotalSalary(employee);
            Integer latestCurrentAmount = companyBalanceRepository.findLatestCurrentAmount();

            // Check if there's enough balance
            if (latestCurrentAmount != null && latestCurrentAmount >= totalSalary) {
                // Transfer salary to each employee
                transferSalaryToEmployee(employee, request);

                // Update the currentAmount after the salary transfer
                updateCurrentAmountAfterTransfer(totalSalary);
            } else {
                // Handle insufficient funds or other scenarios
                throw new InvalidOperationException("Insufficient funds to transfer salary.", 417);
            }
        }
    }

    private int calculateTotalSalary(Employee employees) {
        // Logic to calculate the total salary based on individual employee salaries and their salary grades
        return getSalaryForEmployee(employees);
    }

    private int getSalaryForEmployee(Employee employee) {
        String employeeSalaryGrade = mapEnumToDatabaseValue(employee.getSalaryGradeValue());
        Integer salaryAmount = salaryGradeRepository.findAmountByGrade(employeeSalaryGrade);

        if (salaryAmount != null) {
            return salaryAmount;
        } else {
            // Handle the case where no salary amount is found for the employee's grade
            return 0;
        }
    }

    public void transferSalaryToEmployee(Employee employee, HttpServletRequest request) {
        // Fetch the salary amount for the employee
        LocalDate currentDate = LocalDate.now();
        int salaryAmount = getSalaryForEmployee(employee);

        // Create a salary transaction
        SalaryTransaction salaryTransaction = new SalaryTransaction();
        salaryTransaction.setEmployee(employee);
        salaryTransaction.setAmount(salaryAmount);
        salaryTransaction.setDate(String.valueOf(currentDate));
        salaryTransaction.setDescription("Salary Transfer to Employee: " + employee.getEmpId());
        salaryTransaction.setTxnId(generateTransactionId());
        salaryTransaction.setInsertBy(String.valueOf(getUserId(request)));
        salaryTransaction.setInsertDate(String.valueOf(currentDate));

        salaryTransactionRepository.save(salaryTransaction);
    }

    public void updateCurrentAmountAfterTransfer(int totalSalaryAmount) {
        // Fetch the latest company balance
        Integer latestCompanyBalance = companyBalanceRepository.findLatestCurrentAmount();

        if (latestCompanyBalance != null) {
            // Subtract the total salary amount from the current balance
            int updatedBalance = latestCompanyBalance - totalSalaryAmount;
            // Save the updated company balance
            Long latestId = companyBalanceRepository.findLatestId();
            if (latestId != null) {
                companyBalanceRepository.updateCurrentAmount(latestId, updatedBalance);
            }
        } else {
            System.out.println("No Current Balance Found!");
        }
    }

    private String generateTransactionId() {
        long timestamp = System.currentTimeMillis();
        String randomPart = UUID.randomUUID().toString().substring(0, 8);

        return timestamp + "-" + randomPart;
    }

    private String mapEnumToDatabaseValue(String enumValue) {
        switch (enumValue) {
            case "GRADE ONE":
                return "GRADE_1";
            case "GRADE TWO":
                return "GRADE_2";
            case "GRADE THREE":
                return "GRADE_3";
            case "GRADE FOUR":
                return "GRADE_4";
            case "GRADE FIVE":
                return "GRADE_5";
            case "GRADE SIX":
                return "GRADE_6";
            default:
                throw new IllegalArgumentException("Invalid salary grade: " + enumValue);
        }
    }

    public Long getUserId(HttpServletRequest request) {
        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = requestTokenHeader.substring(7);
        String username = jwtTokenUtils.getUserNameFromToken(jwtToken);
        SecurityUser userValue = userRepository.findByUsername(username).get();
        return userValue.getUserId();
    }
}
