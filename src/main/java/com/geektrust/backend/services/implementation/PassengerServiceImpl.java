package com.geektrust.backend.services.implementation;

import com.geektrust.backend.models.MetroCard;
import com.geektrust.backend.models.Passenger;
import com.geektrust.backend.models.enums.PassengerType;
import com.geektrust.backend.exceptions.MetroCardNotFoundException;
import com.geektrust.backend.repositories.MetroCardRepository;
import com.geektrust.backend.repositories.PassengerRepository;
import com.geektrust.backend.services.MetroCardService;
import com.geektrust.backend.services.PassengerService;
import com.geektrust.backend.services.StationService;

public class PassengerServiceImpl implements PassengerService {
    private final StationService stationService;
    private final MetroCardService metroCardService;
    private final MetroCardRepository metroCardRepository;
    private final PassengerRepository passengerRepository;

    public PassengerServiceImpl(StationService stationService, MetroCardService metroCardService,
                                MetroCardRepository metroCardRepository, PassengerRepository passengerRepository) {
        this.stationService = stationService;
        this.metroCardService = metroCardService;
        this.metroCardRepository = metroCardRepository;
        this.passengerRepository = passengerRepository;
    }

    @Override
    public Passenger create(String cardNumber, PassengerType passengerType, String boardingStation) {
        MetroCard metroCard = getMetroCardByNumber(cardNumber);
        return findOrCreatePassenger(metroCard, passengerType, boardingStation);
    }

    @Override
    public void travel(Passenger passenger) {
        int travelCharge = calculateTravelCharge(passenger);
        ensureSufficientBalance(passenger.getMetroCard(), travelCharge, passenger);
        performTravelTransaction(passenger, travelCharge);
    }

    private MetroCard getMetroCardByNumber(String cardNumber) {
        return metroCardRepository.findByCardNumber(cardNumber)
                                  .orElseThrow(MetroCardNotFoundException::new);
    }

    private Passenger findOrCreatePassenger(MetroCard metroCard, PassengerType passengerType, String boardingStation) {
        return passengerRepository.findByMetroCard(metroCard)
                                  .map(passenger -> updateBoardingStation(passenger, boardingStation))
                                  .orElseGet(() -> createNewPassenger(metroCard, passengerType, boardingStation));
    }

    private Passenger updateBoardingStation(Passenger passenger, String boardingStation) {
        passenger.setBoardingStation(boardingStation);
        return passenger;
    }

    private Passenger createNewPassenger(MetroCard metroCard, PassengerType passengerType, String boardingStation) {
        Passenger passenger = new Passenger(metroCard, passengerType, boardingStation);
        return passengerRepository.save(passenger);
    }

    private int calculateTravelCharge(Passenger passenger) {
        passenger.updateJourneyTypeCode();
        return stationService.getTravelCharge(passenger);
    }

    private void ensureSufficientBalance(MetroCard metroCard, int travelCharge, Passenger passenger) {
        if (!metroCard.hasSufficientBalance(travelCharge)) {
            metroCardService.recharge(metroCard, travelCharge, passenger);
        }
    }

    private void performTravelTransaction(Passenger passenger, int travelCharge) {
        metroCardService.makePayment(passenger.getMetroCard(), travelCharge, passenger);
        stationService.addPassengerToBoardedList(passenger);
    }
}
