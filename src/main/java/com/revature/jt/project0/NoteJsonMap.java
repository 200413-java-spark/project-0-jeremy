package com.revature.jt.project0;

import com.revature.jt.project0.Note;
import com.revature.jt.project0.NoteSQL;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.sql.SQLException;

public class NoteJsonMap {
    private List<Note> notes;

    public NoteJsonMap(String file) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            notes = Arrays.asList(objectMapper.readerFor(Note[].class).readValue(new File(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToDB() throws SQLException {
        NoteSQL noteDB = new NoteSQL();
        noteDB.insertNoteList(notes);
    }
}