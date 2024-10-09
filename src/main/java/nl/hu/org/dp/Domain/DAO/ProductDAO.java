package nl.hu.org.dp.Domain.DAO;

import nl.hu.org.dp.Domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    boolean save(Product product) throws SQLException;
    boolean update(Product product) throws SQLException;
    boolean delete(Product product) throws SQLException;
    Product findById(int id) throws SQLException;
    List<Product> findAll() throws SQLException;
}
