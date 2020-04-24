package com.revature.jt.project0;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonFormat;

// basic entity
public class Note {

    private Integer id;
    private String entry;
    private String category;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime creationDateTime;

    public Note() {
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

    public LocalDateTime getCreationDateTime() {
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

        if (!creationDateTime.equals(note.creationDateTime))
            return false;
        if (!entry.equals(note.entry))
            return false;
        if (!category.equals(note.category))
            return false;

        return true;
    }
}
