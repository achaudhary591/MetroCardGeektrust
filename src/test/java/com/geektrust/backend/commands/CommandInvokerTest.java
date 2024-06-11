package com.geektrust.backend.commands;

import com.geektrust.backend.exceptions.NoSuchCommandException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CommandInvokerTest {

    private CommandInvoker commandInvoker;

    @Mock
    private BalanceCommand balanceCommand;

    @Mock
    private CheckInCommand checkInCommand;

    @Mock
    private PrintSummaryCommand printSummaryCommand;

    @BeforeEach
    public void setup() {
        commandInvoker = new CommandInvoker();
        commandInvoker.register("BALANCE", balanceCommand);
        commandInvoker.register("CHECK_IN", checkInCommand);
        commandInvoker.register("PRINT_SUMMARY", printSummaryCommand);
    }

    @Test
    public void executeCommandShouldExecuteCommandGivenNameAndTokens() {

        commandInvoker.executeCommand("BALANCE", new ArrayList<String>());
        commandInvoker.executeCommand("CHECK_IN", new ArrayList<String>());
        commandInvoker.executeCommand("PRINT_SUMMARY", new ArrayList<String>());


        verify(balanceCommand, times(1)).execute(anyList());
        verify(checkInCommand, times(1)).execute(anyList());
        verify(printSummaryCommand, times(1)).execute(anyList());
    }

    @Test
    public void executeCommandShouldThrowExceptionGivenIncorrectNameAndTokens() {

        Assertions.assertThrows(NoSuchCommandException.class, () -> commandInvoker.executeCommand("RANDOM_COMMAND", new ArrayList<String>()));
    }
}
