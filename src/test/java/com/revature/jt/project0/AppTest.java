package com.revature.jt.project0;

import com.revature.jt.project0.model.Note;
import com.revature.jt.project0.db.NoteDataSource;
import com.revature.jt.project0.db.NoteSQL;
import com.revature.jt.project0.file.NoteJsonMap;
import com.revature.jt.project0.file.NoteCsvMap;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class AppTest {
    private NoteDataSource ds;

    @Before
    @Test
    public void initialize() throws IOException {
        InputStream input = AppTest.class.getClassLoader().getResourceAsStream("app.properties");
        Properties prop = new Properties(System.getProperties());
        prop.load(input);
        System.setProperties(prop);

        ds = NoteDataSource.getInstance();
    }

    @Test
    public void streamingSum() {
        int prims[] = {1, 2, 3, 4, 5};
        Integer addition = Arrays.stream(prims).sum();
        assertEquals((float) 15, (float) addition, 0);
    }

    @Test
    public void connectDb() throws SQLException {
        Connection conn = ds.getConnected();
        String productName = conn.getMetaData().getDatabaseProductName();

        assertEquals(productName, "H2");
    }

    @Test
    public void addNote() throws SQLException {
        Note note = new Note("This is a test", "test", LocalDateTime.now());
        NoteSQL noteDB = new NoteSQL(ds);
        noteDB.insertNote(note);

        List<Note> retrieved = noteDB.getAllNotes();
        Note record = retrieved.get(0);
        assertEquals(note, record);
    }

    @Test
    public void readJson() {
        NoteJsonMap map = new NoteJsonMap("notes.json");
        System.out.println(map.getNotes());
        try {
            NoteSQL noteDB = new NoteSQL(ds);
            noteDB.nuke();
            map.saveToDB(ds);
            List<Note> retrieved = noteDB.getAllNotes();
            System.out.println(retrieved);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readCsv() {
        NoteCsvMap map = new NoteCsvMap("notes.csv");
        System.out.println(map.getNotes());
    }

}
