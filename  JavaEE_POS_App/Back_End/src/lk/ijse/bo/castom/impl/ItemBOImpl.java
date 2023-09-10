package lk.ijse.bo.castom.impl;

import lk.ijse.bo.castom.ItemBO;
import lk.ijse.dao.DAOTypes;
import lk.ijse.dao.FactoryDAO;
import lk.ijse.dao.SuperDAO;
import lk.ijse.dao.castom.impl.ItemDAOImpl;
import lk.ijse.dto.ItemDTO;
import lk.ijse.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    private final ItemDAOImpl itemDAO = (ItemDAOImpl) FactoryDAO.getFactoryDAO().getInstance(DAOTypes.ITEM);

    @Override
    public boolean addItem(ItemDTO dto) throws SQLException {
        return itemDAO.add(new Item(dto.getCode(), dto.getName(), dto.getPrice(), dto.getQty()));
    }
    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException {
        return itemDAO.update(new Item(dto.getCode(), dto.getName(), dto.getPrice(), dto.getQty()));
    }

    @Override
    public boolean deleteItem(ItemDTO dto) throws SQLException {
        return itemDAO.delete(new Item(dto.getCode()));
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item i:all) {
            itemDTOS.add(new ItemDTO(i.getCode(),i.getName(),i.getPrice(),i.getQty()));
        }
        return itemDTOS;
    }
}
