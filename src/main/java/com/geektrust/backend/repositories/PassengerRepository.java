package com.geektrust.backend.repositories;

import java.util.Optional;
import com.geektrust.backend.models.MetroCard;
import com.geektrust.backend.models.Passenger;

public interface PassengerRepository {
    Passenger save(Passenger entity);
    Optional<Passenger> findByMetroCard(MetroCard metroCard);
}
