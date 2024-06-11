package com.geektrust.backend.models;

import com.geektrust.backend.models.enums.PassengerType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassengerTest {
    private MetroCard metroCard;

    @BeforeEach
    public void setup() {
        metroCard = new MetroCard("1", "MC1", 100);
    }

    @Test
    public void setBoardingStationShouldSetBoardingStation() {

        String expectedBoardingStation = "AIRPORT";
        Passenger passenger = new Passenger(metroCard, PassengerType.ADULT, "CENTRAL");


        passenger.setBoardingStation("AIRPORT");
        String actualBoardingStation = passenger.getBoardingStation();


        Assertions.assertEquals(expectedBoardingStation, actualBoardingStation);
    }

    @Test
    public void updateJourneyTypeCodeShouldUpdateToZeroForFirstCall() {

        int expectedJourneyTypeCode = 0;
        Passenger passenger = new Passenger("1", metroCard, PassengerType.ADULT, "CENTRAL");


        passenger.updateJourneyTypeCode();
        int actualJourneyTypeCode = passenger.getJourneyTypeCode();


        Assertions.assertEquals(expectedJourneyTypeCode, actualJourneyTypeCode);
    }

    @Test
    public void updateJourneyTypeCodeShouldUpdateToOneForSecondCall() {

        int expectedJourneyTypeCode = 1;
        Passenger passenger = new Passenger("1", metroCard, PassengerType.ADULT, "CENTRAL");


        passenger.updateJourneyTypeCode();
        passenger.updateJourneyTypeCode();
        int actualJourneyTypeCode = passenger.getJourneyTypeCode();


        Assertions.assertEquals(expectedJourneyTypeCode, actualJourneyTypeCode);
    }

    @Test
    public void updateJourneyTypeCodeShouldUpdateToZeroForThirdCall() {

        int expectedJourneyTypeCode = 0;
        Passenger passenger = new Passenger("1", metroCard, PassengerType.ADULT, "CENTRAL");


        passenger.updateJourneyTypeCode();
        passenger.updateJourneyTypeCode();
        passenger.updateJourneyTypeCode();
        int actualJourneyTypeCode = passenger.getJourneyTypeCode();


        Assertions.assertEquals(expectedJourneyTypeCode, actualJourneyTypeCode);
    }
}
