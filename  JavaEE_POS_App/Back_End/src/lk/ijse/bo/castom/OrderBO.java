package lk.ijse.bo.castom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.CustomDTO;

import java.sql.Connection;

public interface OrderBO extends SuperBO {
    boolean saveOrder(CustomDTO dto, Connection connection);
}
