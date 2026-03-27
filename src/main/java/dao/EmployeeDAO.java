package dao;

import entity.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDAO implements InterfaceDAO<Employee> {
    private final String TABLE_NAME  = "Employees";

    private Employee mapEmployee(ResultSet resultSet) throws SQLException{
        Employee employee = new Employee();
        employee.setId(resultSet.getInt("id"));
        employee.setFirst_name(resultSet.getString("first_name"));
        employee.setLast_name(resultSet.getString("last_name"));
        employee.setEmail(resultSet.getString("email"));
        return employee;
    }


    @Override
    public void save(Connection connection, Employee employee) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " (first_name, last_name, email) VALUES (?, ?, ?)";
        try(PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, employee.getFirst_name());
            ps.setString(2, employee.getLast_name());
            ps.setString(3, employee.getEmail());
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) employee.setId(rs.getInt(1));
            }
        }
    }

    @Override
    public Optional<Employee> findById(Connection connection, int id) throws SQLException {
        String sql = "SELECT id, first_name, last_name, email FROM " + TABLE_NAME + " WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) return Optional.of(mapEmployee(rs));
                else return Optional.empty();
            }
        }
    }

    @Override
    public void update(Connection connection, Employee employee) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET first_name = ?, last_name = ?, email = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, employee.getFirst_name());
            ps.setString(2, employee.getLast_name());
            ps.setString(3, employee.getEmail());
            ps.setInt(4, employee.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteById(Connection connection, int id) throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public void saveAll(Connection connection, List<Employee> employees) throws SQLException{
        String sql = "INSERT INTO " + TABLE_NAME + " (first_name, last_name, email) VALUES (?, ?, ?)";
        try(PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (Employee e : employees){
                ps.setString(1, e.getFirst_name());
                ps.setString(2, e.getLast_name());
                ps.setString(3, e.getEmail());
                ps.addBatch();
            }
            ps.executeBatch();
            try(ResultSet rs = ps.getGeneratedKeys()){
                for (Employee e : employees){
                    if (rs.next()){
                        e.setId(rs.getInt(1));
                    }
                }
            }
        }
    }

    @Override
    public List<Employee> findAll(Connection connection) throws SQLException {
        String sql = "SELECT id, first_name, last_name, email FROM " + TABLE_NAME;
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            try(ResultSet rs = ps.executeQuery()){
                List<Employee> employees = new ArrayList<>();
                while (rs.next()){
                    employees.add(mapEmployee(rs));
                }
                return employees;
            }
        }
    }

    @Override
    public void deleteAll(Connection connection) throws SQLException{
        String sql = "TRUNCATE TABLE " + TABLE_NAME;
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }

    @Override
    public long count(Connection connection) throws SQLException{
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
        PreparedStatement ps = connection.prepareStatement(sql);
        try(ResultSet rs = ps.executeQuery()) {
            if(rs.next()){
                return rs.getInt(1);
            }
            else return 0;
        }
    }

}
