package nl.hu.org.dp.infra.hibernate;

import nl.hu.org.dp.Domain.DAO.ReizigerDAO;
import nl.hu.org.dp.infra.hibernate.Reiziger;

import nl.hu.org.dp.infra.HibernateUtil;
import org.hibernate.Session;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.Date;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {
    private Session session;

    public ReizigerDAOHibernate() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    @Override
    public boolean save(nl.hu.org.dp.Domain.Reiziger reiziger) throws SQLException {
        try {
            if(findById(reiziger.getReiziger_id()) != null){
                return false;
            }
            Reiziger infraReizeger = new Reiziger(reiziger.getReiziger_id(), reiziger.getVoorletters(), reiziger.getTussenvoegsel(), reiziger.getAchternaam(), reiziger.getGeboortedatum());
            session.beginTransaction();
            session.persist(infraReizeger);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    @Override
    public boolean update(nl.hu.org.dp.Domain.Reiziger reiziger) throws SQLException {
        try {
            Reiziger infraReizeger = session.get(Reiziger.class, reiziger.getReiziger_id());
            if(infraReizeger == null){
                return false;
            }
            infraReizeger.setVoorletters(reiziger.getVoorletters());
            infraReizeger.setTussenvoegsel(reiziger.getTussenvoegsel());
            infraReizeger.setAchternaam(reiziger.getAchternaam());
            infraReizeger.setGeboortedatum(reiziger.getGeboortedatum());
            session.beginTransaction();
            session.merge(infraReizeger);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(nl.hu.org.dp.Domain.Reiziger reiziger) throws SQLException {
        try {
            Reiziger infraReizeger = session.get(Reiziger.class, reiziger.getReiziger_id());
            if(infraReizeger == null){
                return false;
            }
            session.beginTransaction();
            session.remove(infraReizeger);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public nl.hu.org.dp.Domain.Reiziger findById(int id) throws SQLException {
        try {
            Reiziger infraReizeger = session.get(Reiziger.class, id);
            if (infraReizeger == null) {
                return null;
            }
            nl.hu.org.dp.Domain.Reiziger domainREIZGER = new nl.hu.org.dp.Domain.Reiziger(infraReizeger.getReiziger_id(), infraReizeger.getVoorletters(), infraReizeger.getTussenvoegsel(), infraReizeger.getAchternaam(), infraReizeger.getGeboortedatum());
            return domainREIZGER;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<nl.hu.org.dp.Domain.Reiziger> findByGbdatum(Date datum) throws SQLException {
        try {
            ArrayList<Reiziger> infralists = (ArrayList<Reiziger>) session.createQuery(
                    "FROM Reiziger WHERE geboortedatum = :datum", Reiziger.class)
                    .setParameter("datum", datum).list();


            ArrayList<nl.hu.org.dp.Domain.Reiziger> domainList = new ArrayList<>();
            for (Reiziger X : infralists){
                nl.hu.org.dp.Domain.Reiziger domainREIZGER = new nl.hu.org.dp.Domain.Reiziger(X.getReiziger_id(), X.getVoorletters(), X.getTussenvoegsel(), X.getAchternaam(), X.getGeboortedatum());
                domainList.add(domainREIZGER);
            }
            return domainList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<nl.hu.org.dp.Domain.Reiziger> findAll() throws SQLException {
        try {
            ArrayList<Reiziger> infralists = (ArrayList<Reiziger>) session.createQuery(
                            "FROM Reiziger", Reiziger.class).list();
            ArrayList<nl.hu.org.dp.Domain.Reiziger> domainList = new ArrayList<>();
            for (Reiziger X : infralists){
                nl.hu.org.dp.Domain.Reiziger domainREIZGER = new nl.hu.org.dp.Domain.Reiziger(X.getReiziger_id(), X.getVoorletters(), X.getTussenvoegsel(), X.getAchternaam(), X.getGeboortedatum());
                domainList.add(domainREIZGER);
            }
            return domainList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
