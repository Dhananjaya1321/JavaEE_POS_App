package lk.ijse.dao.castom.impl;

import lk.ijse.dao.castom.OrderDAO;
import lk.ijse.entity.CustomEntity;
import lk.ijse.util.CrudUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean add(CustomEntity to, Connection connection) throws SQLException {
        return CrudUtil.setQuery(connection, "INSERT INTO orders VALUES (?,?,?,?,?,?,?,?)");
    }

    @Override
    public boolean update(CustomEntity to, Connection connection) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(CustomEntity to, Connection connection) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<CustomEntity> getAll(Connection connection) throws SQLException {
        return null;
    }
}
