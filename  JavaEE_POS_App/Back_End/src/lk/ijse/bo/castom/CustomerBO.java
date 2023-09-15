package lk.ijse.bo.castom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    boolean addCustomer(CustomerDTO dto, Connection connection) throws SQLException;

    boolean updateCustomer(CustomerDTO dto,Connection connection) throws SQLException;

    boolean deleteCustomer(CustomerDTO dto,Connection connection) throws SQLException;

    ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException;
}
