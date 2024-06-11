package com.geektrust.backend.services;

import java.util.List;
import com.geektrust.backend.dtos.CollectionSummary;
import com.geektrust.backend.dtos.PassengerSummary;
import com.geektrust.backend.models.Passenger;
import com.geektrust.backend.models.Station;

public interface StationService {
    Station create(String stationName);
    void addPassengerToBoardedList(Passenger passenger);
    void collectTravelCharge(Passenger passenger, int travelCharge);
    void collectServiceFee(Passenger passenger, int rechargeAmount);
    List<Station> getAllStations();
    int getTravelCharge(Passenger passenger);
    CollectionSummary getCollectionSummary(Station station);
    PassengerSummary getPassengerSummary(Station station);
}
