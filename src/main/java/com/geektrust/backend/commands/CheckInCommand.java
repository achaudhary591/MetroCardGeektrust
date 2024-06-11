package com.geektrust.backend.commands;

import java.util.List;
import com.geektrust.backend.models.Passenger;
import com.geektrust.backend.models.enums.PassengerType;
import com.geektrust.backend.exceptions.InvalidAmountException;
import com.geektrust.backend.exceptions.InvalidPassengerException;
import com.geektrust.backend.exceptions.InvalidStationNameException;
import com.geektrust.backend.exceptions.MetroCardNotFoundException;
import com.geektrust.backend.exceptions.StationNotFoundException;
import com.geektrust.backend.services.PassengerService;
import com.geektrust.backend.services.StationService;

public class CheckInCommand implements ICommand {
    private final PassengerService passengerService;
    private final StationService stationService;

    public CheckInCommand(PassengerService passengerService, StationService stationService) {
        this.passengerService = passengerService;
        this.stationService = stationService;
    }

    @Override
    public void execute(List<String> tokens) {
        final int CARD_NUMBER_INDEX = 1;
        final int PASSENGER_TYPE_INDEX = 2;
        final int STATION_NAME_INDEX = 3;

        String cardNumber = tokens.get(CARD_NUMBER_INDEX);
        PassengerType passengerType = PassengerType.valueOf(tokens.get(PASSENGER_TYPE_INDEX)) ;
        String stationName = tokens.get(STATION_NAME_INDEX);

        try {
            Passenger passenger = passengerService.create(cardNumber, passengerType, stationName);
            stationService.create(stationName);
            passengerService.travel(passenger);
        } 
        catch (MetroCardNotFoundException | InvalidAmountException | StationNotFoundException | InvalidStationNameException | InvalidPassengerException e) {
            System.out.println(e.getMessage());
        } 
    } 
}
