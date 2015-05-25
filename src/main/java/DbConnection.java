import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

/**
 * Created by Илья on 01.05.2015.
 */
public class DbConnection {
    private double Ro = 0;
    private double C = 0;
    private double T0 = 0;
    private String description = "";
    private String type = "";
    private int pk_materialId = 0;
    private double mu0;
    private double b;
    private double Tr;
    private double n;
    private double alfau;
    private int fk_materialId = 0;

    private Object[][] databaseResults;
    public Object[] columns;
//    public Object[] columns2;
    public DefaultTableModel defaultTableModel;
    public DefaultTableModel defaultTableModel2;

    private Connection connection = null;
    private Statement stmt = null;
    public ResultSet res1;
    public ResultSet res2;


//    public void dbConnection() throws SQLException {
//
//        connection = connection();
//        stmt = connection.createStatement();
//        stmt = connection.createStatement();
//        connect_to_materials(stmt);
//        connect_to_ecotmm(stmt);
//
//        System.out.println(Ro);
//        System.out.println(mu0);
//    }
//
//    private void connect_to_materials(Statement stmt) throws SQLException {
//        ResultSet res = stmt.executeQuery("SELECT material_id," +
//                "density, ash, melting_point, description," +
//                " \"type\"   FROM materials");
//
//        try {
//            while (res.next()) {
////                pk_materialId = res.getInt("material_id");
//                Ro = res.getDouble("density");
//                C = res.getDouble("ash");
//                T0 = res.getDouble("melting_point");
//                description = res.getString("description");
//                type = res.getString("type");
//
//            }
//        } catch (SQLException e) {
//            System.out.println("Connection to materials Failed!");
//            e.printStackTrace();
//            return;
//        }
//
//    }
//
//    private void connect_to_ecotmm(Statement stmt) throws SQLException {
//        ResultSet res2 = stmt.executeQuery("SELECT consistency, " +
//                "temperatury_coef, temp_reduction, flow_index_material," +
//                "heat_transfer  FROM ecotmm where material_id=1;");
//
//        try {
//            while (res2.next()) {
//                mu0 = res2.getDouble("consistency");
//                b = res2.getDouble("temperatury_coef");
//                Tr = res2.getDouble("temp_reduction");
//                n = res2.getDouble("flow_index_material");
//                alfau = res2.getDouble("heat_transfer");
////                fk_materialId = res2.getInt("material_id");
//            }
//        } catch (SQLException e) {
//            System.out.println("Connection to ecotmm Failed!");
//            e.printStackTrace();
//            return;
//        }
//
//    }

    public DbConnection() throws SQLException {
        connection = connection();
        Vector columns2 = new Vector<String>();
        columns = new Object[]{"Номер","Название", "Плотность", "Теплоемкость", "Т плавления", "Описание",
                "Коэффициэет консистениции", "Коэффициэнт вязкости", "Т приведения", "Индекс Течения", "Коэффициэнт теплоотдачи от крышки"};
        for (int i=0; i<columns.length; i++){
            columns2.add(columns[i]);
        }

        defaultTableModel = new DefaultTableModel(databaseResults, columns) {
            public Class getColumnClass(int column) {  // Override the getColumnClass method to get the
                Class classToReturn;                   // class types of the data retrieved from the database

                if ((column >= 0) && column < getColumnCount()) {
                    classToReturn = getValueAt(0,column).getClass();
                } else {
                    classToReturn = Object.class;
                }
                return classToReturn;
            }
        };

//        defaultTableModel2 = new DefaultTableModel(databaseResults, columns2) {
//            public Class getColumnClass(int column) {  // Override the getColumnClass method to get the
//                Class classToReturn;                   // class types of the data retrieved from the database
//
//                if ((column >= 0) && column < getColumnCount()) {
//                    classToReturn = getValueAt(0,column).getClass();
//                } else {
//                    classToReturn = Object.class;
//                }
//                return classToReturn;
//            }
//        };
        Vector<Vector<Object>> intermediaryTableModel = new Vector<Vector<Object>>();
        Vector<Object> intermediaryTableModel2 = new Vector<Object>();
        Vector<Vector<Object>> intermediaryTableModel3 = new Vector<Vector<Object>>();


        try {
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String select1 = "SELECT material_id, type, density, ash, melting_point, description  FROM materials";
            String select2 = "SELECT consistency, material_id, temperatury_coef, temp_reduction," +
                    " flow_index_material, heat_transfer  FROM ecotmm";

            res1 = stmt.executeQuery(select1);
            Object[] tempRow;

            try {
                while (res1.next()) {
//                    tempRow = new Object[]{res1.getInt("material_id"), res1.getString("type"), res1.getDouble("density"),
//                            res1.getDouble("ash"), res1.getDouble("melting_point"), res1.getString("description")};
                    intermediaryTableModel2 = new Vector<Object>();
                    intermediaryTableModel2.add(res1.getInt("material_id"));
                    intermediaryTableModel2.add(res1.getString("type"));
                    intermediaryTableModel2.add(res1.getDouble("density"));
                    intermediaryTableModel2.add(res1.getDouble("ash"));
                    intermediaryTableModel2.add(res1.getDouble("melting_point"));
                    intermediaryTableModel2.add(res1.getString("description"));
                    intermediaryTableModel.add(intermediaryTableModel2);
//                    defaultTableModel.addRow(tempRow);
                }
                System.out.println("Connect from materials");
            } catch (SQLException e) {
                System.out.println("failed connect from materials");
            }

            res2 = stmt.executeQuery(select2);
            try {
                while (res2.next()) {
//                    tempRow = new Object[]{res2.getDouble("consistency"), res2.getDouble("temperatury_coef"),
//                            res2.getDouble("temp_reduction"), res2.getDouble("flow_index_material"),
//                            res2.getDouble("flow_index_material"), res2.getDouble("heat_transfer")};
                    intermediaryTableModel2 = new Vector<Object>();
                    intermediaryTableModel2.add(res2.getDouble("consistency"));
                    intermediaryTableModel2.add(res2.getDouble("temperatury_coef"));
                    intermediaryTableModel2.add(res2.getDouble("temp_reduction"));
                    intermediaryTableModel2.add(res2.getDouble("flow_index_material"));
                    intermediaryTableModel2.add(res2.getDouble("flow_index_material"));
                    intermediaryTableModel2.add(res2.getDouble("heat_transfer"));
                    intermediaryTableModel3.add(intermediaryTableModel2);
                }

                for (int i=0; i<intermediaryTableModel3.size(); i++){
                    (intermediaryTableModel.elementAt(i)).addAll(intermediaryTableModel3.elementAt(i));
                }

//                intermediaryTableModel.addAll(intermediaryTableModel3);

                defaultTableModel.setDataVector(intermediaryTableModel,columns2);

                System.out.println("Connect from ecotmm");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Все гуд");
        } catch (SQLException e) { // Print errors if exceptions occur
            System.out.println("Connection Failed!");
        }

    }

    private Connection connection() {

//        System.out.println("-------- PostgreSQL "
//                + "JDBC Connection Testing ------------");

        try {

            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your PostgreSQL JDBC Driver? "
                    + "Include in your library path!");
            e.printStackTrace();
            return null;

        }

//        System.out.println("PostgreSQL JDBC Driver Registered!");

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/testdb", "postgres",
                    "27059993");

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return null;

        }

        if (connection != null) {

        } else {
            System.out.println("Failed to make connection!");
        }
        return connection;
    }

    public void dbAdd(){

    }

    public double getRo() {
        return Ro;
    }

    public double getC() {
        return C;
    }

    public double getT0() {
        return T0;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public double getMu0() {
        return mu0;
    }

    public double getB() {
        return b;
    }

    public double getTr() {
        return Tr;
    }

    public double getN() {
        return n;
    }

    public double getAlfau() {
        return alfau;
    }
}

