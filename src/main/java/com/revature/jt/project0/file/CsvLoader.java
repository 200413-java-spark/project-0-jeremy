package com.revature.jt.project0.file;

import com.revature.jt.project0.model.Note;

import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CsvLoader extends FileLoader {
    private static final Logger logger = LoggerFactory.getLogger(CsvLoader.class);

    public CsvLoader(String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            noteList = new CsvToBeanBuilder<Note>(br).withType(Note.class).build().parse();
            logger.debug("Reading csv...");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("CSV IOException", e);
        }
    }
}
