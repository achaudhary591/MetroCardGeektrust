package com.geektrust.backend.repositories.implementation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import com.geektrust.backend.models.MetroCard;
import com.geektrust.backend.repositories.MetroCardRepository;

public class MetroCardRepositoryImpl implements MetroCardRepository {
    private final Map<String, MetroCard> metroCardMap;
    private int autoIncrement = 0;

    public MetroCardRepositoryImpl(Map<String, MetroCard> metroCardMap) {
        this.metroCardMap = metroCardMap;
        this.autoIncrement = calculateInitialAutoIncrement(metroCardMap);
    }

    public MetroCardRepositoryImpl() {
        this(new HashMap<>());
    }

    @Override
    public MetroCard save(MetroCard metroCard) {
        if(metroCard.getId() == null) {
            String newId = generateNewId();
            MetroCard newMetroCard = new MetroCard(newId, metroCard.getCardNumber(), metroCard.getBalance());
            metroCardMap.put(newId, newMetroCard);
            return newMetroCard;
        }
        metroCardMap.put(metroCard.getId(), metroCard);
        return metroCard;
    }

    @Override
    public Optional<MetroCard> findByCardNumber(String cardNumber) {
        return metroCardMap.values().stream()
                           .filter(metroCard -> metroCard.getCardNumber().equals(cardNumber))
                           .findFirst();
    }

    private String generateNewId() {
        autoIncrement++;
        return Integer.toString(autoIncrement);
    }

    private int calculateInitialAutoIncrement(Map<String, MetroCard> map) {
        return map.values().stream()
                  .mapToInt(metroCard -> Integer.parseInt(metroCard.getId()))
                  .max()
                  .orElse(0);
    }
}
