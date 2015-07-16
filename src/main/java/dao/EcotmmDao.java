package dao;

import model.Ecotmm;

import java.util.List;
import java.util.Vector;


public interface EcotmmDao {
    Vector<Vector<Object>> get();

    Vector<Object> get(int id);

    void insert(Ecotmm ecotmm);

    void update(Ecotmm ecotmm);
}
