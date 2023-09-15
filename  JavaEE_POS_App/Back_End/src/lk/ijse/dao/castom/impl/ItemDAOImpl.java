package lk.ijse.dao.castom.impl;

import lk.ijse.dao.castom.ItemDAO;
import lk.ijse.entity.Item;
import lk.ijse.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean add(Item to, Connection connection) throws SQLException {
        return CrudUtil.setQuery(connection,"INSERT INTO item VALUES (?,?,?,?)", to.getCode(), to.getName(), to.getPrice(), to.getQty());
    }

    @Override
    public boolean update(Item to,Connection connection) throws SQLException {
        return CrudUtil.setQuery(connection,"UPDATE item SET name=?,price=?,qty=? WHERE code=?", to.getName(), to.getPrice(), to.getQty(), to.getCode());
    }

    @Override
    public boolean delete(Item to,Connection connection) throws SQLException {
        return CrudUtil.setQuery(connection,"DELETE FROM item WHERE code=? ", to.getCode());
    }

    @Override
    public ArrayList<Item> getAll(Connection connection) throws SQLException {
        ResultSet resultSet = CrudUtil.setQuery(connection,"SELECT * FROM item");
        ArrayList<Item> items=new ArrayList<>();
        while (resultSet.next()){
            items.add(new Item(resultSet.getString(1),resultSet.getString(2),Double.parseDouble(resultSet.getString(3)),Integer.parseInt(resultSet.getString(4))));
        }
        return items;
    }
}
