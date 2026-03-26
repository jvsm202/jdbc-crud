package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface InterfaceDAO<T> {
    void save(Connection connection, T object) throws SQLException;
    Optional<T> findById(Connection connection, int id) throws SQLException;
    void update(Connection connection, T object) throws SQLException;
    void deleteById(Connection connection, int id) throws SQLException;

    void saveAll(Connection connection, Collection<T> objects) throws SQLException;
    Collection<T> findAll(Connection connection) throws SQLException;
    void deleteAll(Connection connection) throws SQLException;
    long count(Connection connection) throws SQLException;
}
