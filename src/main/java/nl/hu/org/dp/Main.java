package nl.hu.org.dp;

import nl.hu.org.dp.Domain.Adres;
import nl.hu.org.dp.Domain.OV_chipkaart;
import nl.hu.org.dp.Domain.Product;
import nl.hu.org.dp.infra.DOA.AdresDAOPsql;
import nl.hu.org.dp.infra.DOA.OV_chipkaartDOAPSQL;
import nl.hu.org.dp.infra.DOA.ProductDAOPsql;
import nl.hu.org.dp.infra.DOA.ReizigerDAOPsql;
import nl.hu.org.dp.Domain.Reiziger;
import nl.hu.org.dp.infra.HibernateUtil;
import nl.hu.org.dp.infra.hibernate.AdresDAOHibernate;
import nl.hu.org.dp.infra.hibernate.OV_chipkaartDAOHibernate;
import nl.hu.org.dp.infra.hibernate.ProductDAOHibernate;
import nl.hu.org.dp.infra.hibernate.ReizigerDAOHibernate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static Connection connection = null;
    private static ReizigerDAOHibernate reizigerDAOHibernate = new ReizigerDAOHibernate();
    private static AdresDAOHibernate adresDAOHibernate = new AdresDAOHibernate();
    private static OV_chipkaartDAOHibernate ov_chipkaartDAOHibernate = new OV_chipkaartDAOHibernate();
    private static ProductDAOHibernate productDAOHibernate = new ProductDAOHibernate();

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
        Reiziger reiziger = new Reiziger(12, "K", "", "Joost", java.sql.Date.valueOf("2002-12-03"));
        System.out.println(adoap.delete(new Adres(8, "1234AB", "12", "Straat", "Woonplaats", reiziger)));
        System.out.println(adoap.findall().size() - size);
        System.out.println("\n---------- Save adres--------------------");
        size = adoap.findall().size();
        System.out.println(adoap.save(new Adres(8, "1234AB", "12", "Straat", "Woonplaats", reiziger)));
        System.out.println(adoap.findall().size() - size);
        System.out.println("\n---------- Update adres------------------");
        Adres updateAdres = new Adres(8, "1234AB", "12", "Straat", "Woonplaats", reiziger);
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
        reizigerDAOHibernate.delete(reiziger);
        System.out.println("--------- TEST ADRES HIBERNATE ---------");
        System.out.println("--------- SAVE ---------");
        Adres adres = new Adres(22, "1234AB", "12", "Straat", "Woonplaats", reiziger);
        System.out.println(adresDAOHibernate.save(adres));
        System.out.println("--------- DELETE ---------");
        System.out.println(adresDAOHibernate.delete(adres));
        System.out.println("--------- FIND BY ID ---------");
        System.out.println(adresDAOHibernate.findByAdresID(1));
        System.out.println("--------- FIND ALL ---------");
        System.out.println(adresDAOHibernate.findall());
        System.out.println("--------- Find By ReizigerID ---------");
        System.out.println(adresDAOHibernate.findByReiziger(1));
        System.out.println("--------- UPDATE ---------");
        reizigerDAOHibernate.delete(reiziger);

        adresDAOHibernate.save(adres);
        System.out.println(adresDAOHibernate.findByAdresID(22));
        adres.setHuisnummer("13");
        adres.setStraat("Straatje");
        adresDAOHibernate.update(adres);
        System.out.println(adresDAOHibernate.findByAdresID(22));
    }

    public static void testOV_chipkaartDAO(OV_chipkaartDOAPSQL ovcds) throws SQLException {
        Reiziger reiziger = new Reiziger(1, "K", "", "Joost", java.sql.Date.valueOf("2002-12-03"));
        System.out.println("\n---------- Test OV_chipkaartDAO -------------");
        System.out.println("---------- Find all OV_chipkaarten -------------");
        System.out.println(ovcds.findAll());
        System.out.println("---------- Find OV_chipkaart by id -------------");
        System.out.println(ovcds.findById(35283));
        System.out.println("---------- Save OV_chipkaart -------------");
        System.out.println(ovcds.save(new OV_chipkaart(3, java.sql.Date.valueOf("2022-09-01"), 2, 25.0f, reiziger)));
        System.out.println(ovcds.findById(3));
        System.out.println("---------- Update OV_chipkaart -------------");
        OV_chipkaart ov_chipkaart = new OV_chipkaart(3, java.sql.Date.valueOf("2022-09-01"), 2, 25.0f, reiziger);
        ov_chipkaart.setSaldo(30.0f);
        System.out.println(ovcds.update(ov_chipkaart));
        System.out.println(ovcds.findById(3));
        System.out.println("-----------------find by reiziger ID-----------------");
        System.out.println(ovcds.findByReizigerID(1));
        System.out.println("---------- Delete OV_chipkaart -------------");
        System.out.println(ovcds.delete(new OV_chipkaart(3, java.sql.Date.valueOf("2022-09-01"), 2, 25.0f, reiziger)));

    }

    public static void testOV_chipkaartDAOHibernate() throws SQLException {
        Reiziger reiziger = new Reiziger(99, "K", "", "Joost", java.sql.Date.valueOf("2002-12-03"));
        reizigerDAOHibernate.delete(reiziger);
        System.out.println("\n---------- Test OV_chipkaartDAOHibernate -------------");
        System.out.println("---------- Find all OV_chipkaarten -------------");
        System.out.println(ov_chipkaartDAOHibernate.findAll());
        System.out.println("---------- Find OV_chipkaart by id -------------");
        System.out.println(ov_chipkaartDAOHibernate.findById(35283));
        System.out.println("---------- Save OV_chipkaart -------------");
        System.out.println(ov_chipkaartDAOHibernate.save(new OV_chipkaart(3, java.sql.Date.valueOf("2022-09-01"), 2, 25.0f, reiziger)));
        System.out.println(ov_chipkaartDAOHibernate.findById(3));
        System.out.println("---------- Update OV_chipkaart -------------");
        OV_chipkaart ov_chipkaart = new OV_chipkaart(3, java.sql.Date.valueOf("2022-09-01"), 2, 25.0f, reiziger);
        ov_chipkaart.setSaldo(30.0f);
        System.out.println(ov_chipkaartDAOHibernate.update(ov_chipkaart));
        System.out.println(ov_chipkaartDAOHibernate.findById(3));
        System.out.println("-----------------find by reiziger ID-----------------");
        System.out.println(ov_chipkaartDAOHibernate.findByReiziger(new nl.hu.org.dp.infra.hibernate.Reiziger(99, "K", "", "Joost", java.sql.Date.valueOf("2002-12-03"))));
        System.out.println("---------- Delete OV_chipkaart -------------");
        System.out.println(ov_chipkaartDAOHibernate.delete(new OV_chipkaart(3, java.sql.Date.valueOf("2022-09-01"), 2, 25.0f, reiziger)));
    }

    public static void testProductDAO(ProductDAOPsql prdsq, OV_chipkaartDOAPSQL ovcds) throws Exception {
        getConnection();

        OV_chipkaart ovChipkaart = ovcds.findById(35283);
        OV_chipkaart ovChipkaart2 = ovcds.findById(79625);

        System.out.println("nieuw product");
        Product product = new Product(8, "Weekkaart 2e klas", "Een week lang reizen in de 2e klas", 150.0f);
        product.addOVKaart(ovChipkaart);
        product.addOVKaart(ovChipkaart2);
        prdsq.save(product);
        System.out.println(prdsq.findById(8));

        product.setPrijs(200.0f);
        prdsq.update(product);
        System.out.println( "\nProduct met nieuwe prijs\n" + prdsq.findById(8));

        System.out.println( "\nfindByOVChipkaart\n" + prdsq.findByOVChipkaart(ovChipkaart));

        prdsq.delete(prdsq.findById(8));
        System.out.println("\nProduct verwijderd\n" + prdsq.findById(8));

        closeConnection();
    }

    public static void testproductDaoHibernate() throws SQLException {
        HibernateUtil.openSession();
        System.out.println("\n---------- Test ProductDAOHibernate -------------");
        System.out.println("---------- Find all products -------------");
        System.out.println(productDAOHibernate.findAll());
        System.out.println("---------- Find product by id -------------");
        System.out.println(productDAOHibernate.findById(1));
        System.out.println("---------- Save product -------------");
        HibernateUtil.getSession().beginTransaction();
        Product product = new Product(99, "test", "test", 1.0f);
        product.addOVKaart(ov_chipkaartDAOHibernate.findById(35283));
        System.out.println(product.getOvChipkaarten());
        System.out.println(productDAOHibernate.save(product));
        HibernateUtil.getSession().getTransaction().commit();
        System.out.println(productDAOHibernate.findById(99));
        System.out.println("---------- Update product -------------");
        HibernateUtil.getSession().beginTransaction();
        System.out.println(productDAOHibernate.update(new Product(99, "test", "test2", 1.0f)));
        HibernateUtil.getSession().getTransaction().commit();
        System.out.println(productDAOHibernate.findById(99));
        System.out.println("---------- Delete product -------------");
        HibernateUtil.getSession().beginTransaction();
        System.out.println(productDAOHibernate.delete(new Product(99, "test", "test2", 1.0f)));
        HibernateUtil.getSession().getTransaction().commit();
        System.out.println("---------- Find by OV_chipkaart -------------");
        HibernateUtil.getSession().beginTransaction();
        System.out.println(productDAOHibernate.findByOVChipkaart(new nl.hu.org.dp.infra.hibernate.OV_chipkaart(35283, java.sql.Date.valueOf("2022-09-01"), 2, 25.0f, new nl.hu.org.dp.infra.hibernate.Reiziger(1, "K", "", "Joost", java.sql.Date.valueOf("2002-12-03")))));
        HibernateUtil.getSession().getTransaction().commit();

    }


    public static void main(String[] args) throws Exception {
        testProductDAO(new ProductDAOPsql(), new OV_chipkaartDOAPSQL());
    }
}