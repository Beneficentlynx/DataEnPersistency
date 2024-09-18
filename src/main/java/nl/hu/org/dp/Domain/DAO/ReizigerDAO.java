package nl.hu.org.dp.Domain.DAO;

import nl.hu.org.dp.Domain.Reiziger;

import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

public interface ReizigerDAO {

    boolean save(Reiziger reiziger) throws SQLException;

    boolean update(Reiziger reiziger) throws SQLException;
    boolean delete(Reiziger reiziger) throws SQLException;
    Reiziger findById(int id) throws SQLException;
    List<Reiziger> findByGbdatum(Date datum) throws SQLException;
    List<Reiziger> findAll() throws SQLException;
}
