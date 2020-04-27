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
    public void readJson() {
        NoteJsonMap map = new NoteJsonMap("test.json");
        System.out.println(map.getNotes());
        try {
            NoteSQL noteDB = new NoteSQL(ds);
            noteDB.nuke();
            map.saveToDB(ds);
            List<Note> retrieved = noteDB.getAllNotes();
            assertEquals(map.getNotes(), retrieved);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readCsv() {
        NoteCsvMap map = new NoteCsvMap("test.csv");
        System.out.println(map.getNotes());
        try {
            NoteSQL noteDB = new NoteSQL(ds);
            noteDB.nuke();
            map.saveToDB(ds);
            List<Note> retrieved = noteDB.getAllNotes();
            assertEquals(map.getNotes(), retrieved);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
