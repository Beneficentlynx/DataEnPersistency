package nl.hu.org.dp;

import java.sql.*;

public class Main {
    static Connection connection = null;

    private static Connection getConnection() throws SQLException {
        if (connection == null) {
            String url =
                    "jdbc:postgresql://localhost/ovchip?user=postgres&password=Zwemmen2004.";
            connection = DriverManager.getConnection(url);
        }
        return connection;
    }

    private static void closeConnection() throws
            SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    private static void testConnection() throws SQLException {
        getConnection();
        String query = "SELECT * FROM reiziger;";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet set = statement.executeQuery();
        while (set != null && set.next()) {
            String tussenvoegsel = "";
            if(set.getString("tussenvoegsel") != null){
                tussenvoegsel = " " + set.getString("tussenvoegsel");
            }
            System.out.println("#" + set.getString("reiziger_id") + " " + set.getString("voorletters") + "."
            +  tussenvoegsel + " " +set.getString("achternaam") + " (" +
                    set.getString("geboortedatum") + ")");
        }
        closeConnection();
    }


    public static void main(String[] args) throws SQLException {
        System.out.println("Hello world!");
        testConnection();
    }
}