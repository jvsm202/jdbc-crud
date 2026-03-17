package dao;

import entity.Funcionario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    private Funcionario mapFuncionario(ResultSet resultSet) throws SQLException{
        Funcionario funcionario = new Funcionario();
        funcionario.setId(resultSet.getInt("id"));
        funcionario.setNome(resultSet.getString("nome"));
        funcionario.setEmail(resultSet.getString("email"));
        return funcionario;
    }

    public void insertFuncionario(Connection connection, Funcionario funcionario) throws SQLException {
        String sql = "INSERT INTO funcionario (nome, email) VALUES (?, ?)";
        try(PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getEmail());
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) funcionario.setId(rs.getInt(1));
            }
        }
    }

    public void insertFuncionarioBatch(Connection connection, List<Funcionario> funcionarios) throws SQLException{
        String sql = "INSERT INTO funcionario (nome, email) VALUES (?, ?)";
        try(PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (Funcionario f : funcionarios){
                ps.setString(1, f.getNome());
                ps.setString(2, f.getEmail());
                ps.addBatch();
            }
            ps.executeBatch();
            try(ResultSet rs = ps.getGeneratedKeys()){
                for (Funcionario f : funcionarios){
                    if (rs.next()){
                        f.setId(rs.getInt(1));
                    }
                }
            }
        }
    }

    public Funcionario getFuncionario(Connection connection, int id) throws SQLException {
        String sql = "SELECT id, nome, email FROM funcionario WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) return mapFuncionario(rs);
                return null;
            }
        }
    }

    public List<Funcionario> getFuncionarios(Connection connection) throws SQLException {
        String sql = "SELECT id, nome, email FROM funcionario";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            try(ResultSet rs = ps.executeQuery()){
                List<Funcionario> funcionarios = new ArrayList<>();
                while (rs.next()){
                    funcionarios.add(mapFuncionario(rs));
                }
                return funcionarios;
            }
        }
    }

    public boolean updateFuncionario(Connection connection, Funcionario funcionario) throws SQLException {
        String sql = "UPDATE funcionario SET nome = ?, email = ? WHERE id = ? ";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getEmail());
            ps.setInt(3, funcionario.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteFuncionario(Connection connection, int id) throws SQLException {
        String sql = "DELETE FROM funcionario WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
