package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    private EmployeeDTO mapToDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getSalary()
        );
    }

    private Employee mapToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setDepartment(employeeDTO.getDepartment());
        employee.setSalary(employeeDTO.getSalary());

        return employee;
    }

    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {

        Employee employee = mapToEntity(employeeDTO);

        Employee savedEmployee = employeeRepository.save(employee);

        return mapToDTO(savedEmployee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElse(null);

        return employee != null ? mapToDTO(employee) : null;
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {

        Employee existingEmployee = employeeRepository.findById(id)
                .orElse(null);

        if (existingEmployee != null) {

            existingEmployee.setName(employeeDTO.getName());
            existingEmployee.setEmail(employeeDTO.getEmail());
            existingEmployee.setDepartment(employeeDTO.getDepartment());
            existingEmployee.setSalary(employeeDTO.getSalary());

            Employee updatedEmployee = employeeRepository.save(existingEmployee);

            return mapToDTO(updatedEmployee);
        }

        return null;
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Page<EmployeeDTO> getEmployees(int page, int size) {

        Page<Employee> employeePage =
                employeeRepository.findAll(PageRequest.of(page, size));

        return employeePage.map(this::mapToDTO);
    }

    @Override
    public List<EmployeeDTO> getEmployeesSorted(String field) {

        return employeeRepository
                .findAll(Sort.by(Sort.Direction.ASC, field))
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}