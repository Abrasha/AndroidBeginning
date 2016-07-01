package com.example.aabrasha.firstandroidapp;

import com.example.aabrasha.firstandroidapp.activity.crime.helper.CrimeHolder;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void crimeHolderTest() {
        CrimeHolder holder = CrimeHolder.getInstance();
        assertNotNull(holder);
        assertTrue(holder.getCrimes().size() > 0);
    }
}