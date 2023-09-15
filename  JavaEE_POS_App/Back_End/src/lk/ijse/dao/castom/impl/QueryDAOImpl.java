package lk.ijse.dao.castom.impl;

import lk.ijse.dao.castom.QueryDAO;
import lk.ijse.entity.CustomEntity;

import java.sql.Connection;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public boolean save(CustomEntity entity, Connection connection) {
        return false;
    }
}
