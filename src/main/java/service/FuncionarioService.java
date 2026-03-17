package service;

import dao.FuncionarioDAO;
import database.ConnectionFactory;
import entity.Funcionario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FuncionarioService {
    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    public void cadastrarFuncionario(Funcionario funcionario){
        try (Connection connection = ConnectionFactory.openConnection()){
            funcionarioDAO.insertFuncionario(connection, funcionario);
        }catch (SQLException ex){
            System.out.println("Não foi possível cadastrar usuário.");
            ex.printStackTrace();
        }
    }

    public void cadastrarListaFuncionarios(List<Funcionario> funcionarioList){
        try(Connection connection = ConnectionFactory.openConnection()) {
            funcionarioDAO.insertFuncionarioBatch(connection, funcionarioList);
        } catch (SQLException ex){
            System.out.println("Não foi possível cadastrar lista de funcionários.");
            ex.printStackTrace();
        }
    }

    public Optional<Funcionario> consultarFuncionario(int id){
        try(Connection connection = ConnectionFactory.openConnection()) {
            return Optional.ofNullable(funcionarioDAO.getFuncionario(connection, id));
        } catch (SQLException ex){
            System.out.println("Não foi possível realizar consulta");
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    public List<Funcionario> consultarListaFuncionarios(){
        try(Connection connection = ConnectionFactory.openConnection()){
            return funcionarioDAO.getFuncionarios(connection);
        } catch (SQLException ex){
            System.out.println("Não foi possível realizar consulta");
            ex.printStackTrace();
            return List.of();
        }
    }

    public boolean atualizarFuncionario(Funcionario funcionario){
        try(Connection connection = ConnectionFactory.openConnection()) {
            return funcionarioDAO.updateFuncionario(connection, funcionario);
        } catch (SQLException ex){
            System.out.println("Não foi possível atualizar dados.");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deletarFuncionario(int id){
        try(Connection connection = ConnectionFactory.openConnection()) {
            return funcionarioDAO.deleteFuncionario(connection, id);
        } catch (SQLException ex){
            System.out.println("Não foi possível realizar operação de exclusão.");
            ex.printStackTrace();
            return false;
        }
    }

}
