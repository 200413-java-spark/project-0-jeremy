package com.revature.project0.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "NOTES")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String entry;

    @Column
    private String category;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime creationDateTime;

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

    public LocalDateTime getCreatedDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    @Override
    public String toString() {
        return "note entry: " + entry + "\nnote category: " + category
            + "\n[" + creationDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "]";
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
