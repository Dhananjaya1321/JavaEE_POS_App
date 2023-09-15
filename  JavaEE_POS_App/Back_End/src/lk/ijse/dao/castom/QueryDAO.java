package lk.ijse.dao.castom;

import lk.ijse.dao.SuperDAO;
import lk.ijse.entity.CustomEntity;

import java.sql.Connection;
import java.sql.SQLException;

public interface QueryDAO extends SuperDAO {
    boolean save(CustomEntity entity, Connection connection) throws SQLException;
}
