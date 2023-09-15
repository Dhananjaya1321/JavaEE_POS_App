package lk.ijse.bo.castom.impl;

import lk.ijse.bo.castom.OrderBO;
import lk.ijse.dao.DAOTypes;
import lk.ijse.dao.FactoryDAO;
import lk.ijse.dao.SuperDAO;
import lk.ijse.dao.castom.impl.ItemDAOImpl;
import lk.ijse.dao.castom.impl.OrderDAOImpl;
import lk.ijse.dao.castom.impl.QueryDAOImpl;
import lk.ijse.dto.CustomDTO;
import lk.ijse.entity.CustomEntity;
import lk.ijse.entity.Item;
import lk.ijse.util.CrudUtil;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {
    private final OrderDAOImpl orderDAO = (OrderDAOImpl) FactoryDAO.getFactoryDAO().getInstance(DAOTypes.ORDER);
    private final QueryDAOImpl queryDAO = (QueryDAOImpl) FactoryDAO.getFactoryDAO().getInstance(DAOTypes.QUERY);
    private final ItemDAOImpl itemDAO = (ItemDAOImpl) FactoryDAO.getFactoryDAO().getInstance(DAOTypes.ITEM);

    @Override
    public boolean saveOrder(CustomDTO dto, Connection connection) throws SQLException {
        try {
            connection.setAutoCommit(false);
            boolean orderIsAdded = orderDAO.add(new CustomEntity(dto.getOrderId(), dto.getDate(), dto.getNic(), dto.getTotal(), dto.getSubTotal(), dto.getCash(), dto.getDiscount(), dto.getBalance()), connection);

            if (orderIsAdded) {
                int count = 0;
                ArrayList<CustomDTO> customDTOS = dto.getCustomDTOS();
                for (CustomDTO c : customDTOS) {
                    boolean orderDetailsIsAdded = queryDAO.save(new CustomEntity(c.getOrderId(), c.getCode(), c.getPrice(), c.getQty()), connection);
                    if (orderDetailsIsAdded) {
                        count++;
                    }
                }
                if (count != customDTOS.size()) {
                    connection.rollback();
                    return false;
                }

                count = 0;
                for (CustomDTO c : customDTOS) {
                    Item item = itemDAO.getItem(new Item(c.getCode()), connection);
                    boolean isUpdate = itemDAO.update(new Item(item.getCode(), item.getName(), item.getQty() - c.getQty(), item.getQty()), connection);
                    if (isUpdate) {
                        count++;
                    }
                }
                if (count != customDTOS.size()) {
                    connection.rollback();
                    return false;
                }
                return true;
            }else {
                connection.rollback();
                return false;
            }
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
    }
}
