package nl.hu.org.dp.Domain.DAO;

import nl.hu.org.dp.Domain.OV_chipkaart;
import nl.hu.org.dp.Domain.Reiziger;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface OV_chipkaartDOA {

    boolean save(OV_chipkaart ov_chipkaart) throws SQLException;

    boolean update(OV_chipkaart ov_chipkaart) throws SQLException;
    boolean delete(OV_chipkaart ov_chipkaart) throws SQLException;
    OV_chipkaart findById(int id) throws SQLException;
    List<OV_chipkaart> findAll() throws SQLException;
}
