package lk.ijse.bo.castom.impl;

import lk.ijse.bo.castom.ItemBO;
import lk.ijse.dao.DAOTypes;
import lk.ijse.dao.FactoryDAO;
import lk.ijse.dao.SuperDAO;
import lk.ijse.dao.castom.impl.ItemDAOImpl;
import lk.ijse.dto.ItemDTO;
import lk.ijse.entity.Item;
import lk.ijse.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    private final ItemDAOImpl itemDAO = (ItemDAOImpl) FactoryDAO.getFactoryDAO().getInstance(DAOTypes.ITEM);

    @Override
    public boolean addItem(ItemDTO dto, Connection connection) throws SQLException {
        return itemDAO.add(new Item(dto.getCode(), dto.getName(), dto.getPrice(), dto.getQty()),connection);
    }
    @Override
    public boolean updateItem(ItemDTO dto,Connection connection) throws SQLException {
        return itemDAO.update(new Item(dto.getCode(), dto.getName(), dto.getPrice(), dto.getQty()),connection);
    }

    @Override
    public boolean deleteItem(ItemDTO dto,Connection connection) throws SQLException {
        return itemDAO.delete(new Item(dto.getCode()),connection);
    }

    @Override
    public ArrayList<ItemDTO> getAllItems(Connection connection) throws SQLException {
        ArrayList<Item> all = itemDAO.getAll(connection);
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item i:all) {
            itemDTOS.add(new ItemDTO(i.getCode(),i.getName(),i.getPrice(),i.getQty()));
        }
        return itemDTOS;
    }
    @Override
    public ItemDTO getItem(ItemDTO dto, Connection connection) throws SQLException {
        Item item = itemDAO.getItem(new Item(dto.getCode()), connection);
        return new ItemDTO(item.getCode(),item.getName(),item.getPrice(),item.getQty());
    }
}
