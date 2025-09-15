package com.example.jpacrud.service;

import com.example.jpacrud.entity.Employee;
import com.example.jpacrud.repository.EmployeeJDBCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeJDBCService {

    @Autowired
    private EmployeeJDBCRepository repository;

    // Create
    public Employee saveEmployee(Employee employee) {
        int rows = repository.save(employee);
        if (rows > 0) {
            // Return the same object since JDBC doesn't auto-fetch inserted record
            return employee;
        } else {
            throw new RuntimeException("Failed to insert employee");
        }
    }

    // Read All
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    // Read By Id
    public Employee getEmployeeById(Long id) {
        try {
            return repository.findById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }

    // Update
    public Employee updateEmployee(Long id, Employee employee) {
        getEmployeeById(id); // Validate existence
        int rows = repository.update(id, employee);
        if (rows > 0) {
            return employee;
        } else {
            throw new RuntimeException("Failed to update employee with id: " + id);
        }
    }

    // Delete
    public void deleteEmployee(Long id) {
        getEmployeeById(id); // Validate existence
        int rows = repository.delete(id);
        if (rows == 0) {
            throw new RuntimeException("Failed to delete employee with id: " + id);
        }
    }
}
