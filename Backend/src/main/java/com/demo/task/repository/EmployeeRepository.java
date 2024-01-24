package com.demo.task.repository;

import com.demo.task.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.id IN(:ids)")
    List<Employee> getDataByIds(@Param("ids") List<Long> ids);

    @Query(value = "SELECT e.id, CONCAT(e.firstName, ' ', e.lastName) AS name FROM Employee e")
    Object[][] getDataByEmployee();


}
