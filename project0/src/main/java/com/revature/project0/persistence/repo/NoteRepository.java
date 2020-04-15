package com.revature.project0.persistence.repo;

import org.springframework.data.repository.CrudRepository;

import com.revature.project0.persistence.model.Note;

public interface NoteRepository extends CrudRepository<Note, Long> {

}