package lk.ijse.bo.castom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    boolean addItem(ItemDTO obj,Connection connection) throws SQLException;

    boolean updateItem(ItemDTO dto,Connection connection) throws SQLException;

    boolean deleteItem(ItemDTO dto,Connection connection) throws SQLException;

    ArrayList<ItemDTO> getAllItems(Connection connection) throws SQLException;

    ItemDTO getItem(ItemDTO dto, Connection connection) throws SQLException;
}
