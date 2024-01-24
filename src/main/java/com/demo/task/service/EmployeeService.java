package com.demo.task.service;

import com.demo.task.model.Employee;
import org.springframework.web.multipart.MultipartFile;

public interface EmployeeService extends BaseService<Employee, Long> {
    Object[][] getDataByEmployee();
}
