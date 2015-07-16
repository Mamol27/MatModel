package dao;

import model.Material;
import uni.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;


public class MaterialDaoImpl implements MaterialDao {
    @Override
    public Vector<Vector<Object>> get() {
        Connection connection = null;
        Statement st = null;
        ResultSet resultSet = null;

        Vector<Vector<Object>> materials = new Vector<>();

        try {
            connection = DbConnection.createConnection();
            String sql = "SELECT material_id, type, density, ash, melting_point, description  FROM materials;";
            st = connection.createStatement();
            resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                materials.add(getMaterialFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            System.out.println("get materials exception");
            return materials;
        } finally {
            DbConnection.close(resultSet, st, connection);
        }
        return materials;
    }

    @Override
    public Vector<Object> get(int id) {
        Connection connection = null;
        Statement st = null;
        ResultSet resultSet = null;

        Vector<Object> materials = null;

        try {
            connection = DbConnection.createConnection();
            String sql = "SELECT * FROM materials WHERE material_id=" + id + ";";
            st = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = st.executeQuery(sql);

            while (resultSet.next()) {
                materials = getMaterialFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            System.out.println("get materials exception");
            return materials;
        } finally {
            DbConnection.close(resultSet, st, connection);
        }
        return materials;
    }

    @Override
    public int insert(Material material) {
        int id = 0;
        Connection connection = null;
        Statement st = null;
        ResultSet resultSet = null;


        try {
            connection = DbConnection.createConnection();
            String sql = "SELECT *  FROM materials";
            st = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = st.executeQuery(sql);

            resultSet.moveToInsertRow();
            resultSet.updateInt("material_id", material.getId());
            resultSet.updateDouble("ash", material.getAsh());
            resultSet.updateDouble("melting_point", material.getMelting_point());
            resultSet.updateString("description", material.getDescription());
            resultSet.updateString("type", material.getType());
            resultSet.updateDouble("density", material.getDensity());


            resultSet.insertRow();
            id = resultSet.getInt("material_id");
            System.out.println("Новая строка в материалы добавлена");
        } catch (SQLException e) {
            System.out.println("Ниче не вышло");
        } finally {
            DbConnection.close(resultSet, st, connection);
        }

        return material.getId();
    }

    public Vector<String> getColumn() throws SQLException {
        Connection connection = DbConnection.createConnection();
        String sql = "SELECT type FROM materials;";
        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery(sql);
        Vector<String> mat = new Vector<>();
        while (resultSet.next()) {
            mat.add(resultSet.getString("type"));
        }

        return mat;
    }

    @Override
    public void update(Material material) {

    }

    public static void delete(int id) {
        Connection connection = null;
        Statement st = null;
        ResultSet resultSet = null;

        try {
            connection = DbConnection.createConnection();
            String sql = "DELETE FROM materials WHERE material_id=" + id + ";";
            st = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            st.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println("delete row from materials exception");
        } finally {
            DbConnection.close(resultSet, st, connection);
        }
    }

    private Vector<Object> getMaterialFromResultSet(ResultSet rset) throws SQLException {

        int id = rset.getInt("material_id");
        String type = rset.getString("type");
        double density = rset.getDouble("density");
        double ash = rset.getDouble("ash");
        double melting_point = rset.getDouble("melting_point");
        String description = rset.getObject("description") != null ? rset.getString("description") : "";
        Vector<Object> mat = new Vector<>();
        //(id,type,density, ash, melting_point, description);
        mat.add(id);
        mat.add(type);
        mat.add(density);
        mat.add(ash);
        mat.add(melting_point);
        mat.add(description);

        return mat;
    }

}
