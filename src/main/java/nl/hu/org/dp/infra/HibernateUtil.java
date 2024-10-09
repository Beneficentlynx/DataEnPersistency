package nl.hu.org.dp.infra;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;

public abstract class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static Session session;

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getSession() {
        return session;
    }

    public static void openSession() {
        session = getSessionFactory().openSession();
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

}
