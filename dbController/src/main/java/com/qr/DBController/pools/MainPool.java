package com.qr.DBController.pools;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MainPool {

    private static SessionFactory ourSessionFactory;
    private static final Object MONITOR = new Object();


    public static void initialize() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        ourSessionFactory = configuration.buildSessionFactory();
    }

    public static Session getPool() {
        synchronized (MONITOR) {
            if (ourSessionFactory == null) {
                initialize();
            }
        }
        return ourSessionFactory.openSession();
    }
}
