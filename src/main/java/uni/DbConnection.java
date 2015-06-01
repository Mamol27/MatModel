package uni;

import java.sql.*;

/**
 * Created by Илья on 01.05.2015.
 */
public class DbConnection {

    public static void close(ResultSet rset, Statement st, Connection conn) {
        try {
            if (rset != null) rset.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (st != null) st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection createConnection() {

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
            System.out.println("Failed to make createConnection!");
        }
        return connection;
    }

}

