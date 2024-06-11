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
        this.autoIncrement = metroCardMap.size();
    }

    public MetroCardRepositoryImpl() {
        this.metroCardMap = new HashMap<>();
    }

    @Override
    public MetroCard save(MetroCard entity) {
        if(entity.getId() == null) {
            autoIncrement++;
            MetroCard metroCard = new MetroCard(Integer.toString(autoIncrement), entity.getCardNumber(), entity.getBalance());
            metroCardMap.put(metroCard.getId(), metroCard);
            return metroCard;
        }
        metroCardMap.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<MetroCard> findByCardNumber(String cardNumber) {
        Optional<MetroCard> maybeMetroCard = metroCardMap.values().stream().filter(metroCard -> metroCard.getCardNumber().equals(cardNumber)).findFirst();
        return maybeMetroCard;
    }
}
