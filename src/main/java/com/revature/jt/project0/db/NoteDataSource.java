package com.revature.jt.project0.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoteDataSource {
    private static final Logger logger = LoggerFactory.getLogger(NoteDataSource.class);
    private static NoteDataSource instance;
    private String url;
    private String user;
    private String password;

    private NoteDataSource() {
        url = System.getProperty("db.url");
        user = System.getProperty("db.user");
        password = System.getProperty("db.password");
        logger.debug("Initiating datasource...");
    }

    public static NoteDataSource getInstance() {
        if (instance == null) {
            instance = new NoteDataSource();
        }
        return instance;
    }

    public Connection getConnected() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to db ", e);
        }
    }
}
