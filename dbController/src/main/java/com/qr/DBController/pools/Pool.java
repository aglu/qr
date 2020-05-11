package com.qr.DBController.pools;

import org.hibernate.Session;

public interface Pool {

    void initialize();

    Session getPool();

}
