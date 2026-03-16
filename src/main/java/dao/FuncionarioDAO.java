package dao;

import entity.Funcionario;
import database.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FuncionarioDAO {

    private Funcionario mapFuncionario(ResultSet resultSet) throws SQLException{
        Funcionario funcionario = new Funcionario();
        funcionario.setId(resultSet.getInt("id"));
        funcionario.setNome(resultSet.getString("nome"));
        funcionario.setEmail(resultSet.getString("email"));
        return funcionario;
    }

    public boolean cadastrarFuncionario(Funcionario funcionario){
        String sql = "INSERT INTO funcionario (nome, email) VALUES (?, ?)";
        try(Connection conn = ConnectionFactory.createConnection(); PreparedStatement ps =
                conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getEmail());
            ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()) {
                // rs começa antes da primeira coluna. O método next() o move para a primeira.
                if (rs.next()) {
                    // Pegando o id (é a única coluna do ResultSet, já que ele só pegou as chaves feradas).
                    funcionario.setId(rs.getInt(1));
                }
            }
            return true;
        }catch (SQLException ex){
            System.out.println("Não foi possível Cadastrar Funcionário.");
            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Email: " + funcionario.getEmail());
            ex.printStackTrace();
            return false;
        }
    }

    public Funcionario getFuncionario(int id){
        String sql = "SELECT id, nome, email FROM funcionario WHERE id = ?";
        try(Connection conn = ConnectionFactory.createConnection(); PreparedStatement ps =
                conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return mapFuncionario(rs);
                }
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Não foi possível selecionar usuário com id " + id);
            ex.printStackTrace();
            return null;
        }
    }

    public List<Funcionario> getFuncionarios(){
        String sql = "SELECT id, nome, email FROM funcionario";
        List<Funcionario> funcionarios = new ArrayList<>();
        try(Connection conn = ConnectionFactory.createConnection(); PreparedStatement ps =
                conn.prepareStatement(sql)) {
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    funcionarios.add(mapFuncionario(rs));
                }
            }
        }catch (SQLException ex){
            System.out.println("Não foi possível retornar a lista de funcionários");
            ex.printStackTrace();
        }
        return funcionarios;
    }

    public boolean updateFuncionario(Funcionario funcionario){
        String sql = "UPDATE funcionario SET nome = ?, email = ? WHERE id = ? ";
        int linhasAfetadas = 0;
        try(Connection conn = ConnectionFactory.createConnection(); PreparedStatement ps =
                conn.prepareStatement(sql)) {
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getEmail());
            ps.setInt(3, funcionario.getId());
            linhasAfetadas = ps.executeUpdate();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return linhasAfetadas > 0;
    }



    public boolean deleteFuncionario(int id){
        String sql = "DELETE FROM funcionario WHERE id = ?";
        int linhasAfetadas = 0;
        try(Connection conn = ConnectionFactory.createConnection(); PreparedStatement ps =
                conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            linhasAfetadas = ps.executeUpdate();
        }catch(SQLException ex){
            System.out.println("Não foi possível completar a operação de deletar.");
            ex.printStackTrace();
        }
        return linhasAfetadas > 0;
    }

}
