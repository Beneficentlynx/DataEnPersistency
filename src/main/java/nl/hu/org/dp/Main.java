package nl.hu.org.dp;

import nl.hu.org.dp.infra.DOA.ReizigerDAOPsql;
import nl.hu.org.dp.Domain.Reiziger;
import nl.hu.org.dp.infra.hibernate.ReizigerDAOHibernate;

import java.sql.*;
import java.util.List;

public class Main {
    static Connection connection = null;
    private static ReizigerDAOHibernate reizigerDAOHibernate = new ReizigerDAOHibernate();

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

    private static void testReizigerDAO(ReizigerDAOPsql rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete() ");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        Reiziger veranderNaam = new Reiziger(12, "J", "", "Groot", java.sql.Date.valueOf(gbdatum));
        rdao.delete(veranderNaam);
        rdao.save(veranderNaam);
        System.out.println(rdao.findAll());
        veranderNaam.setAchternaam("Klein");
        rdao.update(veranderNaam);
        System.out.println(rdao.findAll());
        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
    }

    private static void testReizigerDAOHIbernate() throws SQLException {
        System.out.println(reizigerDAOHibernate.findById(1));
        System.out.println(reizigerDAOHibernate.findByGbdatum(java.sql.Date.valueOf("2002-12-03")));
    }


    public static void main(String[] args) throws SQLException {
        System.out.println("Hello world!");
//        ReizigerDAOPsql rdao = new ReizigerDAOPsql();
//        testReizigerDAO(rdao);
        testReizigerDAOHIbernate();

    }
}