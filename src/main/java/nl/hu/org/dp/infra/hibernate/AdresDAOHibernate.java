package nl.hu.org.dp.infra.hibernate;

import nl.hu.org.dp.Domain.DAO.AdresDOA;
import nl.hu.org.dp.Domain.Adres;
import nl.hu.org.dp.infra.HibernateUtil;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOHibernate implements AdresDOA {
    private Session session;

    public AdresDAOHibernate() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    @Override
    public boolean save(Adres adres) {
        try {
            ReizigerDAOHibernate reizigerDAOHibernate = new ReizigerDAOHibernate();
            if (reizigerDAOHibernate.findById(adres.getReiziger_id()) == null) {
                return false;

            }
            System.out.println(adres);
            nl.hu.org.dp.infra.hibernate.Adres infraAdres = new nl.hu.org.dp.infra.hibernate.Adres(adres.getAdres_id(), adres.getPostcode(), adres.getHuisnummer(), adres.getStraat(), adres.getWoonplaats(), adres.getReiziger());
            session.save(infraAdres);
            session.beginTransaction().commit();
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
            return false;
        }
    }

        @Override
    public boolean update(Adres adres) {
        try {
            nl.hu.org.dp.infra.hibernate.Adres infraAdres = session.get(nl.hu.org.dp.infra.hibernate.Adres.class, adres.getReiziger_id());
            infraAdres.setPostcode(adres.getPostcode());
            infraAdres.setHuisnummer(adres.getHuisnummer());
            infraAdres.setStraat(adres.getStraat());
            infraAdres.setWoonplaats(adres.getWoonplaats());
            infraAdres.setReiziger_id(adres.getReiziger_id());
            session.merge(infraAdres);
            session.beginTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            nl.hu.org.dp.infra.hibernate.Adres infraAdres = session.get(nl.hu.org.dp.infra.hibernate.Adres.class, adres.getReiziger_id());
            session.delete(infraAdres);
            session.beginTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Adres> findall() {
        List<nl.hu.org.dp.infra.hibernate.Adres> infraAdressen = session.createQuery("from Adres").list();
        List<Adres> adressen = new ArrayList<>();
        for (nl.hu.org.dp.infra.hibernate.Adres infraAdres : infraAdressen) {
            Adres adres = new Adres(infraAdres.getAdres_id(), infraAdres.getPostcode(), infraAdres.getHuisnummer(), infraAdres.getStraat(), infraAdres.getWoonplaats(), infraAdres.getReiziger_id());
            adressen.add(adres);
        }
        return adressen;
    }

    public Adres findByAdresID(int id) {
        nl.hu.org.dp.infra.hibernate.Adres InfraAdres = session.get(nl.hu.org.dp.infra.hibernate.Adres.class, id);
        if(InfraAdres == null){
            return null;
        }
        Adres adres = new Adres(InfraAdres.getAdres_id(), InfraAdres.getPostcode(), InfraAdres.getHuisnummer(), InfraAdres.getStraat(), InfraAdres.getWoonplaats(), InfraAdres.getReiziger_id());
        return adres;
    }

    public Adres findByReiziger(int id) {

        try {
            nl.hu.org.dp.infra.hibernate.Adres InfraAdres = session.createQuery("from Adres where reiziger_id = :id", nl.hu.org.dp.infra.hibernate.Adres.class).setParameter("id", id).getSingleResult();

            if (InfraAdres == null) {
                return null;
            }
            Adres adres = new Adres(InfraAdres.getAdres_id(), InfraAdres.getPostcode(), InfraAdres.getHuisnummer(), InfraAdres.getStraat(), InfraAdres.getWoonplaats(), InfraAdres.getReiziger_id());
            return adres;
        }
        catch (Exception e){
            return null;
        }
    }
}
