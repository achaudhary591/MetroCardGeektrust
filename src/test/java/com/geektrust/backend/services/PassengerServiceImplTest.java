package com.geektrust.backend.services;

import com.geektrust.backend.exceptions.MetroCardNotFoundException;
import com.geektrust.backend.models.MetroCard;
import com.geektrust.backend.models.Passenger;
import com.geektrust.backend.models.enums.PassengerType;
import com.geektrust.backend.repositories.MetroCardRepository;
import com.geektrust.backend.repositories.PassengerRepository;
import com.geektrust.backend.services.implementation.PassengerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceImplTest {
    private Passenger passenger;
    private MetroCard metroCard;

    @Mock
    private StationService stationServiceMock;

    @Mock
    private MetroCardService metroCardServiceMock;

    @Mock
    private MetroCardRepository metroCardRepositoryMock;

    @Mock
    private PassengerRepository passengerRepositoryMock;

    @InjectMocks
    private PassengerServiceImpl passengerServiceImpl;

    @BeforeEach
    public void setup() {
        metroCard = new MetroCard("1", "MC1", 100);
        passenger = new Passenger(metroCard, PassengerType.ADULT, "AIRPORT");
    }

    @Test
    public void shouldReturnNewPassengerIfNotPresent() {

        Passenger expectedPassenger = new Passenger("1", metroCard, PassengerType.ADULT, "AIRPORT");
        when(metroCardRepositoryMock.findByCardNumber("MC1")).thenReturn(Optional.ofNullable(metroCard));
        when(passengerRepositoryMock.findByMetroCard(metroCard)).thenReturn(Optional.empty());
        when(passengerRepositoryMock.save(passenger)).thenReturn(expectedPassenger);


        Passenger actualPassenger = passengerServiceImpl.create("MC1", PassengerType.ADULT, "AIRPORT");


        Assertions.assertEquals(expectedPassenger, actualPassenger);
        verify(metroCardRepositoryMock, times(1)).findByCardNumber("MC1");
        verify(passengerRepositoryMock, times(1)).findByMetroCard(metroCard);
        verify(passengerRepositoryMock, times(1)).save(passenger);
    }

    @Test
    public void shouldReturnExistingPassengerIfAlreadyPresent() {

        Passenger passenger1 = new Passenger("1", metroCard, PassengerType.ADULT, "AIRPORT");
        Passenger expectedPassenger = new Passenger("1", metroCard, PassengerType.ADULT, "CENTRAL");
        when(metroCardRepositoryMock.findByCardNumber("MC1")).thenReturn(Optional.ofNullable(metroCard));
        when(passengerRepositoryMock.findByMetroCard(metroCard)).thenReturn(Optional.ofNullable(passenger1));


        Passenger actualPassenger = passengerServiceImpl.create("MC1", PassengerType.ADULT, "CENTRAL");


        Assertions.assertEquals(expectedPassenger, actualPassenger);
        verify(metroCardRepositoryMock, times(1)).findByCardNumber("MC1");
        verify(passengerRepositoryMock, times(1)).findByMetroCard(metroCard);
        verify(passengerRepositoryMock, times(0)).save(any(Passenger.class));
    }

    @Test
    public void shouldThrowMetroCardNotFoundException() {

        when(metroCardRepositoryMock.findByCardNumber("MC3")).thenReturn(Optional.empty());


        Assertions.assertThrows(MetroCardNotFoundException.class, () -> passengerServiceImpl.create("MC3", PassengerType.ADULT, "CENTRAL"));
        verify(metroCardRepositoryMock, times(1)).findByCardNumber("MC3");
        verify(passengerRepositoryMock, times(0)).findByMetroCard(any(MetroCard.class));
        verify(passengerRepositoryMock, times(0)).save(any(Passenger.class));
    }

    @Test
    public void travelMakePaymentIfSufficientBalance() {

        Passenger passenger1 = new Passenger("1", metroCard, PassengerType.KID, "AIRPORT");
        when(stationServiceMock.getTravelCharge(passenger1)).thenReturn(50);


        passengerServiceImpl.travel(passenger1);


        verify(stationServiceMock, times(1)).getTravelCharge(passenger1);
        verify(metroCardServiceMock, times(1)).makePayment(metroCard, 50, passenger1);
        verify(metroCardServiceMock, times(0)).recharge(any(MetroCard.class), anyInt(), any(Passenger.class));
        verify(stationServiceMock, times(1)).addPassengerToBoardedList(passenger1);
    }

    @Test
    public void travelRechargeMetroCardAndMakePaymentIfNoSufficientBalance() {

        Passenger passenger1 = new Passenger("1", metroCard, PassengerType.ADULT, "AIRPORT");
        when(stationServiceMock.getTravelCharge(passenger1)).thenReturn(200);


        passengerServiceImpl.travel(passenger1);


        verify(stationServiceMock, times(1)).getTravelCharge(passenger1);
        verify(metroCardServiceMock, times(1)).recharge(metroCard, 200, passenger1);
        verify(metroCardServiceMock, times(1)).makePayment(metroCard, 200, passenger1);
        verify(stationServiceMock, times(1)).addPassengerToBoardedList(passenger1);
    }
}
