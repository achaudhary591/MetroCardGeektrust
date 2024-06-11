package com.geektrust.backend.services;

import com.geektrust.backend.models.Passenger;
import com.geektrust.backend.models.enums.PassengerType;

public interface PassengerService {
    Passenger create(String cardNumber, PassengerType passengerType, String boardingStation);
    void travel(Passenger passenger);
}
