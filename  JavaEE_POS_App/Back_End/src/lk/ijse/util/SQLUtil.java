package lk.ijse.util;

import lk.ijse.dao.SuperDAO;

import java.util.ArrayList;

public interface SQLUtil<T> extends SuperDAO {
    boolean add(T to);
    boolean update(T to);
    boolean delete(T to);
    ArrayList<T> getAll();
}
