package com.revature.jt.project0;

import com.revature.jt.project0.db.NoteDataSource;
import com.revature.jt.project0.db.NoteSQL;
import com.revature.jt.project0.file.CsvLoader;
import com.revature.jt.project0.file.FileLoader;
import com.revature.jt.project0.file.JsonLoader;
import com.revature.jt.project0.model.Note;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class AppTest {
    private NoteSQL db;

    @Before
    public void initialize() throws IOException {
        InputStream input = AppTest.class.getClassLoader().getResourceAsStream("app.properties");
        Properties prop = new Properties(System.getProperties());
        prop.load(input);
        System.setProperties(prop);
        db = new NoteSQL(NoteDataSource.getInstance());
    }

    @Ignore
    @Test
    public void readJsonToDbTest() {
        FileLoader loader = new JsonLoader("test.json");
        System.out.println(loader.getNotes());
        try {
            db.nuke();
            loader.saveToDB(db);
            List<Note> retrieved = db.getAllNotes();
            assertEquals(loader.getNotes(), retrieved);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Ignore
    @Test
    public void readCsvToDbTest() {
        FileLoader loader = new CsvLoader("test.csv");
        System.out.println(loader.getNotes());
        try {
            db.nuke();
            loader.saveToDB(db);
            List<Note> retrieved = db.getAllNotes();
            assertEquals(loader.getNotes(), retrieved);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
