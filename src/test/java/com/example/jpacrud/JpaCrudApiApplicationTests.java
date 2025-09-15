package com.example.jpacrud;

import com.example.jpacrud.entity.Employee;
import com.example.jpacrud.service.EmployeeService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JpaCrudApiApplicationTests {

    @Autowired
    private EmployeeService service;

    private static Long empId;

    @Test
    @Order(1)
    void contextLoads() {
        Assertions.assertNotNull(service, "Spring context failed to load EmployeeService");
    }

    @Test
    @Order(2)
    void testCreateEmployee() {
        Employee emp = new Employee();
        emp.setName("Achyu");
        emp.setEmail("achyu@example.com");

        Employee saved = service.saveEmployee(emp);
        empId = saved.getId();

        Assertions.assertNotNull(saved.getId(), "Employee ID should not be null after saving");
        Assertions.assertEquals("Achyu", saved.getName());
        Assertions.assertEquals("achyu@example.com", saved.getEmail());
    }

    @Test
    @Order(3)
    void testGetAllEmployees() {
        List<Employee> employees = service.getAllEmployees();
        Assertions.assertFalse(employees.isEmpty(), "Employee list should not be empty");
    }

    @Test
    @Order(4)
    void testGetEmployeeById() {
        Employee emp = service.getEmployeeById(empId);
        Assertions.assertEquals("Achyu", emp.getName());
        Assertions.assertEquals("achyu@example.com", emp.getEmail());
    }

    @Test
    @Order(5)
    void testUpdateEmployee() {
        Employee updated = new Employee();
        updated.setName("Achyu Updated");
        updated.setEmail("achyu.updated@example.com");

        Employee result = service.updateEmployee(empId, updated);
        Assertions.assertEquals("Achyu Updated", result.getName());
        Assertions.assertEquals("achyu.updated@example.com", result.getEmail());
    }

    @Test
    @Order(6)
    void testDeleteEmployee() {
        service.deleteEmployee(empId);

        // After deletion, fetching should throw RuntimeException
        RuntimeException ex = Assertions.assertThrows(RuntimeException.class,
                () -> service.getEmployeeById(empId));
        Assertions.assertTrue(ex.getMessage().contains("Employee not found with id: " + empId));
    }
}
