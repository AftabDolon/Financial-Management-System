package com.demo.task.controller;

import com.demo.task.constant.WebApiUrlConstants;
import com.demo.task.exception.InvalidOperationException;
import com.demo.task.model.CompanyAccount;
import com.demo.task.service.SalaryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SalaryController {
    @Autowired
    private SalaryService salaryService;

    @RequestMapping(value = WebApiUrlConstants.BALANCE_SAVE_API, method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@Valid @RequestBody CompanyAccount companyAccount, HttpServletRequest request) {
        try {
            salaryService.saveCompanyBalance(companyAccount, request);
            return ResponseEntity.status(HttpStatus.OK).body("RECORD INSERTED SUCCESSFULLY!");
        } catch (InvalidOperationException e) {
            String errorMessage = e.getMessage();
            Integer statusCode = e.getStatusCode();
            return ResponseEntity.status(statusCode).body(errorMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @RequestMapping(value = WebApiUrlConstants.BALANCE_API + "/listValue", method = RequestMethod.GET)
    public ResponseEntity<?> getUserList() {
        return ResponseEntity.ok(salaryService.getData());
    }

    @RequestMapping(value = WebApiUrlConstants.BALANCE_API + "/balance-info", method = RequestMethod.GET)
    public ResponseEntity<?> getBalanceInformation() {
        return ResponseEntity.ok(salaryService.getBalanceInformation());
    }

    @RequestMapping(value = WebApiUrlConstants.TRANSFER_SALARY_API, method = RequestMethod.POST)
    public ResponseEntity<?> transferSalary(@PathVariable("date") Integer date, HttpServletRequest request) {
        try {
            salaryService.transferSalaryToEmployees(date, request);
            return ResponseEntity.status(HttpStatus.OK).body("SALARY TRANSFER SUCCESSFULLY!");
        } catch (InvalidOperationException e) {
            String errorMessage = e.getMessage();
            Integer statusCode = e.getStatusCode();
            return ResponseEntity.status(statusCode).body(errorMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @RequestMapping(value = WebApiUrlConstants.BALANCE_API + "/salary-info/{fromDate}/{toDate}", method = RequestMethod.GET)
    public ResponseEntity<?> getSalaryReports(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate) {
        if ("null".equals(fromDate)) {
            fromDate = null;
        }
        if ("null".equals(toDate)) {
            toDate = null;
        }
        return ResponseEntity.ok(salaryService.getSalaryReports(fromDate, toDate));
    }
}
