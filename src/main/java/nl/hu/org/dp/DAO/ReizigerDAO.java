package nl.hu.org.dp.DAO;

import nl.hu.org.dp.Domain.Reiziger;

import java.util.List;

public interface ReizigerDAO {

    default boolean save(){
        return false;
    }

    default boolean update() {
        return false;
    }

    default boolean delete(){
        return false;
    }

    default List<Reiziger> findall(){
        return null;
    }
}
