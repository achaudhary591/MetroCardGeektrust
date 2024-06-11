package com.geektrust.backend.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import com.geektrust.backend.models.MetroCard;
import com.geektrust.backend.models.Passenger;
import com.geektrust.backend.models.enums.PassengerType;
import com.geektrust.backend.repositories.MetroCardRepository;
import com.geektrust.backend.services.implementation.MetroCardServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MetroCardServiceImplTest {
    private Passenger passenger;
    private MetroCard metroCard;

    @Mock
    private StationService stationServiceMock;

    @Mock
    private MetroCardRepository metroCardRepositoryMock;

    @InjectMocks
    private MetroCardServiceImpl metroCardServiceImpl;

    @BeforeEach
    public void setup() {
        metroCard = new MetroCard("1", "MC1", 100);
        passenger = new Passenger("1", metroCard, PassengerType.KID, "CENTRAL");
    }

    @Test
    public void shouldReturnNewMetroCardIfNotPresent() {
        
        MetroCard expectedMetroCard = new MetroCard("1", "MC1", 100);
        when(metroCardRepositoryMock.findByCardNumber("MC1")).thenReturn(Optional.empty());
        when(metroCardRepositoryMock.save(any(MetroCard.class))).thenReturn(expectedMetroCard);

        
        MetroCard actualMetroCard = metroCardServiceImpl.create("MC1", 100);

        
        Assertions.assertEquals(expectedMetroCard, actualMetroCard);
        verify(metroCardRepositoryMock, times(1)).findByCardNumber("MC1");
        verify(metroCardRepositoryMock, times(1)).save(any(MetroCard.class));
    }

    @Test
    public void shouldReturnExistingMetroCardIfAlreadyPresent() {
         
        MetroCard expectedMetroCard = new MetroCard("1", "MC1", 100);
        when(metroCardRepositoryMock.findByCardNumber("MC1")).thenReturn(Optional.ofNullable(expectedMetroCard));

        
        MetroCard actualMetroCard = metroCardServiceImpl.create("MC1", 100);

        
        Assertions.assertEquals(expectedMetroCard, actualMetroCard);
        verify(metroCardRepositoryMock, times(1)).findByCardNumber("MC1");
        verify(metroCardRepositoryMock, times(0)).save(any(MetroCard.class));
    }

    @Test
    public void rechargeShouldRechargeMetroCardGivenTravelCharge() {
        
        int expectedBalance = 200;

        
        metroCardServiceImpl.recharge(metroCard, 200, passenger);
        int actualBalance = metroCard.getBalance();

        
        Assertions.assertEquals(expectedBalance, actualBalance);
        verify(stationServiceMock, times(1)).collectServiceFee(passenger, 100);
    }

    @Test
    public void makePaymentShouldMakePaymentGivenTravelCharge() {
        
        int expectedBalance = 0;

        
        metroCardServiceImpl.makePayment(metroCard, 100, passenger);
        int actualBalance = metroCard.getBalance();

        
        Assertions.assertEquals(expectedBalance, actualBalance);
        verify(stationServiceMock, times(1)).collectTravelCharge(passenger, 100);
    }
}
