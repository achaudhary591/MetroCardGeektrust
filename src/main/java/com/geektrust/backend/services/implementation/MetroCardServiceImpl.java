package com.geektrust.backend.services.implementation;

import com.geektrust.backend.models.MetroCard;
import com.geektrust.backend.models.Passenger;
import com.geektrust.backend.repositories.MetroCardRepository;
import com.geektrust.backend.services.MetroCardService;
import com.geektrust.backend.services.StationService;

import java.util.Optional;

public class MetroCardServiceImpl implements MetroCardService {
    private final StationService stationService;
    private final MetroCardRepository metroCardRepository;

    public MetroCardServiceImpl(StationService stationService, MetroCardRepository metroCardRepository) {
        this.stationService = stationService;
        this.metroCardRepository = metroCardRepository;
    }

    @Override
    public MetroCard create(String cardNumber, int balance) {
        Optional<MetroCard> maybeMetroCard = metroCardRepository.findByCardNumber(cardNumber);

        if (!maybeMetroCard.isPresent()) {
            MetroCard metroCard = new MetroCard(cardNumber, balance);
            return metroCardRepository.save(metroCard);
        }
        return maybeMetroCard.get();

    }

    @Override
    public void recharge(MetroCard metroCard, int travelCharge, Passenger passenger) {
        int balance = metroCard.getBalance();
        int rechargeAmount = travelCharge - balance;
        metroCard.addAmount(rechargeAmount);
        stationService.collectServiceFee(passenger, rechargeAmount);
    }

    @Override
    public void makePayment(MetroCard metroCard, int travelCharge, Passenger passenger) {
        metroCard.deductAmount(travelCharge);
        stationService.collectTravelCharge(passenger, travelCharge);
    }
}
