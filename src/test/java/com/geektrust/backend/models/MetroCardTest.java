package com.geektrust.backend.models;

import com.geektrust.backend.exceptions.InvalidAmountException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MetroCardTest {

    @Test
    public void addAmountShouldAddAmountToMetroCard() {

        int expectedBalance = 200;
        MetroCard metroCard = new MetroCard("MC1", 100);


        metroCard.addAmount(100);
        int actualBalance = metroCard.getBalance();


        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    @Test
    public void deductAmountShouldDeductAmountFromMetroCard() {

        int expectedBalance = 100;
        MetroCard metroCard = new MetroCard("MC1", 200);


        metroCard.deductAmount(100);
        int actualBalance = metroCard.getBalance();


        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    @Test
    public void addAmountShouldThrowInvalidAmountExceptionGivenInvalidAmount() {

        MetroCard metroCard = new MetroCard("MC1", 200);


        Assertions.assertThrows(InvalidAmountException.class, () -> metroCard.addAmount(-100));
    }

    @Test
    public void deductAmountShouldThrowInvalidAmountExceptionGivenInvalidAmount() {

        MetroCard metroCard = new MetroCard("MC1", 200);


        Assertions.assertThrows(InvalidAmountException.class, () -> metroCard.deductAmount(300));
    }

    @Test
    public void hasSufficientBalanceShouldReturnTrueGivenAmount() {

        MetroCard metroCard = new MetroCard("MC1", 200);


        Assertions.assertTrue(metroCard.hasSufficientBalance(100));
    }

    @Test
    public void hasSufficientBalanceShouldReturnFalseGivenAmount() {

        MetroCard metroCard = new MetroCard("MC1", 200);


        Assertions.assertFalse(metroCard.hasSufficientBalance(400));
    }
}
