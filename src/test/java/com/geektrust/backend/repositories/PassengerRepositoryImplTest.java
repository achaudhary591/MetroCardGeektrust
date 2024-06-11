package com.geektrust.backend.repositories;

import com.geektrust.backend.models.MetroCard;
import com.geektrust.backend.models.Passenger;
import com.geektrust.backend.models.enums.PassengerType;
import com.geektrust.backend.repositories.implementation.PassengerRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PassengerRepositoryImplTest {
    private PassengerRepository passengerRepository;

    @BeforeEach
    public void setup() {
        Map<String, Passenger> passengerMap = new HashMap<>();
        MetroCard metroCard1 = new MetroCard("1", "MC1", 100);
        MetroCard metroCard2 = new MetroCard("2", "MC2", 200);

        passengerMap.put("1", new Passenger("1", metroCard1, PassengerType.ADULT, "CENTRAL"));
        passengerMap.put("2", new Passenger("2", metroCard2, PassengerType.KID, "AIRPORT"));

        passengerRepository = new PassengerRepositoryImpl(passengerMap);
    }

    @Test
    public void savePassengerShouldSavePassenger() {

        MetroCard metroCard3 = new MetroCard("3", "MC3", 300);
        Passenger passenger3 = new Passenger(metroCard3, PassengerType.ADULT, "CENTRAL");
        Passenger expectedPassenger = new Passenger("3", metroCard3, PassengerType.ADULT, "CENTRAL");


        Passenger actualPassenger = passengerRepository.save(passenger3);


        Assertions.assertEquals(expectedPassenger, actualPassenger);
    }

    @Test
    public void findByMetroCardShouldReturnPassengerGivenMetroCard() {

        MetroCard metroCard2 = new MetroCard("2", "MC2", 200);
        Passenger expectedPassenger = new Passenger("2", metroCard2, PassengerType.KID, "AIRPORT");


        Passenger actualPassenger = passengerRepository.findByMetroCard(metroCard2).get();


        Assertions.assertEquals(expectedPassenger, actualPassenger);
    }

    @Test
    public void findByMetroCardShouldReturnEmptyIfMetroCardNotFound() {

        MetroCard metroCard3 = new MetroCard("3", "MC3", 300);
        Optional<Passenger> expectedPassenger = Optional.empty();


        Optional<Passenger> actualPassenger = passengerRepository.findByMetroCard(metroCard3);


        Assertions.assertEquals(expectedPassenger, actualPassenger);
    }
}
