package com.geektrust.backend.commands;

import java.util.List;
import com.geektrust.backend.services.MetroCardService;

public class BalanceCommand implements ICommand {
    private final MetroCardService metroCardService;

    public BalanceCommand(MetroCardService metroCardService) {
        this.metroCardService = metroCardService;
    }

    @Override
    public void execute(List<String> tokens) {
        final int CARD_NUMBER_INDEX = 1;
        final int BALANCE_INDEX = 2;

        String cardNumber = tokens.get(CARD_NUMBER_INDEX);
        int balance = Integer.parseInt(tokens.get(BALANCE_INDEX));
        metroCardService.create(cardNumber, balance);
    } 
}
