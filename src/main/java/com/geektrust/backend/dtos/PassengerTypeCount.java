package com.geektrust.backend.dtos;

import com.geektrust.backend.models.enums.PassengerType;

public class PassengerTypeCount implements Comparable<PassengerTypeCount> {
    private final PassengerType passengerType;
    private final int count;

    public PassengerTypeCount(PassengerType passengerType, int count) {
        this.passengerType = passengerType;
        this.count = count;
    }

    public PassengerType getPassengerType() {
        return this.passengerType;
    }

    public int getCount() {
        return this.count;
    }
    
    @Override
    public int compareTo(PassengerTypeCount other) {
        if(this.count < other.count)
            return 1;
        else if(this.count == other.count) {
            return this.passengerType.compareTo(other.passengerType);
        }
        else
            return -1;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + count;
        result = prime * result + ((passengerType == null) ? 0 : passengerType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;

        PassengerTypeCount other = (PassengerTypeCount) obj;
        return (this.passengerType.equals(other.passengerType)) && (this.count == other.count);
    }
}
