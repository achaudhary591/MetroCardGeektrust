package com.geektrust.backend.models;

import com.geektrust.backend.models.enums.PassengerType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StationTest {
    @Test
    public void addTravelChargeShouldAddTravelChargeToTravelChargeCollection() {

        int expectedTravelChargeCollection = 200;
        Station station = new Station("1", "AIRPORT");


        station.addTravelCharge(200);
        int actualTravelChargeCollection = station.getTravelChargeCollection();


        Assertions.assertEquals(expectedTravelChargeCollection, actualTravelChargeCollection);
    }

    @Test
    public void addServiceFeeShouldAddServiceFeeToServiceFeeCollection() {

        int expectedServiceFeeCollection = 100;
        Station station = new Station("1", "AIRPORT");


        station.addServiceFee(100);
        int actualServiceFeeCollection = station.getServiceFeeCollection();


        Assertions.assertEquals(expectedServiceFeeCollection, actualServiceFeeCollection);
    }

    @Test
    public void addDiscountShouldAddDiscountToDiscountCollection() {

        int expectedDiscountCollection = 300;
        Station station = new Station("1", "AIRPORT");


        station.addDiscount(300);
        int actualDiscountCollection = station.getDiscountCollection();


        Assertions.assertEquals(expectedDiscountCollection, actualDiscountCollection);
    }

    @Test
    public void addPassengerShouldAddPassenger() {

        int expectedCount = 1;
        MetroCard metroCard = new MetroCard("1", "MC1", 100);
        Passenger passenger = new Passenger("1", metroCard, PassengerType.KID, "CENTRAL");
        Station station = new Station("1", "AIRPORT");


        station.addPassenger(passenger);
        int actualCount = station.getBoardedPassengers().size();


        Assertions.assertEquals(expectedCount, actualCount);
    }

    @Test
    public void getTotalCollectionShouldReturnTotalCollection() {

        int expectedTotalCollection = 500;
        Station station = new Station("1", "AIRPORT");
        station.addTravelCharge(400);
        station.addServiceFee(100);


        int actualTotalCollection = station.getTotalCollection();


        Assertions.assertEquals(expectedTotalCollection, actualTotalCollection);
    }
}
