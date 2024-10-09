package nl.hu.org.dp.infra.DOA;

import nl.hu.org.dp.Domain.Adres;
import nl.hu.org.dp.Domain.DAO.AdresDOA;
import nl.hu.org.dp.Domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDOA {
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

    public boolean save(Adres adres) throws SQLException{
        if (findByAdresID(adres.getAdres_id() ) != null){
            return false;
        }
        if(findByReiziger(adres.getReiziger_id()) != null){
            return false;
        }
        getConnection();
        String query = "SELECT * FROM reiziger WHERE reiziger_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, adres.getReiziger_id());
        ResultSet set = statement.executeQuery();
        closeConnection();
        if(!set.next()){
            return false;
        }
        getConnection();
        String query2 = "INSERT INTO adres(adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES(?, ?, ?, ?, ?, ?)";
        statement = connection.prepareStatement(query2);
        statement.setInt(1, adres.getAdres_id());
        statement.setString(2, adres.getPostcode());
        statement.setString(3, adres.getHuisnummer());
        statement.setString(4, adres.getStraat());
        statement.setString(5, adres.getWoonplaats());
        statement.setInt(6, adres.getReiziger_id());
        statement.executeUpdate();
        closeConnection();
        return true;
    }

    public boolean update(Adres adres)throws SQLException{
        if(findByAdresID(adres.getAdres_id()) == null){
            return false;
        }
        else{
            getConnection();
            String query = "UPDATE adres SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id = ? WHERE adres_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, adres.getPostcode());
            statement.setString(2, adres.getHuisnummer());
            statement.setString(3, adres.getStraat());
            statement.setString(4, adres.getWoonplaats());
            statement.setInt(5, adres.getReiziger_id());
            statement.setInt(6, adres.getAdres_id());
            statement.executeUpdate();
            closeConnection();
            return true;
        }
    }

    public boolean delete(Adres adres)throws SQLException{
        getConnection();
        String query = "DELETE FROM adres WHERE adres_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, adres.getAdres_id());
        statement.executeUpdate();
        closeConnection();
        return true;
    }

    public List<Adres> findall()throws SQLException{
        getConnection();
        String query = "SELECT * FROM adres";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet set = statement.executeQuery();
        closeConnection();
        ArrayList<Adres> returnlist = new ArrayList<>();
        while (set != null && set.next()) {


            Reiziger reiziger = new ReizigerDAOPsql().findById(set.getInt("reiziger_id"));
            returnlist.add(new Adres(set.getInt("adres_id"), set.getString("postcode"), set.getString("huisnummer"), set.getString("straat"), set.getString("woonplaats"), reiziger));
        }
        return returnlist;
    }

    public Adres findByReiziger(int reiziger_id) throws SQLException{
        getConnection();
        String query = "SELECT * FROM adres WHERE reiziger_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, reiziger_id);
        ResultSet set = statement.executeQuery();
        if (!set.next()){
            return null;
        }
        closeConnection();
        Reiziger reiziger = new ReizigerDAOPsql().findById(set.getInt("reiziger_id"));
        return new Adres(set.getInt("adres_id"), set.getString("postcode"), set.getString("huisnummer"), set.getString("straat"), set.getString("woonplaats"), reiziger);
    }

    public Adres findByAdresID(int adres_id) throws SQLException {
        getConnection();
        String query = "SELECT * FROM adres WHERE adres_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, adres_id);
        ResultSet set = statement.executeQuery();
        if (!set.next()){
            return null;
        }
        closeConnection();
        Reiziger reiziger = new ReizigerDAOPsql().findById(set.getInt("reiziger_id"));
        return new Adres(set.getInt("adres_id"), set.getString("postcode"), set.getString("huisnummer"), set.getString("straat"), set.getString("woonplaats"), reiziger);
    }
}
