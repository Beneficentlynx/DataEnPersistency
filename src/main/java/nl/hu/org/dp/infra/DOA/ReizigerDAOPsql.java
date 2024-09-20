package nl.hu.org.dp.infra.DOA;

import nl.hu.org.dp.Domain.DAO.ReizigerDAO;
import nl.hu.org.dp.Domain.Reiziger;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class ReizigerDAOPsql implements ReizigerDAO {
    static Connection connection = null;

    private static Connection getConnection() throws SQLException {
        if (connection == null) {
            String url =
                    "jdbc:postgresql://localhost/ovchip?user=postgres&password=Zwemmen2004.";
            connection = DriverManager.getConnection(url);
        }
        return connection;
    }

    private static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    public List findAll() throws SQLException {
        getConnection();
        String query = "SELECT * FROM reiziger;";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet set = statement.executeQuery();
        closeConnection();
        ArrayList<Reiziger> returnlist = new ArrayList<>();
        while (set != null && set.next()) {
            String tussenvoegsel = "";
            if(set.getString("tussenvoegsel") != null){
                tussenvoegsel = " " + set.getString("tussenvoegsel");
            }
            returnlist.add(new Reiziger(set.getInt("reiziger_id"), set.getString("voorletters")
                    , tussenvoegsel, set.getString("achternaam"),
                    java.sql.Date.valueOf(set.getString("geboortedatum"))));
        }
        return returnlist;

    }

    public boolean save(Reiziger reiziger) throws SQLException {
        List bestaandeReizigers = findAll();
        for (Object x : bestaandeReizigers){
            x = (Reiziger) x;
            if (((Reiziger) x).getReiziger_id() == reiziger.getReiziger_id()){
                return false;
            }
        }
        getConnection();
        int reiziger_id = reiziger.getReiziger_id();
        String voorletters = reiziger.getVoorletters();
        String tussenvoegsel = reiziger.getTussenvoegsel();
        String achternaam = reiziger.getAchternaam();
        Date geboortedatum = (Date) reiziger.getGeboortedatum();
        String query = "INSERT INTO reiziger VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, reiziger_id);
        statement.setString(2, voorletters);
        statement.setString(3, tussenvoegsel);
        statement.setString(4, achternaam);
        statement.setDate(5, geboortedatum);
        statement.executeUpdate();
        closeConnection();
        return true;
    }

    public boolean delete(Reiziger reiziger) throws SQLException {
        List bestaandeReizigers = findAll();
        for (Object x : bestaandeReizigers){
            x = (Reiziger) x;
            if (((Reiziger) x).getReiziger_id() == reiziger.getReiziger_id()){
                getConnection();
                String query = "DELETE FROM adres WHERE reiziger_id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, reiziger.getReiziger_id());
                statement.executeUpdate();
                closeConnection();
                getConnection();
                query = "DELETE FROM reiziger WHERE reiziger_id = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, reiziger.getReiziger_id());
                statement.executeUpdate();
                closeConnection();
                return true;

            }
        }
        return false;
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        getConnection();
        String query = "SELECT * FROM reiziger WHERE reiziger_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet set = statement.executeQuery();
        set.next();
        closeConnection();
        return new Reiziger(set.getInt("reiziger_id"), set.getString("voorletters")
                , set.getString("tussenvoegsel"), set.getString("achternaam"),
                java.sql.Date.valueOf(set.getString("geboortedatum")));
    }

    @Override
    public List<Reiziger> findByGbdatum(Date datum) throws SQLException {
        return List.of();
    }

    public boolean update(Reiziger reiziger) throws SQLException {
        List bestaandeReizigers = findAll();
        for (Object x : bestaandeReizigers){
            x = (Reiziger) x;
            if (((Reiziger) x).getReiziger_id() == reiziger.getReiziger_id()){
                getConnection();
                String query = "DELETE FROM reiziger WHERE reiziger_id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, reiziger.getReiziger_id());
                statement.executeUpdate();
                closeConnection();
                getConnection();
                int reiziger_id = reiziger.getReiziger_id();
                String voorletters = reiziger.getVoorletters();
                String tussenvoegsel = reiziger.getTussenvoegsel();
                String achternaam = reiziger.getAchternaam();
                Date geboortedatum = (Date) reiziger.getGeboortedatum();
                String query2 = "INSERT INTO reiziger VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement2 = connection.prepareStatement(query2);
                statement2.setInt(1, reiziger_id);
                statement2.setString(2, voorletters);
                statement2.setString(3, tussenvoegsel);
                statement2.setString(4, achternaam);
                statement2.setDate(5, geboortedatum);
                statement2.executeUpdate();
                closeConnection();
                return true;
            }
        }
        return false;
    }
}
