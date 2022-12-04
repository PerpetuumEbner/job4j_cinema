package ru.job4j.cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CinemaHallDBStore {
    private static final Logger LOG = LogManager.getLogger(UserDBStore.class);

    private final BasicDataSource pool;

    public CinemaHallDBStore(BasicDataSource pool) {
        this.pool = pool;
    }
}