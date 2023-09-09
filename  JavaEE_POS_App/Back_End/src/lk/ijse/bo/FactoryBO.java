package lk.ijse.bo;

import lk.ijse.bo.castom.impl.CustomerBOImpl;

public class FactoryBO {
    private static FactoryBO bo;

    private FactoryBO() {
    }

    public static SuperBO getInstance(BOTypes types) {
        switch (types) {
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return null;
            default:
                return null;
        }
    }
}
