package com.example.employeemanagement.repository;

import com.example.employeemanagement.entity.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
}
