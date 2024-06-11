package com.geektrust.backend.repositories;

import java.util.Optional;
import com.geektrust.backend.models.MetroCard;

public interface MetroCardRepository {
    MetroCard save(MetroCard entity);
    Optional<MetroCard> findByCardNumber(String cardNumber);
}
