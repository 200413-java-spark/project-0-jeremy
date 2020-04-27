package com.revature.jt.project0.file;

import com.revature.jt.project0.model.Note;
import com.revature.jt.project0.db.NoteDataSource;
import com.revature.jt.project0.db.NoteSQL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.sql.SQLException;

import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoteCsvMap {
    private List<Note> notes;
    private static final Logger logger = LoggerFactory.getLogger(NoteCsvMap.class);

    public NoteCsvMap(String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            notes = new CsvToBeanBuilder(br).withType(Note.class).build().parse();
            logger.debug("Reading csv...");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("CSV IOException", e);
        }
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void saveToDB(NoteDataSource ds) throws SQLException {
        NoteSQL noteDB = new NoteSQL(ds);
        noteDB.insertNoteList(notes);
    }
}
