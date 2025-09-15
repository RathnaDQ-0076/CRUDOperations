package com.example.jpacrud.repository;

import com.example.jpacrud.entity.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmployeeJDBCRepository {

    private static final Logger logger = LogManager.getLogger(EmployeeJDBCRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${jdbc.employee.insert}")
    private String insertQuery;

    @Value("${jdbc.employee.select_all}")
    private String selectAllQuery;

    @Value("${jdbc.employee.select_by_id}")
    private String selectByIdQuery;

    @Value("${jdbc.employee.update}")
    private String updateQuery;

    @Value("${jdbc.employee.delete}")
    private String deleteQuery;

    private final RowMapper<Employee> rowMapper = new RowMapper<>() {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee emp = new Employee();
            emp.setId(rs.getLong("id"));
            emp.setName(rs.getString("name"));
            emp.setEmail(rs.getString("email"));
            logger.debug("Mapped row to Employee: {}", emp);
            return emp;
        }
    };

    // Create
    public int save(Employee employee) {
        logger.info("Executing insert for employee: {}", employee);
        int rows = jdbcTemplate.update(insertQuery, employee.getName(), employee.getEmail());
        logger.debug("Insert completed, rows affected: {}", rows);
        return rows;
    }

    // Read All
    public List<Employee> findAll() {
        logger.info("Fetching all employees");
        List<Employee> employees = jdbcTemplate.query(selectAllQuery, rowMapper);
        logger.debug("Total employees fetched: {}", employees.size());
        return employees;
    }

    // Read By Id
    public Employee findById(Long id) throws EmptyResultDataAccessException {
        logger.info("Fetching employee with ID: {}", id);
        Employee employee = jdbcTemplate.queryForObject(selectByIdQuery, rowMapper, id);
        logger.debug("Employee fetched: {}", employee);
        return employee;
    }

    // Update
    public int update(Long id, Employee employee) {
        logger.info("Updating employee with ID: {}", id);
        logger.debug("Update payload: {}", employee);
        int rows = jdbcTemplate.update(updateQuery, employee.getName(), employee.getEmail(), id);
        logger.debug("Update completed, rows affected: {}", rows);
        return rows;
    }

    // Delete
    public int delete(Long id) {
        logger.info("Deleting employee with ID: {}", id);
        int rows = jdbcTemplate.update(deleteQuery, id);
        logger.debug("Delete completed, rows affected: {}", rows);
        return rows;
    }
}
