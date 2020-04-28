package com.revature.jt.project0.file;

import com.revature.jt.project0.model.Note;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonLoader extends FileLoader {
    private static final Logger logger = LoggerFactory.getLogger(JsonLoader.class);

    public JsonLoader(String file) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            files = Arrays.asList(objectMapper.readerFor(Note[].class).readValue(br));
            logger.debug("Reading json...");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("IOException", e);
        }
    }
}
