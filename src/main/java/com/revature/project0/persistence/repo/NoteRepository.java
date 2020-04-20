package com.revature.project0.persistence.repo;

import org.springframework.data.repository.CrudRepository;

import com.revature.project0.persistence.model.Note;

import java.util.List;

public interface NoteRepository extends CrudRepository<Note, Long> {
    List<Note> findByCategory(String category);
}