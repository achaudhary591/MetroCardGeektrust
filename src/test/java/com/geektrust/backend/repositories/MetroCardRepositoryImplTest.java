package com.geektrust.backend.repositories;

import com.geektrust.backend.models.MetroCard;
import com.geektrust.backend.repositories.implementation.MetroCardRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MetroCardRepositoryImplTest {
    private MetroCardRepository metroCardRepository;

    @BeforeEach
    public void setup() {
        Map<String, MetroCard> metroCardMap = new HashMap<>();
        metroCardMap.put("1", new MetroCard("1", "MC1", 100));
        metroCardMap.put("2", new MetroCard("2", "MC2", 200));
        metroCardMap.put("3", new MetroCard("3", "MC3", 300));

        metroCardRepository = new MetroCardRepositoryImpl(metroCardMap);
    }

    @Test
    public void saveMetroCardShouldSaveMetroCard() {

        MetroCard metroCard4 = new MetroCard("MC4", 400);
        MetroCard expectedMetroCard = new MetroCard("4", "MC4", 400);


        MetroCard actualMetroCard = metroCardRepository.save(metroCard4);


        Assertions.assertEquals(expectedMetroCard, actualMetroCard);
    }

    @Test
    public void findByCardNumberShouldReturnMetroCardGivenCardNumber() {

        MetroCard expectedMetroCard = new MetroCard("2", "MC2", 200);


        MetroCard actualMetroCard = metroCardRepository.findByCardNumber("MC2").get();


        Assertions.assertEquals(expectedMetroCard, actualMetroCard);
    }

    @Test
    public void findByCardNumberShouldReturnEmptyIfCardNumberNotFound() {

        Optional<MetroCard> expectedMetroCard = Optional.empty();


        Optional<MetroCard> actualMetroCard = metroCardRepository.findByCardNumber("MC6");


        Assertions.assertEquals(expectedMetroCard, actualMetroCard);
    }
}
