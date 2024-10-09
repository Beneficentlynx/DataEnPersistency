package nl.hu.org.dp.infra.DOA;

import nl.hu.org.dp.Domain.DAO.OV_chipkaartDOA;
import nl.hu.org.dp.Domain.OV_chipkaart;
import nl.hu.org.dp.Domain.Product;
import nl.hu.org.dp.Domain.Reiziger;
import nl.hu.org.dp.infra.DOA.ReizigerDAOPsql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OV_chipkaartDOAPSQL implements OV_chipkaartDOA {
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

    @Override
    public boolean save(OV_chipkaart ov_chipkaart) throws SQLException {
        ProductDAOPsql productDAOPsql = new ProductDAOPsql();
        if (productDAOPsql != null) {
            for (Product p : ov_chipkaart.getProducten()) {
                productDAOPsql.save(p);
            }
        }
        getConnection();
        String query = "INSERT INTO OV_chipkaart(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES(?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ov_chipkaart.getKaart_nummer());
            statement.setDate(2, ov_chipkaart.getGeldig_tot());
            statement.setInt(3, ov_chipkaart.getKlasse());
            statement.setFloat(4, ov_chipkaart.getSaldo());
            statement.setInt(5, ov_chipkaart.getReiziger_id());
            statement.executeUpdate();
            closeConnection();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(OV_chipkaart ov_chipkaart) throws SQLException {
        if(findById(ov_chipkaart.getKaart_nummer()) == null){
            return false;
        }
        else{
            getConnection();
            String query = "UPDATE OV_chipkaart SET geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaart_nummer = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, ov_chipkaart.getGeldig_tot());
            statement.setInt(2, ov_chipkaart.getKlasse());
            statement.setFloat(3, ov_chipkaart.getSaldo());
            statement.setInt(4, ov_chipkaart.getReiziger_id());
            statement.setInt(5, ov_chipkaart.getKaart_nummer());
            statement.executeUpdate();
            closeConnection();
            return true;
        }
    }

    @Override
    public boolean delete(OV_chipkaart ov_chipkaart) throws SQLException {
        if(findById(ov_chipkaart.getKaart_nummer()) != null){
            ProductDAOPsql productDAOPsql = new ProductDAOPsql();
            productDAOPsql.deleteRelatie(ov_chipkaart);
        }
        getConnection();
        String query = "DELETE FROM OV_chipkaart WHERE kaart_nummer = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, ov_chipkaart.getKaart_nummer());
        statement.executeUpdate();
        closeConnection();
        return true;
    }

    @Override
    public OV_chipkaart findById(int id) throws SQLException {
        getConnection();
        String query = "SELECT * FROM OV_chipkaart WHERE kaart_nummer = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet set = statement.executeQuery();
        closeConnection();
        if(!set.next()){
            return null;
        }
        Reiziger reiziger = new ReizigerDAOPsql().findById(set.getInt("reiziger_id"));
        return new OV_chipkaart(set.getInt("kaart_nummer"), set.getDate("geldig_tot"), set.getInt("klasse"), set.getFloat("saldo"), reiziger);
    }

    @Override
    public List<OV_chipkaart> findAll() throws SQLException {
        getConnection();
        String query = "SELECT * FROM OV_chipkaart;";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet set = statement.executeQuery();
        closeConnection();
        ArrayList<OV_chipkaart> returnlist = new ArrayList<>();
        while (set != null && set.next()) {
            Reiziger reiziger = new ReizigerDAOPsql().findById(set.getInt("reiziger_id"));
            returnlist.add(new OV_chipkaart(set.getInt("kaart_nummer"), set.getDate("geldig_tot"), set.getInt("klasse"), set.getFloat("saldo"), reiziger));
        }
        return returnlist;
    }

    public List<OV_chipkaart> findByReizigerID(int id) throws SQLException {
        getConnection();
        String query = "SELECT * FROM reiziger WHERE reiziger_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet set = statement.executeQuery();
        if(!set.next()){
            return null;
        }
        query = "SELECT * FROM OV_chipkaart WHERE reiziger_id = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        set = statement.executeQuery();
        closeConnection();
        ArrayList<OV_chipkaart> returnlist = new ArrayList<>();
        while (set != null && set.next()) {
            Reiziger reiziger = new ReizigerDAOPsql().findById(set.getInt("reiziger_id"));
            returnlist.add(new OV_chipkaart(set.getInt("kaart_nummer"), set.getDate("geldig_tot"), set.getInt("klasse"), set.getFloat("saldo"), reiziger));
        }
        return returnlist;
    }
}
