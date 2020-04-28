package com.revature.jt.project0.file;

import com.revature.jt.project0.file.FileLoader;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class FileTypeTest {


    @Test
    public void testType() {
        FileLoader jsontestLoader = new JsonLoader("test.json");
        FileLoader csvtestloader = new CsvLoader("test.csv");
        assertTrue(true);
    }
}