package nl.hu.org.dp.Domain.DAO;

import nl.hu.org.dp.Domain.Adres;

import java.sql.SQLException;
import java.util.List;

public interface AdresDOA {
    boolean save(Adres adres)throws SQLException;

    boolean update(Adres adres)throws SQLException;

    boolean delete(Adres adres)throws SQLException;


    List<Adres> findall()throws SQLException;
}
