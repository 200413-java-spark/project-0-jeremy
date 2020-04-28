package com.revature.jt.project0.file;

import com.revature.jt.project0.model.Note;
import com.revature.jt.project0.db.NoteDataSource;
import com.revature.jt.project0.db.NoteSQL;

import java.sql.SQLException;
import java.util.List;

public class FileLoader {
    protected List<Note> files;
    
    public FileLoader() {
    }

    public List<Note> getNotes() {
        return files;
    }

    public void saveToDB(NoteDataSource ds) throws SQLException {
        NoteSQL noteDB = new NoteSQL(ds);
        noteDB.insertNoteList(files);
    }
}