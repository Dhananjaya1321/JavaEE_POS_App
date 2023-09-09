package lk.ijse.bo.castom.impl;

import lk.ijse.bo.castom.CustomerBO;
import lk.ijse.dao.DAOTypes;
import lk.ijse.dao.FactoryDAO;
import lk.ijse.dao.castom.impl.CustomerDAOImpl;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.entity.Customer;

import java.sql.SQLException;

public class CustomerBOImpl implements CustomerBO {
    private final CustomerDAOImpl customerDAO = (CustomerDAOImpl) FactoryDAO.getFactoryDAO().getInstance(DAOTypes.CUSTOMER);
    public boolean addCustomer(CustomerDTO dto) throws SQLException {
        return customerDAO.add(new Customer(dto.getNic(), dto.getName(), dto.getTel(), dto.getAddress()));
    }
}
