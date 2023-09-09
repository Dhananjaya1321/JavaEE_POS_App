package lk.ijse.dao;

import lk.ijse.dao.castom.impl.CustomerDAOImpl;

public class FactoryDAO {
    private static FactoryDAO dao;

    private FactoryDAO() {

    }

    public static SuperDAO getInstance(DAOTypes types) {
        switch (types) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM:
                return null;
            default:
                return null;
        }
    }
}
