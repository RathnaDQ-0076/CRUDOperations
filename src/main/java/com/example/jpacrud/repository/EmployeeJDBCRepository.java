package com.example.jpacrud.repository;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.jpacrud.entity.Employee;

@Repository
public class EmployeeJDBCRepository {
	

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

    private  RowMapper<Employee> rowMapper = new RowMapper<>() {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee emp = new Employee();
            emp.setId(rs.getLong("id"));
            emp.setName(rs.getString("name"));
            emp.setEmail(rs.getString("email"));
            return emp;
        }
    };

    // Create
    public int save(Employee employee) {
        return jdbcTemplate.update(insertQuery, employee.getName(), employee.getEmail());
    }

    // Read All
    public List<Employee> findAll() {
        return jdbcTemplate.query(selectAllQuery, rowMapper);
    }

    // Read By Id
    public Employee findById(Long id) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject(selectByIdQuery, rowMapper, id);
    }

    // Update
    public int update(Long id, Employee employee) {
        return jdbcTemplate.update(updateQuery, employee.getName(), employee.getEmail(), id);
    }

    // Delete
    public int delete(Long id) {
        return jdbcTemplate.update(deleteQuery, id);
    }
}
