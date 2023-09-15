package lk.ijse.bo.castom.impl;

import lk.ijse.bo.castom.CustomerBO;
import lk.ijse.dao.DAOTypes;
import lk.ijse.dao.FactoryDAO;
import lk.ijse.dao.castom.impl.CustomerDAOImpl;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    private final CustomerDAOImpl customerDAO = (CustomerDAOImpl) FactoryDAO.getFactoryDAO().getInstance(DAOTypes.CUSTOMER);

    @Override
    public boolean addCustomer(CustomerDTO dto, Connection connection) throws SQLException {
        return customerDAO.add(new Customer(dto.getNic(), dto.getName(), dto.getTel(), dto.getAddress()),connection);
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto,Connection connection) throws SQLException {
        return customerDAO.update(new Customer(dto.getNic(), dto.getName(), dto.getTel(), dto.getAddress()),connection);
    }

    @Override
    public boolean deleteCustomer(CustomerDTO dto,Connection connection) throws SQLException {
        return customerDAO.delete(new Customer(dto.getNic()),connection);
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException {
        ArrayList<Customer> all = customerDAO.getAll(connection);
        ArrayList<CustomerDTO> customerDTOS=new ArrayList<>();
        for (Customer c:all) {
            customerDTOS.add(new CustomerDTO(c.getNic(),c.getName(),c.getTel(),c.getAddress()));
        }
        return customerDTOS;
    }
}
