package dao;

import model.Material;

import java.util.Vector;

/**
 * Created by Илья on 25.05.2015.
 */
public interface MaterialDao {
    Vector<Vector<Object>> get();

    Vector<Object> get(int id);

    int insert(Material material);

    void update(Material material);
}
