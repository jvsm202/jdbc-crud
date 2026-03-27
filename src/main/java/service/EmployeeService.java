package service;

import dao.EmployeeDAO;
import database.ConnectionFactory;
import entity.Employee;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeeService {
    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    public void save(Employee employee){
        try (Connection connection = ConnectionFactory.openConnection()){
            employeeDAO.save(connection, employee);
        }catch (SQLException ex){
            System.out.println("Failed to create user.");
            ex.printStackTrace();
        }
    }

    public Optional<Employee> findById(int id){
        try(Connection connection = ConnectionFactory.openConnection()) {
            return employeeDAO.findById(connection, id);
        } catch (SQLException ex){
            System.out.println("Failed to execute query.");
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    public void update(Employee employee){
        try(Connection connection = ConnectionFactory.openConnection()) {
            employeeDAO.update(connection, employee);
        }
        catch (SQLException ex){
            System.out.println("Failed to update.");
            ex.printStackTrace();
        }
    }

    public void deleteById(int id){
        try(Connection connection = ConnectionFactory.openConnection()) {
            employeeDAO.deleteById(connection, id);
        }
        catch (SQLException ex){
            System.out.println("Failed to delete.");
            ex.printStackTrace();
        }
    }

    public void saveAll(List<Employee> employeeList){
        try(Connection connection = ConnectionFactory.openConnection()) {
            employeeDAO.saveAll(connection, employeeList);
        } catch (SQLException ex){
            System.out.println("Failed to save.");
            ex.printStackTrace();
        }
    }

    public List<Employee> findAll(){
        try(Connection connection = ConnectionFactory.openConnection()){
            return employeeDAO.findAll(connection);
        } catch (SQLException ex){
            System.out.println("Failed to execute query.");
            ex.printStackTrace();
            return List.of();
        }
    }

    public void deleteAll(){
        try(Connection connection = ConnectionFactory.openConnection()){
            employeeDAO.deleteAll(connection);
        } catch (SQLException ex){
            System.out.println("Failed to delete.");
            ex.printStackTrace();
        }
    }

    public long count(){
        try(Connection connection = ConnectionFactory.openConnection()){
            return employeeDAO.count(connection);
        } catch (SQLException ex){
            System.out.println("Failed to retrieve employees.");
            ex.printStackTrace();
            return -1;
        }
    }

}
