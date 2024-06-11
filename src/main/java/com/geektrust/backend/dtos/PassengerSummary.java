package com.geektrust.backend.dtos;

import java.util.List;

public class PassengerSummary {
    private final List<PassengerTypeCount> passengerTypeCounts;

    public PassengerSummary(List<PassengerTypeCount> passengerTypeCounts) {
        this.passengerTypeCounts = passengerTypeCounts;
    }

    public List<PassengerTypeCount> getPassengerTypeCounts() {
        return this.passengerTypeCounts;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((passengerTypeCounts == null) ? 0 : passengerTypeCounts.hashCode());
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

        PassengerSummary other = (PassengerSummary) obj;
        return this.passengerTypeCounts.equals(other.passengerTypeCounts);
    }
}
