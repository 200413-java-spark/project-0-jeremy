package com.revature.jt.project0.file;

import com.revature.jt.project0.model.Note;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class FileTest {
    @Test
    public void readJsonTest() {
        FileLoader map = new JsonLoader("test.json");
        LocalDateTime expectedDateTime = LocalDateTime.parse("2020-04-13 08:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Note expectedNote = new Note("Day 1 at Revature", "default", expectedDateTime);
        assertEquals(expectedNote, map.getNotes().get(0));
    }

    @Test
    public void readCsvTest() {
        FileLoader map = new CsvLoader("test.csv");
        LocalDateTime expectedDateTime = LocalDateTime.parse("2020-03-16 07:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Note expectedNote = new Note("Day 1 in quarantine","default",expectedDateTime);
        assertEquals(expectedNote, map.getNotes().get(0));
    }
}