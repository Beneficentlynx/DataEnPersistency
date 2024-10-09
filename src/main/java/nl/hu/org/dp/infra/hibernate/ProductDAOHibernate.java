package nl.hu.org.dp.infra.hibernate;

import nl.hu.org.dp.Domain.DAO.ProductDAO;
import nl.hu.org.dp.infra.HibernateUtil;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOHibernate implements ProductDAO {
    @Override
    public boolean save(nl.hu.org.dp.Domain.Product product) throws SQLException {
        try {
            List bestaandeProducten = findAll();
            for (Object x : bestaandeProducten){
                nl.hu.org.dp.infra.hibernate.Product y = (nl.hu.org.dp.infra.hibernate.Product) x;
                if (y.getProduct_nummer() == product.getProduct_nummer()){
                    return false;
                }
            }
            nl.hu.org.dp.infra.hibernate.Product infraProduct = new nl.hu.org.dp.infra.hibernate.Product(product.getProduct_nummer(), product.getNaam(), product.getBeschrijving(), product.getPrijs());
            HibernateUtil.getSession().persist(infraProduct);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(nl.hu.org.dp.Domain.Product product) throws SQLException {
        try {
            nl.hu.org.dp.Domain.Product Product = findById(product.getProduct_nummer());
            if(product == null){
                return false;
            }
            nl.hu.org.dp.infra.hibernate.Product infraProduct = new nl.hu.org.dp.infra.hibernate.Product(product.getProduct_nummer(), product.getNaam(), product.getBeschrijving(), product.getPrijs());
            HibernateUtil.getSession().merge(infraProduct);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(nl.hu.org.dp.Domain.Product product) throws SQLException {
        try {
            nl.hu.org.dp.infra.hibernate.Product infraProduct = HibernateUtil.getSession().get(Product.class, product.getProduct_nummer());
            if(infraProduct == null){
                return false;
            }
            HibernateUtil.getSession().remove(infraProduct);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public nl.hu.org.dp.Domain.Product findById(int id) throws SQLException {
        try{
            nl.hu.org.dp.infra.hibernate.Product product = HibernateUtil.getSession().get(Product.class, id);
            return new nl.hu.org.dp.Domain.Product(product.getProduct_nummer(), product.getNaam(), product.getBeschrijving(), product.getPrijs());
        }
        catch (Exception e){
            return null;
        }
    }

    public List<Product> findByOVChipkaart(OV_chipkaart ovChipkaart) throws SQLException {
        try {
            return HibernateUtil.getSession().createQuery("from Product p join fetch p.ovChipkaarten o where o.kaart_nummer = :kaartNummer", Product.class)
                    .setParameter("kaartNummer", ovChipkaart.getKaart_nummer())
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List findAll() throws SQLException {
        try {
            return HibernateUtil.getSession().createQuery("from Product").list();
        } catch (Exception e) {
            return null;
        }
    }
}
