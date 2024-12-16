package com.example.demo.dao;

import com.example.demo.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmployeeDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setId(rs.getLong("id"));
            employee.setFirstName(rs.getString("first_name"));
            employee.setLastName(rs.getString("last_name"));
            employee.setSalary(rs.getInt("salary"));
            employee.setAddress(rs.getString("address"));
            employee.setPhone(rs.getString("phone"));
            return employee;
        }
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        String sql = "SELECT * FROM employee";
        try {
            return jdbcTemplate.query(sql, new EmployeeRowMapper());
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving employees", e);
        }
    }

    // Add a new employee
    public int addEmployee(Employee employee) {
        String sql = "INSERT INTO employee (first_name, last_name, salary, address, phone) VALUES (?, ?, ?, ?, ?)";
        try {
            return jdbcTemplate.update(sql, employee.getFirstName(), employee.getLastName(),
                    employee.getSalary(), employee.getAddress(), employee.getPhone());
        } catch (Exception e) {
            throw new RuntimeException("Error adding employee", e);
        }
    }

    // Update an existing employee
    public int update(Employee employee) {
        String sql = "UPDATE employee SET first_name = ?, last_name = ?, salary = ?, address = ?, phone = ? WHERE id = ?";
        try {
            return jdbcTemplate.update(sql, employee.getFirstName(), employee.getLastName(),
                    employee.getSalary(), employee.getAddress(), employee.getPhone(), employee.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error updating employee", e);
        }
    }

    // Delete an employee by ID
    public int delete(Long id) {
        String sql = "DELETE FROM employee WHERE id = ?";
        try {
            return jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting employee", e);
        }
    }

    // Find an employee by ID
    public Employee findById(Long id) {
        String sql = "SELECT * FROM employee WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new EmployeeRowMapper());
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving employee by ID", e);
        }
    }
}
