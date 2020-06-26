package com.revature.jt.project0.file;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class FileTypeTest {


    @Test
    public void testType() {
        String jsonTestFileName = "test.json";
        String csvTestFileName = "test.csv";
        String jsonExt = jsonTestFileName.substring(jsonTestFileName.indexOf('.') + 1).toLowerCase();
        String csvExt = csvTestFileName.substring(csvTestFileName.indexOf('.') + 1).toLowerCase();

        assertEquals(jsonExt, "json");
        assertEquals(csvExt, "csv");
    }
}