package com.example.jpacrud.service;

import com.example.jpacrud.entity.Employee;
import com.example.jpacrud.repository.EmployeeJDBCRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeJDBCService {

    private static final Logger logger = LogManager.getLogger(EmployeeJDBCService.class);

    @Autowired
    private EmployeeJDBCRepository repository;

    // Create
    public Employee saveEmployee(Employee employee) {
        logger.info("Attempting to insert employee: {}", employee);
        int rows = repository.save(employee);
        if (rows > 0) {
            logger.debug("Insert successful, rows affected: {}", rows);
            return employee;
        } else {
            logger.error("Insert failed for employee: {}", employee);
            throw new RuntimeException("Failed to insert employee");
        }
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
        try {
            Employee employee = repository.findById(id);
            logger.debug("Employee found: {}", employee);
            return employee;
        } catch (EmptyResultDataAccessException ex) {
            logger.warn("Employee not found with ID: {}", id);
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }

    // Update
    public Employee updateEmployee(Long id, Employee employee) {
        logger.info("Updating employee with ID: {}", id);
        getEmployeeById(id); // Validate existence
        int rows = repository.update(id, employee);
        if (rows > 0) {
            logger.debug("Update successful for ID: {}", id);
            return employee;
        } else {
            logger.error("Update failed for employee with ID: {}", id);
            throw new RuntimeException("Failed to update employee with id: " + id);
        }
    }

    // Delete
    public void deleteEmployee(Long id) {
        logger.info("Deleting employee with ID: {}", id);
        getEmployeeById(id); // Validate existence
        int rows = repository.delete(id);
        if (rows == 0) {
            logger.error("Delete failed for employee with ID: {}", id);
            throw new RuntimeException("Failed to delete employee with id: " + id);
        } else {
            logger.debug("Delete successful for ID: {}", id);
        }
    }
}
