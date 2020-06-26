package com.revature.jt.project0.file;

import com.revature.jt.project0.db.NoteSQL;
import com.revature.jt.project0.model.Note;

import java.sql.SQLException;
import java.util.List;

public class FileLoader implements Runnable {
    protected List<Note> noteList;

    public FileLoader() {
    }

    public List<Note> getNotes() {
        return noteList;
    }

    public void saveToDB(NoteSQL db) throws SQLException {
        db.insertNoteList(noteList);
    }

    public void run() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("NEW THREAD!");
            }
        });
    }
}
