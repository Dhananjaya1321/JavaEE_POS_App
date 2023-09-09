package lk.ijse.dao;

public class FactoryDAO {
    private static FactoryDAO dao;

    private FactoryDAO() {

    }

    public static SuperDAO getInstance(DAOTypes types) {
        switch (types) {
            case CUSTOMER:
                return null;
            case ITEM:
                return null;
            default:
                return null;
        }
    }
}
