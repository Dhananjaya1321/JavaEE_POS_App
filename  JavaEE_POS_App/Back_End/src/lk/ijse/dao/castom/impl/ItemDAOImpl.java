package lk.ijse.dao.castom.impl;

import lk.ijse.dao.castom.ItemDAO;
import lk.ijse.entity.Item;
import lk.ijse.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean add(Item to) throws SQLException {
        return CrudUtil.setQuery("INSERT INTO item VALUES (?,?,?,?)", to.getCode(), to.getName(), to.getPrice(), to.getQty());
    }

    @Override
    public boolean update(Item to) throws SQLException {
        return CrudUtil.setQuery("UPDATE item SET name=?,price=?,qty=? WHERE code=?", to.getName(), to.getPrice(), to.getQty(), to.getCode());
    }

    @Override
    public boolean delete(Item to) throws SQLException {
        return CrudUtil.setQuery("DELETE FROM item WHERE code=? ", to.getCode());
    }

    @Override
    public ArrayList<Item> getAll() {
        return null;
    }
}
