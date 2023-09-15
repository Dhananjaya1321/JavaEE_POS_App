package lk.ijse.dao.castom.impl;

import lk.ijse.dao.castom.QueryDAO;
import lk.ijse.entity.CustomEntity;
import lk.ijse.util.CrudUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public boolean save(CustomEntity entity, Connection connection) throws SQLException {
        return CrudUtil.setQuery(connection,"INSERT INTO orderdetails VALUES (?,?,?,?)",entity.getOrderId(),entity.getCode(),entity.getQty(),entity.getPrice());
    }
}
