package service;

import dao.FuncionarioDAO;
import database.ConnectionFactory;
import entity.Funcionario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class FuncionarioService {
    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    public void saveFuncionario(Funcionario funcionario){
        try (Connection connection = ConnectionFactory.openConnection()){
            funcionarioDAO.save(connection, funcionario);
        }catch (SQLException ex){
            System.out.println("Não foi possível cadastrar usuário.");
            ex.printStackTrace();
        }
    }

    public Optional<Funcionario> findFuncionarioById(int id){
        try(Connection connection = ConnectionFactory.openConnection()) {
            return funcionarioDAO.findById(connection, id);
        } catch (SQLException ex){
            System.out.println("Não foi possível realizar consulta");
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    public void updateFuncionario(Funcionario funcionario){
        try(Connection connection = ConnectionFactory.openConnection()) {
            funcionarioDAO.update(connection, funcionario);
        }
        catch (SQLException ex){
            System.out.println("Não foi possível atualizar dados.");
            ex.printStackTrace();
        }
    }

    public void deleteFuncionarioById(int id){
        try(Connection connection = ConnectionFactory.openConnection()) {
            funcionarioDAO.deleteById(connection, id);
        }
        catch (SQLException ex){
            System.out.println("Não foi possível realizar operação de exclusão.");
            ex.printStackTrace();
        }
    }

    public void saveAllFuncionarios(List<Funcionario> funcionarioList){
        try(Connection connection = ConnectionFactory.openConnection()) {
            funcionarioDAO.saveAll(connection, funcionarioList);
        } catch (SQLException ex){
            System.out.println("Não foi possível cadastrar lista de funcionários.");
            ex.printStackTrace();
        }
    }

    public Collection<Funcionario> findAllFuncionarios(){
        try(Connection connection = ConnectionFactory.openConnection()){
            return funcionarioDAO.findAll(connection);
        } catch (SQLException ex){
            System.out.println("Não foi possível realizar consulta");
            ex.printStackTrace();
            return List.of();
        }
    }

    public void deleteAllFuncionarios(){
        try(Connection connection = ConnectionFactory.openConnection()){
            funcionarioDAO.deleteAll(connection);
        } catch (SQLException ex){
            System.out.println("Não foi possível deletar funcionarios");
            ex.printStackTrace();
        }
    }

    public long countFuncionarios(){
        try(Connection connection = ConnectionFactory.openConnection()){
            return funcionarioDAO.count(connection);
        } catch (SQLException ex){
            System.out.println("Não foi possível listar todos os funcionarios.");
            ex.printStackTrace();
            return -1;
        }
    }

}
