package com.demo.task.serviceImpl;

import com.demo.task.auth.JwtTokenUtils;
import com.demo.task.auth.dto.SecurityUser;
import com.demo.task.constant.ErrorStatusCode;
import com.demo.task.exception.InvalidOperationException;
import com.demo.task.model.Employee;
import com.demo.task.repository.EmployeeRepository;
import com.demo.task.repository.SecurityUserRepository;
import com.demo.task.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class EmployeeServiceImplementation implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SecurityUserRepository userRepository;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    private final String FOLDER_PATH = "E:/Angular/AMS/src/assets/img/";

    @Override
    public Employee save(Employee employee, HttpServletRequest request) {
        if (areMandatoryFieldsPresent(employee)) {
            employee.setInsertBy(String.valueOf(getUserId(request)));
            return employeeRepository.save(employee);
        } else {
            throw new InvalidOperationException("Mandatory fields missing ", 417);
        }
    }


    @Override
    public Employee update(Employee employee, HttpServletRequest request) {
        if (!employee.hasId()) {
            throw new InvalidOperationException(ErrorStatusCode.NOT_PROVIDE_ID.getValue(), ErrorStatusCode.NOT_PROVIDE_ID.getCode());
        } else if (Stream.of(employee.getEmail(), employee.getFirstName(), employee.getLastName()).anyMatch(property -> property != null)) {
            employee.setInsertBy(String.valueOf(getUserId(request)));
            return employeeRepository.save(employee);
        } else {
            throw new InvalidOperationException("Mandatory value cannot be null", 417);
        }
    }

    @Override
    public void deleteByIds(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> getDataByIds(Long... ids) {
        return employeeRepository.getDataByIds(Arrays.asList(ids));
    }

    @Override
    public List<Employee> getData() {
        return employeeRepository.findAll();
    }


    @Override
    public Object[][] getDataByEmployee() {
        return employeeRepository.getDataByEmployee();
    }


    private boolean areMandatoryFieldsPresent(Employee employee) {
        return Stream.of(employee.getEmpId(), employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getPhoneNumber(), employee.getDepartment(),
                employee.getDesignation(), employee.getGender(), employee.getNid(), employee.getBloodGroup(), employee.getSalaryGrade(), employee.getHireDate())
                .allMatch(Objects::nonNull) &&
                Stream.of(employee.getEmpId(), employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getPhoneNumber(),
                        employee.getGender(), employee.getNid(), employee.getHireDate())
                        .noneMatch(String::isEmpty);
    }

    public Long getUserId(HttpServletRequest request) {
        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = requestTokenHeader.substring(7);
        String username = jwtTokenUtils.getUserNameFromToken(jwtToken);
        SecurityUser userValue = userRepository.findByUsername(username).get();
        return userValue.getUserId();
    }
}
