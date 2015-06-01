package dao;

import model.Ecotmm;

import java.util.List;
import java.util.Vector;

/**
 * Created by Илья on 26.05.2015.
 */
public interface EcotmmDao {
    Vector<Vector<Object>> get();

    Vector<Object> get(int id);

    void insert(Ecotmm ecotmm);

    void update(Ecotmm ecotmm);
}
