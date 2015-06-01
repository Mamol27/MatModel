package dao;

import model.Ecotmm;
import uni.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * Created by Илья on 26.05.2015.
 */
public class EcotmmDaoImpl implements EcotmmDao {


    @Override
    public Vector<Vector<Object>> get() {
        Connection connection = null;
        Statement st = null;
        ResultSet resultSet = null;
        Vector<Vector<Object>> ecotmm = new Vector<>();
        try {
            connection = DbConnection.createConnection();
            String sql = "SELECT consistency, material_id, temperatury_coef, temp_reduction," +
                    "flow_index_material, heat_transfer  FROM ecotmm;";
            st = connection.createStatement();
            resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                ecotmm.add(getEcotmmFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            System.out.println("get ecotmm exception");
            return ecotmm;
        } finally {
            DbConnection.close(resultSet, st, connection);
        }

        return ecotmm;
    }

    @Override
    public Vector<Object> get(int id) {
        Connection connection = null;
        Statement st = null;
        ResultSet resultSet = null;

        Vector<Object> eco = null;

        try {
            connection = DbConnection.createConnection();
            String sql = "SELECT * FROM ecotmm WHERE material_id=" + id + ";";
            st = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = st.executeQuery(sql);

            while (resultSet.next()) {
                eco = getEcotmmFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            System.out.println("get eotmm exception");
            return eco;
        } finally {
            DbConnection.close(resultSet, st, connection);
        }
        return eco;
    }

    @Override
    public void insert(Ecotmm ecotmm) {
        Connection connection = null;
        Statement st = null;
        ResultSet resultSet = null;
        int id = ecotmm.getId();

        try {
            connection = DbConnection.createConnection();

            String sql = "INSERT INTO ecotmm( " +
                    "consistency, temperatury_coef, temp_reduction, flow_index_material," +
                    "heat_transfer, material_id)" +
                    "VALUES (" + ecotmm.getConsistency() + "," + ecotmm.getTemperatury_coef() +
                    ", " + ecotmm.getTemp_reduction() + ", " + ecotmm.getFlow_index_material() +
                    ", " + ecotmm.getHeat_transfer() + ", " + ecotmm.getId() + ");";

            st = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbConnection.close(resultSet, st, connection);
        }

    }


    public static void delete(int id) {
        Connection connection = null;
        Statement st = null;
        ResultSet resultSet = null;
        System.out.println(id + "  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        try {
            connection = DbConnection.createConnection();
            String sql = "delete FROM ecotmm where material_id=" + id + ";";
            st = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            st.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println("delete row from ecotmm exception");
        } finally {
            DbConnection.close(resultSet, st, connection);
        }
    }

    @Override
    public void update(Ecotmm ecotmm) {

    }

    private Vector<Object> getEcotmmFromResultSet(ResultSet rset) throws SQLException {
        int id = rset.getInt("material_id");
        double consistency = rset.getDouble("consistency");
        double temperatury_coef = rset.getDouble("temperatury_coef");
        double temp_reduction = rset.getDouble("temp_reduction");
        double flow_index_material = rset.getDouble("flow_index_material");
        double heat_transfer = rset.getDouble("heat_transfer");
        Vector<Object> eco = new Vector<>();
        eco.add(consistency);           //Mu0
        eco.add(temperatury_coef);      //B
        eco.add(temp_reduction);        //T0
        eco.add(flow_index_material);   //N
        eco.add(heat_transfer);         //Alfau

        return eco;
    }
}
