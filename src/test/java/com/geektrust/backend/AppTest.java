package com.geektrust.backend;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("App Test")
class AppTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test()
    public void applicationTest() {
        
        String[] arguments = new String[]{"sample_input/input1.txt"};
        String expectedOutput = "TOTAL_COLLECTION CENTRAL 300 0\n" +
        "PASSENGER_TYPE_SUMMARY\n" +
        "ADULT 1\n" +
        "SENIOR_CITIZEN 1\n" +
        "TOTAL_COLLECTION AIRPORT 403 100\n" +
        "PASSENGER_TYPE_SUMMARY\n" +
        "ADULT 2\n" +
        "KID 2";

        
        App.main(arguments);

        
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
} 
