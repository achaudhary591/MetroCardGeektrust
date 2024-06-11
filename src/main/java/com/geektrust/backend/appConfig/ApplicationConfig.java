package com.geektrust.backend.appConfig;

import com.geektrust.backend.commands.BalanceCommand;
import com.geektrust.backend.commands.CheckInCommand;
import com.geektrust.backend.commands.CommandInvoker;
import com.geektrust.backend.commands.PrintSummaryCommand;
import com.geektrust.backend.repositories.MetroCardRepository;
import com.geektrust.backend.repositories.implementation.MetroCardRepositoryImpl;
import com.geektrust.backend.repositories.PassengerRepository;
import com.geektrust.backend.repositories.implementation.PassengerRepositoryImpl;
import com.geektrust.backend.repositories.StationRepository;
import com.geektrust.backend.repositories.implementation.StationRepositoryImpl;
import com.geektrust.backend.services.MetroCardService;
import com.geektrust.backend.services.implementation.MetroCardServiceImpl;
import com.geektrust.backend.services.PassengerService;
import com.geektrust.backend.services.implementation.PassengerServiceImpl;
import com.geektrust.backend.services.StationService;
import com.geektrust.backend.services.implementation.StationServiceImpl;

public class ApplicationConfig {
    private final MetroCardRepository metroCardRepository = new MetroCardRepositoryImpl();
    private final PassengerRepository passengerRepository = new PassengerRepositoryImpl();
    private final StationRepository stationRepository = new StationRepositoryImpl();

    private final StationService stationService = new StationServiceImpl(stationRepository);
    private final MetroCardService metroCardService = new MetroCardServiceImpl(stationService, metroCardRepository);
    private final PassengerService passengerService = new PassengerServiceImpl(stationService, metroCardService, metroCardRepository, passengerRepository);

    private final BalanceCommand balanceCommand = new BalanceCommand(metroCardService);
    private final CheckInCommand checkInCommand = new CheckInCommand(passengerService, stationService);
    private final PrintSummaryCommand printSummaryCommand = new PrintSummaryCommand(stationService);

    private final CommandInvoker commandInvoker = new CommandInvoker();

    public CommandInvoker getCommandInvoker() {
        final String COMMAND_BALANCE = "BALANCE";
        final String COMMAND_CHECK_IN = "CHECK_IN";
        final String COMMAND_PRINT_SUMMARY = "PRINT_SUMMARY";

        commandInvoker.register(COMMAND_BALANCE, balanceCommand);
        commandInvoker.register(COMMAND_CHECK_IN, checkInCommand);
        commandInvoker.register(COMMAND_PRINT_SUMMARY, printSummaryCommand);
        return commandInvoker;
    }
}
