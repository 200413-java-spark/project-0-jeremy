package com.revature.jt.project0;

import org.h2.jdbcx.JdbcDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectH2 {
    public static Connection getConnected () {
        // attempt connection using system properties set by file in classpath
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL(System.getProperty("db.url"));
        ds.setUser(System.getProperty("db.user"));
        ds.setPassword(System.getProperty("db.password"));
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to db ", e);
        }
    }

    // static method to create connections to db
    public static Connection getConnected (String dburl, String dbuser, String dbpw) {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL(dburl);
        ds.setUser(dbuser);
        ds.setPassword(dbpw);
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to db ", e);
        }
    }
}
