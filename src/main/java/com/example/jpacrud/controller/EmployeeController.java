package com.example.jpacrud.controller;

import com.example.jpacrud.entity.Employee;
import com.example.jpacrud.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private static final Logger logger = LogManager.getLogger(EmployeeController.class);

    @Autowired
//    private EmployeeJDBCService service;
    private EmployeeService service;

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        logger.info("Received request to create employee: {}", employee);
        Employee saved = service.saveEmployee(employee);
        logger.debug("Employee created: {}", saved);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        logger.info("Fetching all employees");
        List<Employee> employees = service.getAllEmployees();
        logger.debug("Total employees fetched: {}", employees.size());
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        logger.info("Fetching employee with ID: {}", id);
        Employee employee = service.getEmployeeById(id);
        logger.debug("Employee fetched: {}", employee);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee employee) {
        logger.info("Updating employee with ID: {}", id);
        logger.debug("Update payload: {}", employee);
        Employee updated = service.updateEmployee(id, employee);
        logger.debug("Employee updated: {}", updated);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        logger.info("Deleting employee with ID: {}", id);
        service.deleteEmployee(id);
        logger.debug("Employee deleted successfully");
        return ResponseEntity.ok("Employee deleted successfully");
    }
}
