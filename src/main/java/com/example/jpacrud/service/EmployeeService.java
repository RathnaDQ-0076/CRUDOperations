package com.example.jpacrud.service;

import com.example.jpacrud.entity.Employee;
import com.example.jpacrud.repository.EmployeeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private static final Logger logger = LogManager.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository repository;

    // Create
    public Employee saveEmployee(Employee employee) {
        logger.info("Saving new employee: {}", employee);
        Employee saved = repository.save(employee);
        logger.debug("Employee saved: {}", saved);
        return saved;
    }

    // Read All
    public List<Employee> getAllEmployees() {
        logger.info("Fetching all employees");
        List<Employee> employees = repository.findAll();
        logger.debug("Total employees fetched: {}", employees.size());
        return employees;
    }

    // Read By Id
    public Employee getEmployeeById(Long id) {
        logger.info("Fetching employee with ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Employee not found with ID: {}", id);
                    return new RuntimeException("Employee not found with id: " + id);
                });
    }

    // Update
    public Employee updateEmployee(Long id, Employee employee) {
        logger.info("Updating employee with ID: {}", id);
        Employee existing = getEmployeeById(id);
        existing.setName(employee.getName());
        existing.setEmail(employee.getEmail());
        Employee updated = repository.save(existing);
        logger.debug("Employee updated: {}", updated);
        return updated;
    }

    // Delete
    public void deleteEmployee(Long id) {
        logger.info("Deleting employee with ID: {}", id);
        Employee existing = getEmployeeById(id);
        repository.delete(existing);
        logger.debug("Employee deleted successfully");
    }
}
