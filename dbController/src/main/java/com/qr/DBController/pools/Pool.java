package com.qr.DBController.pools;

import org.hibernate.Session;

public interface Pool {

    public void initialize();

    public Session getPool();

}
