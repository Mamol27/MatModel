package dao;

import model.Material;

import java.util.Vector;


public interface MaterialDao {
    Vector<Vector<Object>> get();

    Vector<Object> get(int id);

    int insert(Material material);

    void update(Material material);
}
