package lk.ijse.bo.castom.impl;

import lk.ijse.bo.castom.OrderBO;
import lk.ijse.dto.CustomDTO;

import java.sql.Connection;

public class OrderBOImpl implements OrderBO {
    @Override
    public boolean saveOrder(CustomDTO dto, Connection connection) {
        return false;
    }
}
