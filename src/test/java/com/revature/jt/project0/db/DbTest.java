package com.revature.jt.project0.db;

import com.revature.jt.project0.model.Note;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;

public class DbTest {
    // private NoteDataSource ds;
    private DataSource ds;

    @Before
    @Test
    public void initialize() {
        try (InputStream input = this.getClass().getResourceAsStream("/app.properties")) {
            Properties prop = new Properties(System.getProperties());
            prop.load(input);
            System.setProperties(prop);

            ds = NoteDataSource.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Ignore
    @Test
    public void testH2DS() throws NamingException {
        ds = new JdbcDataSource();
        ((JdbcDataSource) ds).setURL("jdbc:h2:mem:test");
        ((JdbcDataSource) ds).setUser("sa");
        ((JdbcDataSource) ds).setPassword("sa");
        Context ctx = new InitialContext(); // ctx.createSubcontext("jdbc");

        ctx.bind("jdbc/dsName", ds);
        Context envCtx = (Context) ctx.lookup("java:comp/env");
        System.out.println(envCtx);
    }

    @Ignore
    @Test
    public void testJNDIDS() throws NamingException {
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        System.out.println(envCtx);
        // DataSource ds = (DataSource) ctx.lookup("jdbc/dsName");

    }

    @Test
    public void streamingSumTest() {
        int prims[] = {1, 2, 3, 4, 5};
        Integer addition = Arrays.stream(prims).sum();
        assertEquals((float) 15, (float) addition, 0);
    }

    @Test
    public void connectDbTest() throws SQLException {
        Connection conn = ds.getConnection();
        String productName = conn.getMetaData().getDatabaseProductName();

        assertEquals(productName, "H2");
    }

    @Test
    public void addNoteTest() throws SQLException {
        Note note = new Note("This is a test", "test", LocalDateTime.now());
        NoteSQL noteDB = new NoteSQL(ds);
        noteDB.nuke();
        noteDB.insertNote(note);

        List<Note> retrieved = noteDB.getAllNotes();
        Note record = retrieved.get(0);
        assertEquals(note, record);
    }

    @Test
    public void getLatestTest() throws SQLException {
        List<Note> notes = new ArrayList<>();
        notes.add(new Note("This is a test", "test", LocalDateTime.now()));
        notes.add(new Note("This is a test", "test", LocalDateTime.now()));
        notes.add(new Note("This is a test", "test", LocalDateTime.now()));
        NoteSQL noteDB = new NoteSQL(ds);
        noteDB.nuke();
        noteDB.insertNoteList(notes);

        List<Note> retrieved = noteDB.getLatest(50);
        assertEquals(notes.size(), retrieved.size());
    }

    @Test
    public void getCatsTest() throws SQLException {
        List<Note> notes = new ArrayList<>();
        notes.add(new Note("This is a test", "default", LocalDateTime.now()));
        notes.add(new Note("This is a test", "test1", LocalDateTime.now()));
        notes.add(new Note("This is a test", "test2", LocalDateTime.now()));
        NoteSQL noteDB = new NoteSQL(ds);
        noteDB.nuke();
        noteDB.insertNoteList(notes);

        List<String> retrieved = noteDB.getCategories();
        System.out.println(retrieved);
        assertEquals(retrieved, Arrays.asList("default", "test1", "test2"));
    }


}
