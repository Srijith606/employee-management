package com.example.employeemanagement.controller;
import jakarta.validation.Valid;
import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public EmployeeDTO createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.saveEmployee(employeeDTO);
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/{id}")
    public EmployeeDTO updateEmployee(@PathVariable Long id,
                                      @Valid @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.updateEmployee(id, employeeDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}