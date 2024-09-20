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
            if(findById(ov_chipkaart.getKaart_nummer()) != null){
                return false;
            }
            nl.hu.org.dp.infra.hibernate.OV_chipkaart infraReizeger = new nl.hu.org.dp.infra.hibernate.OV_chipkaart(ov_chipkaart.getKaart_nummer(),
                    ov_chipkaart.getGeldig_tot(), ov_chipkaart.getKlasse(), ov_chipkaart.getSaldo(), ov_chipkaart.getReiziger_id());
            session.persist(infraReizeger);
            session.beginTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(OV_chipkaart ov_chipkaart) throws SQLException {
        try {
            nl.hu.org.dp.infra.hibernate.OV_chipkaart infraReizeger = session.get(nl.hu.org.dp.infra.hibernate.OV_chipkaart.class, ov_chipkaart.getKaart_nummer());
            infraReizeger.setGeldig_tot(ov_chipkaart.getGeldig_tot());
            infraReizeger.setKlasse(ov_chipkaart.getKlasse());
            infraReizeger.setSaldo(ov_chipkaart.getSaldo());
            session.merge(infraReizeger);
            session.beginTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(OV_chipkaart ov_chipkaart) throws SQLException {
        try {
            nl.hu.org.dp.infra.hibernate.OV_chipkaart infraOV = session.get(nl.hu.org.dp.infra.hibernate.OV_chipkaart.class, ov_chipkaart.getKaart_nummer());
            session.remove(infraOV);
            session.beginTransaction().commit();
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
            return new OV_chipkaart(infraOV.getKaart_nummer(), (Date) infraOV.getGeldig_tot(), infraOV.getKlasse(), (float) infraOV.getSaldo(), infraOV.getReiziger_id());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<OV_chipkaart> findAll() throws SQLException {
        List<nl.hu.org.dp.infra.hibernate.OV_chipkaart> infraOVs = session.createQuery("from OV_chipkaart").list();
        List<OV_chipkaart> ovs = new ArrayList<>();
        for (nl.hu.org.dp.infra.hibernate.OV_chipkaart infraOV : infraOVs) {
            OV_chipkaart ov = new OV_chipkaart(infraOV.getKaart_nummer(), infraOV.getGeldig_tot(), infraOV.getKlasse(), (float) infraOV.getSaldo(), infraOV.getReiziger_id());
            ovs.add(ov);
        }
        return ovs;
    }

    public List<OV_chipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        List<nl.hu.org.dp.infra.hibernate.OV_chipkaart> infraOVs = session.createQuery("from OV_chipkaart where reiziger_id = :id").setParameter("id", reiziger.getReiziger_id()).list();
        List<OV_chipkaart> ovs = new ArrayList<>();
        for (nl.hu.org.dp.infra.hibernate.OV_chipkaart infraOV : infraOVs) {
            OV_chipkaart ov = new OV_chipkaart(infraOV.getKaart_nummer(), (Date) infraOV.getGeldig_tot(), infraOV.getKlasse(), (float) infraOV.getSaldo(), infraOV.getReiziger_id());
            ovs.add(ov);
        }
        return ovs;
    }
}
