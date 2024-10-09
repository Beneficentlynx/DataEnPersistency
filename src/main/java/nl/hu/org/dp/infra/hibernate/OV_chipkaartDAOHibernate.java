package nl.hu.org.dp.infra.hibernate;

import nl.hu.org.dp.Domain.DAO.OV_chipkaartDOA;
import nl.hu.org.dp.Domain.OV_chipkaart;
import nl.hu.org.dp.infra.HibernateUtil;
import org.hibernate.Session;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OV_chipkaartDAOHibernate implements OV_chipkaartDOA {
    private Session session;

    public OV_chipkaartDAOHibernate() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    @Override
    public boolean save(OV_chipkaart ov_chipkaart) throws SQLException {
        try {
            session.beginTransaction();
            if(findById(ov_chipkaart.getKaart_nummer()) != null){
                return false;
            }
            nl.hu.org.dp.Domain.Reiziger domainReiziger = ov_chipkaart.getReiziger();
            nl.hu.org.dp.infra.hibernate.Reiziger reiziger = new nl.hu.org.dp.infra.hibernate.Reiziger(domainReiziger.getReiziger_id(), domainReiziger.getVoorletters(), domainReiziger.getTussenvoegsel(), domainReiziger.getAchternaam(), domainReiziger.getGeboortedatum());
            nl.hu.org.dp.infra.hibernate.OV_chipkaart infraOV = new nl.hu.org.dp.infra.hibernate.OV_chipkaart(ov_chipkaart.getKaart_nummer(),
                    ov_chipkaart.getGeldig_tot(), ov_chipkaart.getKlasse(), ov_chipkaart.getSaldo(), reiziger);

            session.persist(infraOV);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(OV_chipkaart ov_chipkaart) throws SQLException {
        try {
            session.beginTransaction();
            nl.hu.org.dp.infra.hibernate.OV_chipkaart infraReizeger = session.get(nl.hu.org.dp.infra.hibernate.OV_chipkaart.class, ov_chipkaart.getKaart_nummer());
            infraReizeger.setGeldig_tot(ov_chipkaart.getGeldig_tot());
            infraReizeger.setKlasse(ov_chipkaart.getKlasse());
            infraReizeger.setSaldo(ov_chipkaart.getSaldo());

            session.merge(infraReizeger);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(OV_chipkaart ov_chipkaart) throws SQLException {
        try {
            session.beginTransaction();
            nl.hu.org.dp.infra.hibernate.OV_chipkaart infraOV = session.get(nl.hu.org.dp.infra.hibernate.OV_chipkaart.class, ov_chipkaart.getKaart_nummer());

            session.remove(infraOV);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public OV_chipkaart findById(int id) throws SQLException {
        try {
            nl.hu.org.dp.infra.hibernate.OV_chipkaart infraOV = session.get(nl.hu.org.dp.infra.hibernate.OV_chipkaart.class, id);
            if (infraOV == null) {
                return null;
            }
            nl.hu.org.dp.infra.hibernate.Reiziger reiziger = infraOV.getReiziger();
            nl.hu.org.dp.Domain.Reiziger domainReiziger = new nl.hu.org.dp.Domain.Reiziger(reiziger.getReiziger_id(), reiziger.getVoorletters(), reiziger.getTussenvoegsel(), reiziger.getAchternaam(), reiziger.getGeboortedatum());
            return new OV_chipkaart(infraOV.getKaart_nummer(), (Date) infraOV.getGeldig_tot(), infraOV.getKlasse(), (float) infraOV.getSaldo(), domainReiziger);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<OV_chipkaart> findAll() throws SQLException {
        List<nl.hu.org.dp.infra.hibernate.OV_chipkaart> infraOVs = session.createQuery("from OV_chipkaart").list();
        List<OV_chipkaart> ovs = new ArrayList<>();
        for (nl.hu.org.dp.infra.hibernate.OV_chipkaart infraOV : infraOVs) {
            nl.hu.org.dp.infra.hibernate.Reiziger reiziger = infraOV.getReiziger();
            nl.hu.org.dp.Domain.Reiziger domainReiziger = new nl.hu.org.dp.Domain.Reiziger(reiziger.getReiziger_id(), reiziger.getVoorletters(), reiziger.getTussenvoegsel(), reiziger.getAchternaam(), reiziger.getGeboortedatum());
            OV_chipkaart ov = new OV_chipkaart(infraOV.getKaart_nummer(), infraOV.getGeldig_tot(), infraOV.getKlasse(), (float) infraOV.getSaldo(), domainReiziger);
            ovs.add(ov);
        }
        return ovs;
    }

    public List<OV_chipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        List<nl.hu.org.dp.infra.hibernate.OV_chipkaart> infraOVs = session.createQuery("from OV_chipkaart ov where ov.reiziger.reiziger_id = :id").setParameter("id", reiziger.getReiziger_id()).list();
        List<OV_chipkaart> ovs = new ArrayList<>();
        for (nl.hu.org.dp.infra.hibernate.OV_chipkaart infraOV : infraOVs) {
            nl.hu.org.dp.infra.hibernate.Reiziger infrareiziger = infraOV.getReiziger();
            nl.hu.org.dp.Domain.Reiziger domainReiziger = new nl.hu.org.dp.Domain.Reiziger(infrareiziger.getReiziger_id(), infrareiziger.getVoorletters(), infrareiziger.getTussenvoegsel(), infrareiziger.getAchternaam(), infrareiziger.getGeboortedatum());
            OV_chipkaart ov = new OV_chipkaart(infraOV.getKaart_nummer(), (Date) infraOV.getGeldig_tot(), infraOV.getKlasse(), (float) infraOV.getSaldo(), domainReiziger);
            ovs.add(ov);
        }
        return ovs;
    }
}
