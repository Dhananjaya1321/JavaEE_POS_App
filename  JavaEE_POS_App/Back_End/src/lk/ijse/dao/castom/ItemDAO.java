package lk.ijse.dao.castom;

import lk.ijse.entity.Item;
import lk.ijse.util.SQLUtil;

import java.sql.Connection;
import java.sql.SQLException;

public interface ItemDAO extends SQLUtil<Item> {
    Item getItem(Item to, Connection connection) throws SQLException;
}
