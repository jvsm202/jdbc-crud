package dao;

import entity.Funcionario;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class FuncionarioDAO implements InterfaceDAO<Funcionario> {
    private final String TABLE_NAME  = "funcionario";

    private Funcionario mapFuncionario(ResultSet resultSet) throws SQLException{
        Funcionario funcionario = new Funcionario();
        funcionario.setId(resultSet.getInt("id"));
        funcionario.setNome(resultSet.getString("nome"));
        funcionario.setEmail(resultSet.getString("email"));
        return funcionario;
    }

    @Override
    public void save(Connection connection, Funcionario funcionario) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " (nome, email) VALUES (?, ?)";
        try(PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getEmail());
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) funcionario.setId(rs.getInt(1));
            }
        }
    }

    @Override
    public Optional<Funcionario> findById(Connection connection, int id) throws SQLException {
        String sql = "SELECT id, nome, email FROM " + TABLE_NAME + " WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) return Optional.of(mapFuncionario(rs));
                else return Optional.empty();
            }
        }
    }

    @Override
    public void update(Connection connection, Funcionario funcionario) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET nome = ?, email = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getEmail());
            ps.setInt(3, funcionario.getId());
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
    public void saveAll(Connection connection, Collection<Funcionario> funcionarios) throws SQLException{
        String sql = "INSERT INTO " + TABLE_NAME + " (nome, email) VALUES (?, ?)";
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

    @Override
    public Collection<Funcionario> findAll(Connection connection) throws SQLException {
        String sql = "SELECT id, nome, email FROM " + TABLE_NAME;
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
