package com.geektrust.backend.services.implementation;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.geektrust.backend.dtos.CollectionSummary;
import com.geektrust.backend.dtos.PassengerSummary;
import com.geektrust.backend.dtos.PassengerTypeCount;
import com.geektrust.backend.models.Passenger;
import com.geektrust.backend.models.enums.PassengerType;
import com.geektrust.backend.models.Station;
import com.geektrust.backend.models.enums.TravelCharge;
import com.geektrust.backend.exceptions.StationNotFoundException;
import com.geektrust.backend.repositories.StationRepository;
import com.geektrust.backend.services.StationService;

public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;
    private final StationServiceImplHelper stationServiceImplHelper;

    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
        stationServiceImplHelper = new StationServiceImplHelper(stationRepository);
    }

    @Override
    public Station create(String stationName) {
        Optional<Station> maybeStation = stationRepository.findByName(stationName);

        if (!maybeStation.isPresent()) {
            Station station = new Station(stationName);
            return stationRepository.save(station);
        }
        return maybeStation.get();

    }

    @Override
    public void addPassengerToBoardedList(Passenger passenger) {
        String stationName = passenger.getBoardingStation();
        Station station = stationRepository.findByName(stationName).orElseThrow(StationNotFoundException::new);
        station.addPassenger(new Passenger(passenger));
    }

    @Override
    public void collectTravelCharge(Passenger passenger, int travelCharge) {
        String stationName = passenger.getBoardingStation();
        Station station = stationRepository.findByName(stationName).orElseThrow(StationNotFoundException::new);
        station.addTravelCharge(travelCharge);
    }

    @Override
    public void collectServiceFee(Passenger passenger, int rechargeAmount) {
        String stationName = passenger.getBoardingStation();
        Station station = stationRepository.findByName(stationName).orElseThrow(StationNotFoundException::new);
        int serviceFee = calculateServiceFee(rechargeAmount);
        station.addServiceFee(serviceFee);
    }

    private int calculateServiceFee(int rechargeAmount) {
        final double SERVICE_FEE_RATE = 0.02;
        return (int) (SERVICE_FEE_RATE * rechargeAmount);
    }

    @Override
    public List<Station> getAllStations() {
        List<Station> stations = stationRepository.findAll();
        Collections.sort(stations);
        return stations;
    }

    @Override
    public int getTravelCharge(Passenger passenger) {
        int travelCharge = TravelCharge.valueOf(passenger.getPassengerType().toString()).getCharge();
        int journeyTypeCode = passenger.getJourneyTypeCode();

        if (journeyTypeCode != 0) {
            int revisedTravelCharge = stationServiceImplHelper.getRevisedTravelCharge(travelCharge);
            stationServiceImplHelper.collectDiscount(passenger, travelCharge - revisedTravelCharge);
            return revisedTravelCharge;
        }
        return travelCharge;

    }

    @Override
    public CollectionSummary getCollectionSummary(Station station) {
        int totalCollection = station.getTotalCollection();
        int discountCollection = station.getDiscountCollection();
        return new CollectionSummary(station.getStationName(), totalCollection, discountCollection);
    }

    @Override
    public PassengerSummary getPassengerSummary(Station station) {
        List<Passenger> passengers = station.getBoardedPassengers();
        Map<PassengerType, Integer> passengerTypeCountMap = stationServiceImplHelper.getCountPassengerTypeWise(passengers);
        List<PassengerTypeCount> passengerTypeCounts = stationServiceImplHelper.getPassengerSummaryHelper(passengerTypeCountMap);
        return new PassengerSummary(passengerTypeCounts);
    }
}
