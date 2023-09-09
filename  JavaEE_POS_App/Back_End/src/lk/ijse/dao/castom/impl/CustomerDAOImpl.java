package lk.ijse.dao.castom.impl;

import lk.ijse.dao.castom.CustomerDAO;
import lk.ijse.entity.Customer;
import lk.ijse.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean add(Customer to) throws SQLException {
        return CrudUtil.setQuery("INSERT INTO customer VALUES (?,?,?,?)", to.getNic(), to.getName(), to.getTel(), to.getAddress());
    }

    @Override
    public boolean update(Customer to) throws SQLException {
        return CrudUtil.setQuery("UPDATE customer SET name=?,tel=?,address=? WHERE nic=?", to.getName(), to.getTel(), to.getAddress(), to.getNic());
    }

    @Override
    public boolean delete(Customer to) throws SQLException {
        return CrudUtil.setQuery("DELETE FROM customer WHERE nic=?", to.getNic());
    }

    @Override
    public ArrayList<Customer> getAll() {
        return null;
    }
}
