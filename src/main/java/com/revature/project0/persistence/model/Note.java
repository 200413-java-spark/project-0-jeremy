package com.revature.project0.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private LocalDateTime creationDateTime;

    @Column
    private String entry;

    @Column
    private String category;

    protected Note() {
    }

    public Note(String entry, String category, LocalDateTime creationDateTime) {
        this.entry = entry;
        this.category = category;
        this.creationDateTime = creationDateTime;

    }

    public Note(String entry, String category) {
        this.entry = entry;
        this.category = category;
        this.creationDateTime = LocalDateTime.now();
    }

    public Note(String entry) {
        this.entry = entry;
        this.category = "default";
        this.creationDateTime = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "note entry: " + entry
            + "\nnote category: " + category
            + "\n[" + creationDateTime + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Note note = (Note) o;

        if (!id.equals(note.id))
            return false;
        if (!creationDateTime.equals(note.creationDateTime))
            return false;
        if (!entry.equals(note.entry))
            return false;

        return true;
    }
}