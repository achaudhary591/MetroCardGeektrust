package com.geektrust.backend.repositories.implementation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import com.geektrust.backend.models.MetroCard;
import com.geektrust.backend.models.Passenger;
import com.geektrust.backend.repositories.PassengerRepository;

public class PassengerRepositoryImpl implements PassengerRepository {
    private final Map<String, Passenger> passengerMap;
    private int autoIncrement = 0;

    public PassengerRepositoryImpl(Map<String, Passenger> passengerMap) {
        this.passengerMap = passengerMap;
        this.autoIncrement = calculateInitialAutoIncrement(passengerMap);
    }

    public PassengerRepositoryImpl() {
        this(new HashMap<>());
    }

    @Override
    public Passenger save(Passenger passenger) {
        if(passenger.getId() == null) {
            String newId = generateNewId();
            Passenger newPassenger = new Passenger(newId, passenger.getMetroCard(), passenger.getPassengerType(), passenger.getBoardingStation());
            passengerMap.put(newId, newPassenger);
            return newPassenger;
        }
        passengerMap.put(passenger.getId(), passenger);
        return passenger;
    }

    @Override
    public Optional<Passenger> findByMetroCard(MetroCard metroCard) {
        return passengerMap.values().stream()
                           .filter(passenger -> passenger.getMetroCard().equals(metroCard))
                           .findFirst();
    }

    private String generateNewId() {
        autoIncrement++;
        return Integer.toString(autoIncrement);
    }

    private int calculateInitialAutoIncrement(Map<String, Passenger> map) {
        return map.values().stream()
                  .mapToInt(passenger -> Integer.parseInt(passenger.getId()))
                  .max()
                  .orElse(0);
    }
}
