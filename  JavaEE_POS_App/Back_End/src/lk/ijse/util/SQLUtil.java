package lk.ijse.util;

import lk.ijse.dao.SuperDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface SQLUtil<T> extends SuperDAO {
    boolean add(T to,Connection connection) throws SQLException;
    boolean update(T to,Connection connection) throws SQLException;
    boolean delete(T to, Connection connection) throws SQLException;
    ArrayList<T> getAll(Connection connection) throws SQLException;
}
