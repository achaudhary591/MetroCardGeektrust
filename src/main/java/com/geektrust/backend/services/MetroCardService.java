package com.geektrust.backend.services;

import com.geektrust.backend.models.MetroCard;
import com.geektrust.backend.models.Passenger;

public interface MetroCardService {
    MetroCard create(String cardNumber, int balance);
    void recharge(MetroCard metroCard, int travelCharge, Passenger passenger);
    void makePayment(MetroCard metroCard, int travelCharge, Passenger passenger);
}
