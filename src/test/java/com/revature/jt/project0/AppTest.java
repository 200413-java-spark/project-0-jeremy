package com.revature.jt.project0;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Arrays;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    //@Test(expected = )

    @Test
    public void streamingSum() {
        int prims[] = {1,2,3,4,5};
        Integer addition = Arrays.stream(prims).sum();
        assertEquals((float) 15, (float) addition, 0);
    }
}
