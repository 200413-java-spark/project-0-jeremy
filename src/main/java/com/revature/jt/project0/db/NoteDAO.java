package com.revature.jt.project0.db;

import java.util.List;

public interface NoteDAO<N> {
    N getNote(Integer id);
    List<N> getAllNotes();
    List<N> getNoteByCategory(String category);
    void insertNote(N n);
    void insertNoteList(List<N> n);
    void updateNote(N n);
    void deleteNote(Integer id);
    void nuke();
}