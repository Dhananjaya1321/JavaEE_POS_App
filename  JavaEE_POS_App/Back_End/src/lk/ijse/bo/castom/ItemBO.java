package lk.ijse.bo.castom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.ItemDTO;

import java.sql.SQLException;

public interface ItemBO extends SuperBO {
    boolean addItem(ItemDTO obj) throws SQLException;

    boolean updateItem(ItemDTO dto) throws SQLException;

    boolean deleteItem(ItemDTO dto) throws SQLException;
}
