package com.geektrust.backend.models;

import java.util.ArrayList;
import java.util.List;
import com.geektrust.backend.exceptions.InvalidAmountException;
import com.geektrust.backend.exceptions.InvalidPassengerException;

public class Station extends BaseModel implements Comparable<Station> {

    private final String stationName;
    private final List<Passenger> boardedPassengers;
    private int travelChargeCollection;
    private int serviceFeeCollection;
    private int discountCollection;
    private static final int MIN_AMOUNT = 0;

    public Station(String id, String stationName) {
        this(stationName);
        this.id = id;
    }

    public Station(String stationName) {
        this.stationName = stationName;
        this.boardedPassengers = new ArrayList<>();
        this.travelChargeCollection = 0;
        this.serviceFeeCollection = 0;
        this.discountCollection = 0;
    }

    public void addTravelCharge(int travelCharge) {
        validateAmount(travelCharge);
        this.travelChargeCollection += travelCharge;
    }

    public void addServiceFee(int serviceFee) {
        validateAmount(serviceFee);
        this.serviceFeeCollection += serviceFee;
    }

    public void addDiscount(int discount) {
        validateAmount(discount);
        this.discountCollection += discount;
    }

    public void addPassenger(Passenger passenger) {
        validatePassenger(passenger);
        this.boardedPassengers.add(passenger);
    }

    public String getStationName() {
        return this.stationName;
    }

    public List<Passenger> getBoardedPassengers() {
        return this.boardedPassengers;
    }

    public int getTravelChargeCollection() {
        return this.travelChargeCollection;
    }

    public int getServiceFeeCollection() {
        return this.serviceFeeCollection;
    }

    public int getDiscountCollection() {
        return this.discountCollection;
    }

    public int getTotalCollection() {
        return this.travelChargeCollection + this.serviceFeeCollection;
    }
    
    private void validateAmount(int amount) {
        if (amount <= MIN_AMOUNT)
            throw new InvalidAmountException();
    }

    private void validatePassenger(Passenger passenger) {
        if (passenger == null)
            throw new InvalidPassengerException();
    }

    @Override
    public int compareTo(Station other) {
        return other.stationName.compareTo(this.stationName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Station other = (Station) obj;
        return this.stationName.equals(other.stationName);
    }
}
