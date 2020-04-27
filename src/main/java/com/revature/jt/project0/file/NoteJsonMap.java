package com.revature.jt.project0.file;

import com.revature.jt.project0.model.Note;
import com.revature.jt.project0.db.NoteDataSource;
import com.revature.jt.project0.db.NoteSQL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoteJsonMap {
    private List<Note> notes;
    private static final Logger logger = LoggerFactory.getLogger(NoteCsvMap.class);

    public NoteJsonMap(String file) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            notes = Arrays.asList(objectMapper.readerFor(Note[].class).readValue(br));
            logger.debug("Reading json...");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("IOException", e);
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
