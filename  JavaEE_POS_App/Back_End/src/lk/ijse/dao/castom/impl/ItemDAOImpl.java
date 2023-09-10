package lk.ijse.dao.castom.impl;

import lk.ijse.dao.castom.ItemDAO;
import lk.ijse.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean add(Item to) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Item to) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(Item to) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<Item> getAll() {
        return null;
    }
}
