package lk.ijse.bo;

import lk.ijse.bo.castom.impl.CustomerBOImpl;
import lk.ijse.bo.castom.impl.ItemBOImpl;

public class FactoryBO {
    private static FactoryBO factoryBO;

    private FactoryBO() {
    }

    public static FactoryBO getFactoryBO() {
        if (factoryBO == null) {
            return factoryBO = new FactoryBO();
        } else {
            return factoryBO;
        }
    }
    public SuperBO getInstance(BOTypes types) {
        switch (types) {
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            default:
                return null;
        }
    }
}
