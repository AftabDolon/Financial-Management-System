package com.demo.task.controller;

import com.demo.task.constant.WebApiUrlConstants;
import com.demo.task.exception.InvalidOperationException;
import com.demo.task.model.Employee;
import com.demo.task.model.enumFile.BloodGroup;
import com.demo.task.model.enumFile.Department;
import com.demo.task.model.enumFile.Designation;
import com.demo.task.model.enumFile.SalaryGrade;
import com.demo.task.service.EmployeeService;
import com.demo.task.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = WebApiUrlConstants.EMPLOYEE_SAVE_API, method = RequestMethod.POST)
    public ResponseEntity<?> saveEmployee(@Valid @RequestBody Employee employee, HttpServletRequest request) {
        try {
            employeeService.save(employee, request);
            return ResponseEntity.status(HttpStatus.OK).body("RECORD INSERTED SUCCESSFULLY!");
        } catch (InvalidOperationException e) {
            String errorMessage = e.getMessage();
            Integer statusCode = e.getStatusCode();
            return ResponseEntity.status(statusCode).body(errorMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @RequestMapping(value = WebApiUrlConstants.EMPLOYEE_UPDATE_API, method = RequestMethod.POST)
    public ResponseEntity<?> updateEmployee(@Valid @RequestBody Employee employee, HttpServletRequest request) {
        try {
            employeeService.update(employee, request);
            return ResponseEntity.status(HttpStatus.OK).body("RECORD UPDATED SUCCESSFULLY!");
        } catch (InvalidOperationException e) {
            String errorMessage = e.getMessage();
            Integer statusCode = e.getStatusCode();
            return ResponseEntity.status(statusCode).body(errorMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @RequestMapping(value = WebApiUrlConstants.USER_API + "/employee-list", method = RequestMethod.GET)
    public List<Employee> getEmployeeInfo() {
        return employeeService.getData();
    }

    //
//    @RequestMapping(value = WebApiUrlConstants.USER_API + "/employee-summery-report", method = RequestMethod.GET)
//    public List<EmployeeSummery> getEmployeeSummeryInfo() {
//        return employeeService.getEmployeeSummary();
//    }
    @RequestMapping(value = "/employee/employee-list-values", method = RequestMethod.GET)
    ResponseEntity<?> getAllListValueForEmployee() {
        List<Department> getDepartment = Department.getAllDepartments();
        List<String> getDesignation = Designation.getAllDesignations();
        List<String> getBloodGroup = BloodGroup.getAllBloodGroups();
        List<String> getSalaryGrade = SalaryGrade.getAllSalaryGrade();
        Map<String, Object> response = new HashMap<>();
        response.put("department", getDepartment);
        response.put("designation", getDesignation);
        response.put("bloodGroup", getBloodGroup);
        response.put("salaryGrade", getSalaryGrade);
        return ResponseEntity.ok(response);
    }


}
