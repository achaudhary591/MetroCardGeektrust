package com.geektrust.backend.commands;

import com.geektrust.backend.models.MetroCard;
import com.geektrust.backend.services.MetroCardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BalanceCommandTest {

    @Mock
    private MetroCardService metroCardServiceMock;

    @InjectMocks
    private BalanceCommand balanceCommand;

    @Test
    public void executeShouldCreateMetroCardGivenCardNumberAndBalance() {

        MetroCard metroCard = new MetroCard("1", "MC1", 600);
        List<String> tokens = new ArrayList<>(Arrays.asList("BALANCE", "MC1", "600"));
        when(metroCardServiceMock.create("MC1", 600)).thenReturn(metroCard);


        balanceCommand.execute(tokens);


        verify(metroCardServiceMock, times(1)).create("MC1", 600);
    }
}
