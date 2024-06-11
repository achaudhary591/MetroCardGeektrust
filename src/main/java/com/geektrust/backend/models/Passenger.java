package com.geektrust.backend.models;

import com.geektrust.backend.models.enums.PassengerType;
import com.geektrust.backend.exceptions.InvalidStationNameException;

public class Passenger extends BaseModel{
    private final MetroCard metroCard;
    private final PassengerType passengerType;
    private String boardingStation;
    private int journeyTypeCode; // 0 indicates SINGLE, 1 indicates RETURN, -1 indicates NOT_STARTED

    public Passenger(Passenger passenger) {
        this(passenger.id, passenger.metroCard, passenger.passengerType, passenger.boardingStation);
    }

    public Passenger(String id, MetroCard metroCard, PassengerType passengerType,
            String boardingStation) {
        this(metroCard, passengerType, boardingStation);
        this.id = id;
    }

    public Passenger(MetroCard metroCard, PassengerType passengerType, String boardingStation) {
        this.metroCard = metroCard;
        this.passengerType = passengerType;
        this.boardingStation = boardingStation;
        this.journeyTypeCode = -1;
    }

    public MetroCard getMetroCard() {
        return this.metroCard;
    }

    public PassengerType getPassengerType() {
        return this.passengerType;
    }

    public String getBoardingStation() {
        return this.boardingStation;
    }

    public int getJourneyTypeCode() {
        return this.journeyTypeCode;
    }

    public void setBoardingStation(String boardingStation) {
        validateStationName(boardingStation);
        this.boardingStation = boardingStation;
    }

    public void updateJourneyTypeCode() {
        final int INCREMENT_VALUE = 1;
        final int TOTAL_STATION_COUNT = 2;
        this.journeyTypeCode = (this.journeyTypeCode + INCREMENT_VALUE) % TOTAL_STATION_COUNT;
    }

    private void validateStationName(String stationName) {
        if(stationName == null)
            throw new InvalidStationNameException();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;

        Passenger other = (Passenger) obj;
        return (this.metroCard.equals(other.metroCard)) && (this.passengerType.equals(other.passengerType)) && (this.boardingStation.equals(other.boardingStation));
    }
}
