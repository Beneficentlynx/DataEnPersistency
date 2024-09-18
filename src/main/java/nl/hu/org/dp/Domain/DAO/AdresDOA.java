package nl.hu.org.dp.Domain.DAO;

import nl.hu.org.dp.Domain.Adres;

import java.util.List;

public interface AdresDOA {
    default boolean save(){
        return false;
    }

    default boolean update() {
        return false;
    }

    default boolean delete(){
        return false;
    }

    default List<Adres> findall(){
        return null;
    }
}
