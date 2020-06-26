package com.revature.jt.project0.db;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public class NoteDataSource {
    // private static final Logger logger = LoggerFactory.getLogger(NoteDataSource.class);
    /*public NoteDataSource() {

    }

    public NoteDataSource(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;

    }*/

    public static DataSource getInstance() {
        /*if (instance == null) {
            instance = new NoteDataSource();
        }*/
        String url = System.getProperty("db.url");
        String user = System.getProperty("db.user");
        String password = System.getProperty("db.password");

        JdbcDataSource ds = new JdbcDataSource();
        try {
            Class.forName("org.h2.Driver");
            ds.setURL(url);
            ds.setUser(user);
            ds.setPassword(password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ds;
        // logger.debug("Initiating datasource...");
    }

    /*
     * public Connection getConnected() { try { conn = DriverManager.getConnection(url, user,
     * password); } catch (SQLException e) { logger.error("Error connecting to db", e); throw new
     * RuntimeException("Error connecting to db ", e); } return conn; }
     * 
     * public void closeConnection() { try { this.conn.close(); } catch (SQLException e) {
     * logger.error("Error closing db", e); } }
     */
}
