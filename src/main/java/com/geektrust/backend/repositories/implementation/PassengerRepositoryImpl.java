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
        this.autoIncrement = passengerMap.size();
    }

    public PassengerRepositoryImpl() {
        this.passengerMap = new HashMap<>();
    }

    @Override
    public Passenger save(Passenger entity) {
        if(entity.getId() == null) {
            autoIncrement++;
            Passenger passenger = new Passenger(Integer.toString(autoIncrement), entity.getMetroCard(), entity.getPassengerType(), entity.getBoardingStation());
            passengerMap.put(passenger.getId(), passenger);
            return passenger;
        }
        passengerMap.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<Passenger> findByMetroCard(MetroCard metroCard) {
        return passengerMap.values().stream().filter(passenger -> passenger.getMetroCard().equals(metroCard)).findFirst();
    }
}
