package com.geektrust.backend.services.implementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.geektrust.backend.dtos.PassengerTypeCount;
import com.geektrust.backend.models.Passenger;
import com.geektrust.backend.models.enums.PassengerType;
import com.geektrust.backend.models.Station;
import com.geektrust.backend.exceptions.StationNotFoundException;
import com.geektrust.backend.repositories.StationRepository;

public class StationServiceImplHelper {

    private final StationRepository stationRepository;

    public StationServiceImplHelper(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }


    List<PassengerTypeCount> getPassengerSummaryHelper(Map<PassengerType, Integer> passengerTypeCountMap) {
        return passengerTypeCountMap.entrySet().stream().map(entry -> new PassengerTypeCount(entry.getKey(), entry.getValue())).sorted().collect(Collectors.toList());
    }

    Map<PassengerType, Integer> getCountPassengerTypeWise(List<Passenger> passengers) {
        Map<PassengerType, Integer> passengerTypeCountMap = new HashMap<>();
        final int DEFAULT_COUNT = 0;

        for(Passenger passenger : passengers)
        {
            PassengerType passengerType = passenger.getPassengerType();
            int count = passengerTypeCountMap.getOrDefault(passengerType, DEFAULT_COUNT);
            count++;
            passengerTypeCountMap.put(passengerType, count);
        }
        return passengerTypeCountMap;
    }

    int getRevisedTravelCharge(int travelCharge) {
        final double DISCOUNT_RATE = 0.5;
        return (int)(DISCOUNT_RATE * travelCharge);
    }

    void collectDiscount(Passenger passenger, int discount) {
        String stationName = passenger.getBoardingStation();
        Station station = stationRepository.findByName(stationName).orElseThrow(StationNotFoundException::new);
        station.addDiscount(discount);
    }
}
