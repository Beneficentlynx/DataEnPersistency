package nl.hu.org.dp;

import nl.hu.org.dp.Domain.Adres;
import nl.hu.org.dp.Domain.OV_chipkaart;
import nl.hu.org.dp.infra.DOA.AdresDAOPsql;
import nl.hu.org.dp.infra.DOA.OV_chipkaartDOAPSQL;
import nl.hu.org.dp.infra.DOA.ReizigerDAOPsql;
import nl.hu.org.dp.Domain.Reiziger;
import nl.hu.org.dp.infra.hibernate.AdresDAOHibernate;
import nl.hu.org.dp.infra.hibernate.OV_chipkaartDAOHibernate;
import nl.hu.org.dp.infra.hibernate.ReizigerDAOHibernate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static Connection connection = null;
    private static ReizigerDAOHibernate reizigerDAOHibernate = new ReizigerDAOHibernate();
    private static AdresDAOHibernate adresDAOHibernate = new AdresDAOHibernate();
    private static OV_chipkaartDAOHibernate ov_chipkaartDAOHibernate = new OV_chipkaartDAOHibernate();

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
        System.out.println("\n---------- Test ReizigerDAOHibernate -------------");
        System.out.println("-----------------findAll-----------------");
        System.out.println(reizigerDAOHibernate.findAll());
        System.out.println("-----------------findById-----------------");
        System.out.println(reizigerDAOHibernate.findById(1));
        System.out.println("-----------------findByGbdatum-----------------");
        System.out.println(reizigerDAOHibernate.findByGbdatum(java.sql.Date.valueOf("2002-12-03")));
        System.out.println("-----------------delete-----------------");
        int size = reizigerDAOHibernate.findAll().size();
        Reiziger domeinReiziger = new Reiziger(19, "K", "", "Joost", java.sql.Date.valueOf("2002-12-03"));
        reizigerDAOHibernate.delete(domeinReiziger);
        System.out.println(reizigerDAOHibernate.findAll().size() - size);
        System.out.println("-----------------save-----------------");
        size = reizigerDAOHibernate.findAll().size();
        reizigerDAOHibernate.save(domeinReiziger);
        System.out.println(reizigerDAOHibernate.findAll().size() - size);
        System.out.println("-----------------update-----------------");
        System.out.println(reizigerDAOHibernate.findById(19));
        domeinReiziger.setAchternaam("Klaassen");
        reizigerDAOHibernate.update(domeinReiziger);
        System.out.println(reizigerDAOHibernate.findById(19));
    }


    private static void testAdresDao(AdresDAOPsql adoap) throws SQLException {
        System.out.println("\n---------- Test AdresDAO ----------------");
        System.out.println("---------- Find adres by id--------------");
        System.out.println(adoap.findByAdresID(1));
        System.out.println("\n---------- Find adres by reiziger id-----");
        System.out.println(adoap.findByReiziger(1));
        System.out.println("\n---------- Find all adres----------------");
        System.out.println(adoap.findall());
        System.out.println("\n---------- Delete adres------------------");
        int size = adoap.findall().size();
        System.out.println(adoap.delete(new Adres(8, "1234AB", "12", "Straat", "Woonplaats", 12)));
        System.out.println(adoap.findall().size() - size);
        System.out.println("\n---------- Save adres--------------------");
        size = adoap.findall().size();
        System.out.println(adoap.save(new Adres(8, "1234AB", "12", "Straat", "Woonplaats", 12)));
        System.out.println(adoap.findall().size() - size);
        System.out.println("\n---------- Update adres------------------");
        Adres updateAdres = new Adres(8, "1234AB", "12", "Straat", "Woonplaats", 12);
        System.out.println(updateAdres);
        updateAdres.setHuisnummer("13");
        updateAdres.setStraat("Straatje");
        System.out.println(adoap.update(updateAdres));
        System.out.println(updateAdres);
    }

    private static void testConnectieReizigerAdres(AdresDAOPsql adresDAOPsql, ReizigerDAOPsql reizigerDAOPsql) throws SQLException {
        System.out.println("\n---------- Test Connectie Reiziger en Adres -------------");
        System.out.println("---------- Find adres by reiziger id-----");
        System.out.println(reizigerDAOPsql.findById(1));
        System.out.println(adresDAOPsql.findByReiziger(1));



    }

    private static void testAdresDOAHibernate() throws SQLException {
        Reiziger reiziger = new Reiziger(22, "K", "", "Joost", java.sql.Date.valueOf("2002-12-03"));
        reizigerDAOHibernate.save(reiziger);
        System.out.println("--------- TEST ADRES HIBERNATE ---------");
        System.out.println("--------- SAVE ---------");
        Adres adres = new Adres(22, "1234AB", "12", "Straat", "Woonplaats", 22);
        System.out.println(adresDAOHibernate.delete(adres));
        System.out.println(adresDAOHibernate.save(adres));
        System.out.println("--------- DELETE ---------");
        System.out.println(adresDAOHibernate.delete(adres));
        System.out.println("--------- FIND BY ID ---------");
        System.out.println(adresDAOHibernate.findByAdresID(1));
        System.out.println(adresDAOHibernate.findByAdresID(23));
        System.out.println("--------- FIND ALL ---------");
        System.out.println(adresDAOHibernate.findall());
        System.out.println("--------- Find By ReizigerID ---------");
        System.out.println(adresDAOHibernate.findByReiziger(1));
        System.out.println(adresDAOHibernate.findByReiziger(23));
        System.out.println("--------- UPDATE ---------");
        System.out.println(adresDAOHibernate.save(adres));
        adres.setHuisnummer("13");
        adres.setStraat("Straatje");
        adresDAOHibernate.update(adres);
        System.out.println(adresDAOHibernate.findByAdresID(22));
    }

    public static void testOV_chipkaartDAO(OV_chipkaartDOAPSQL ovcds) throws SQLException {
        System.out.println("\n---------- Test OV_chipkaartDAO -------------");
        System.out.println("---------- Find all OV_chipkaarten -------------");
        System.out.println(ovcds.findAll());
        System.out.println("---------- Find OV_chipkaart by id -------------");
        System.out.println(ovcds.findById(35283));
        System.out.println("---------- Save OV_chipkaart -------------");
        System.out.println(ovcds.save(new OV_chipkaart(3, java.sql.Date.valueOf("2022-09-01"), 2, 25.0f, 1)));
        System.out.println(ovcds.findById(3));
        System.out.println("---------- Update OV_chipkaart -------------");
        OV_chipkaart ov_chipkaart = new OV_chipkaart(3, java.sql.Date.valueOf("2022-09-01"), 2, 25.0f, 1);
        ov_chipkaart.setSaldo(30.0f);
        System.out.println(ovcds.update(ov_chipkaart));
        System.out.println(ovcds.findById(3));
        System.out.println("-----------------find by reiziger ID-----------------");
        System.out.println(ovcds.findByReizigerID(1));
        System.out.println("---------- Delete OV_chipkaart -------------");
        System.out.println(ovcds.delete(new OV_chipkaart(3, java.sql.Date.valueOf("2022-09-01"), 2, 25.0f, 1)));

    }

    public static void testOV_chipkaartDAOHibernate() throws SQLException {
        System.out.println("\n---------- Test OV_chipkaartDAOHibernate -------------");
        System.out.println("---------- Find all OV_chipkaarten -------------");
        System.out.println(ov_chipkaartDAOHibernate.findAll());
        System.out.println("---------- Find OV_chipkaart by id -------------");
        System.out.println(ov_chipkaartDAOHibernate.findById(35283));
        System.out.println("---------- Save OV_chipkaart -------------");
        System.out.println(ov_chipkaartDAOHibernate.save(new OV_chipkaart(3, java.sql.Date.valueOf("2022-09-01"), 2, 25.0f, 1)));
        System.out.println(ov_chipkaartDAOHibernate.findById(3));
        System.out.println("---------- Update OV_chipkaart -------------");
        OV_chipkaart ov_chipkaart = new OV_chipkaart(3, java.sql.Date.valueOf("2022-09-01"), 2, 25.0f, 1);
        ov_chipkaart.setSaldo(30.0f);
        System.out.println(ov_chipkaartDAOHibernate.update(ov_chipkaart));
        System.out.println(ov_chipkaartDAOHibernate.findById(3));
        System.out.println("-----------------find by reiziger ID-----------------");
        System.out.println(ov_chipkaartDAOHibernate.findByReiziger(new nl.hu.org.dp.infra.hibernate.Reiziger(1, "K", "", "Joost", java.sql.Date.valueOf("2002-12-03"))));
        System.out.println("---------- Delete OV_chipkaart -------------");
        System.out.println(ov_chipkaartDAOHibernate.delete(new OV_chipkaart(3, java.sql.Date.valueOf("2022-09-01"), 2, 25.0f, 1)));
    }


    public static void main(String[] args) throws SQLException {
        testOV_chipkaartDAOHibernate();
    }
}