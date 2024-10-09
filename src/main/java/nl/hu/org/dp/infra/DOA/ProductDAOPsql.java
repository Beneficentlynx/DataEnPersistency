package nl.hu.org.dp.infra.DOA;

import nl.hu.org.dp.Domain.DAO.ProductDAO;
import nl.hu.org.dp.Domain.OV_chipkaart;
import nl.hu.org.dp.Domain.OV_chipkaart_product;
import nl.hu.org.dp.Domain.Product;
import nl.hu.org.dp.Domain.Reiziger;
import nl.hu.org.dp.infra.HibernateUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {
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
    public boolean save(Product product) throws SQLException {
        try {
            getConnection();
            // opslaan van product
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO product (product_nummer, naam, beschrijving, prijs) VALUES (?, ?, ?, ?)"
            );
            stmt.setInt(1, product.getProduct_nummer());
            stmt.setString(2, product.getNaam());
            stmt.setString(3, product.getBeschrijving());
            stmt.setDouble(4, product.getPrijs());
            stmt.execute();

            // opslaan van relatie met ovchipkaart
            for (OV_chipkaart kaart : product.getOvChipkaarten()) {
                PreparedStatement stmt2 = connection.prepareStatement(
                        "INSERT INTO ov_chipkaart_product VALUES (?, ?, ?, ?)"
                );
                stmt2.setInt(1, kaart.getKaart_nummer());
                stmt2.setInt(2, product.getProduct_nummer());
                // status is standaard gekocht bij het opslaan van een nieuwe relatie
                stmt2.setString(3, "gekocht");
                stmt2.setDate(4, Date.valueOf(LocalDate.now()));
                stmt2.execute();
            }
            closeConnection();
            return true;
        } catch (SQLException e) {
//            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Product product) throws SQLException {
        if(findById(product.getProduct_nummer()) == null){
            return false;
        }
        else{
            getConnection();
            String query = "UPDATE product SET naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getNaam());
            statement.setString(2, product.getBeschrijving());
            statement.setFloat(3, product.getPrijs());
            statement.setInt(4, product.getProduct_nummer());
            statement.executeUpdate();
            query = "UPDATE ov_chipkaart_product SET last_update = ? WHERE product_nummer = ?";
            statement = connection.prepareStatement(query);
            statement.setDate(1, new Date(System.currentTimeMillis()));
            statement.setInt(2, product.getProduct_nummer());
            statement.executeUpdate();
            closeConnection();
            return true;
        }
    }

    public boolean deleteRelatie(OV_chipkaart ovkaart) throws SQLException {
        try {
            getConnection();
            String query = "DELETE FROM ov_chipkaart_product WHERE kaart_nummer = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ovkaart.getKaart_nummer());
            statement.executeUpdate();
            closeConnection();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Product product) throws SQLException {
        getConnection();
        String query = "DELETE FROM ov_chipkaart_product WHERE product_nummer = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, product.getProduct_nummer());
        statement.executeUpdate();
        query = "DELETE FROM product WHERE product_nummer = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, product.getProduct_nummer());
        statement.executeUpdate();
        closeConnection();
        return true;
    }

    @Override
    public Product findById(int id) throws SQLException {
        getConnection();
        String query = "SELECT * FROM product WHERE product_nummer = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet set = statement.executeQuery();
        closeConnection();
        if (set.next()) {
            return new Product(set.getInt("product_nummer"), set.getString("naam")
                    , set.getString("beschrijving"), set.getFloat("prijs"));
        }
        return null;
    }

    public List<Product> findByOVChipkaart(OV_chipkaart ovChipkaart) throws SQLException {
        try {
            System.out.println("OVChipkaart in findby ovchipkaart: ");
            System.out.println(ovChipkaart);
            if(ovChipkaart == null){
                return null;
            }
            getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM product p JOIN ov_chipkaart_product ocp ON p.product_nummer = ocp.product_nummer WHERE ocp.kaart_nummer = ?"
            );
            stmt.setInt(1, ovChipkaart.getKaart_nummer());
            ResultSet rs = stmt.executeQuery();
            Product transferProduct = null;
            List<Product> producten = new ArrayList<>();
            while (rs.next()) {
                transferProduct = new Product(
                        rs.getInt("product_nummer"),
                        rs.getString("naam"),
                        rs.getString("beschrijving"),
                        (float) rs.getDouble("prijs")
                );
                transferProduct.addOVKaart(ovChipkaart);
                producten.add(transferProduct);
            }
            closeConnection();
            return producten;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Product> findAll() throws SQLException {
        getConnection();
        String query = "SELECT * FROM product;";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet set = statement.executeQuery();
        closeConnection();
        ArrayList<Product> returnlist = new ArrayList<>();
        while (set != null && set.next()) {
            returnlist.add(new Product(set.getInt("product_nummer"), set.getString("naam")
                    , set.getString("beschrijving"), set.getFloat("prijs")));
        }
        return returnlist;
    }
}
