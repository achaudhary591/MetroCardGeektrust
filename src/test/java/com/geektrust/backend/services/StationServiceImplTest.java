package com.geektrust.backend.services;

import com.geektrust.backend.dtos.CollectionSummary;
import com.geektrust.backend.dtos.PassengerSummary;
import com.geektrust.backend.dtos.PassengerTypeCount;
import com.geektrust.backend.exceptions.StationNotFoundException;
import com.geektrust.backend.models.MetroCard;
import com.geektrust.backend.models.Passenger;
import com.geektrust.backend.models.Station;
import com.geektrust.backend.models.enums.PassengerType;
import com.geektrust.backend.repositories.StationRepository;
import com.geektrust.backend.services.implementation.StationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StationServiceImplTest {
    
    @Mock
    private StationRepository stationRepositoryMock;

    @InjectMocks
    private StationServiceImpl stationServiceImpl;

    private static List<Passenger> getPassengers() {
        MetroCard metroCard1 = new MetroCard("1", "MC1", 600);
        Passenger passenger1 = new Passenger("1", metroCard1, PassengerType.SENIOR_CITIZEN, "CENTRAL");

        MetroCard metroCard2 = new MetroCard("2", "MC2", 500);
        Passenger passenger2 = new Passenger("2", metroCard2, PassengerType.ADULT, "CENTRAL");

        MetroCard metroCard3 = new MetroCard("3", "MC3", 300);
        Passenger passenger3 = new Passenger("3", metroCard3, PassengerType.KID, "CENTRAL");

        return Arrays.asList(passenger1, passenger2, passenger3);
    }

    @Test
    public void shouldReturnNewStationIfNotPresent() {
        
        Station station = new Station("CENTRAL");
        Station expectedStation = new Station("1", "CENTRAL");
        when(stationRepositoryMock.findByName("CENTRAL")).thenReturn(Optional.empty());
        when(stationRepositoryMock.save(station)).thenReturn(expectedStation);

        
        Station actualStation = stationServiceImpl.create("CENTRAL");

        
        Assertions.assertEquals(expectedStation, actualStation);
        verify(stationRepositoryMock, times(1)).findByName("CENTRAL");
        verify(stationRepositoryMock, times(1)).save(station);
    }

    @Test
    public void shouldReturnExistingStationIfAlreadyPresent() {
        
        Station expectedStation = new Station("1", "CENTRAL");
        when(stationRepositoryMock.findByName("CENTRAL")).thenReturn(Optional.ofNullable(expectedStation));
        
        
        Station actualStation = stationServiceImpl.create("CENTRAL");

        
        Assertions.assertEquals(expectedStation, actualStation);
        verify(stationRepositoryMock, times(1)).findByName("CENTRAL");
        verify(stationRepositoryMock, times(0)).save(any(Station.class));
    }

    @Test
    public void shouldAddPassengerToBoardedList() {
        
        int expectedCount = 1;
        MetroCard metroCard = new MetroCard("1", "MC1", 400);
        Passenger passenger = new Passenger("1", metroCard, PassengerType.ADULT, "AIRPORT");
        Station station = new Station("1", "AIRPORT");
        when(stationRepositoryMock.findByName("AIRPORT")).thenReturn(Optional.ofNullable(station));

        
        stationServiceImpl.addPassengerToBoardedList(passenger);
        int actualCount = station.getBoardedPassengers().size();

        
        Assertions.assertEquals(expectedCount, actualCount);
        verify(stationRepositoryMock, times(1)).findByName("AIRPORT");

    }

    @Test
    public void shouldThrowStationNotFoundExceptionWhenAddingPassengerToBoardedList() {
        
        MetroCard metroCard = new MetroCard("1", "MC1", 400);
        Passenger passenger = new Passenger("1", metroCard, PassengerType.ADULT, "CENTRAL");
        when(stationRepositoryMock.findByName("CENTRAL")).thenReturn(Optional.empty());

        
        Assertions.assertThrows(StationNotFoundException.class, () -> stationServiceImpl.addPassengerToBoardedList(passenger));
        verify(stationRepositoryMock, times(1)).findByName("CENTRAL");
    }

    @Test
    public void shouldAddTravelChargeToTravelChargeCollection() {
        
        int expectedTravelCharge = 200;
        MetroCard metroCard = new MetroCard("1", "MC1", 400);
        Passenger passenger = new Passenger("1", metroCard, PassengerType.ADULT, "CENTRAL");
        Station station = new Station("1", "CENTRAL");
        when(stationRepositoryMock.findByName("CENTRAL")).thenReturn(Optional.ofNullable(station));

        
        stationServiceImpl.collectTravelCharge(passenger, 200);
        int actualTravelCharge = station.getTravelChargeCollection();

        
        Assertions.assertEquals(expectedTravelCharge, actualTravelCharge);
        verify(stationRepositoryMock, times(1)).findByName("CENTRAL");
    }

    @Test
    public void shouldReturnAllStations() {
        
        int expectedServiceFee = 3;
        MetroCard metroCard = new MetroCard("1", "MC1", 50);
        Passenger passenger = new Passenger("1", metroCard, PassengerType.ADULT, "CENTRAL");
        Station station = new Station("1", "CENTRAL");
        when(stationRepositoryMock.findByName("CENTRAL")).thenReturn(Optional.ofNullable(station));

        
        stationServiceImpl.collectServiceFee(passenger, 150);
        int actualServiceFee = station.getServiceFeeCollection();

        
        Assertions.assertEquals(expectedServiceFee, actualServiceFee);
        verify(stationRepositoryMock, times(1)).findByName("CENTRAL");
    }

    @Test
    public void getAllStations_shouldReturnAllStations() {
         
        Station station1 = new Station("1", "CENTRAL");
        Station station2 = new Station("2", "AIRPORT");
        List<Station> expectedStationList = Arrays.asList(station1, station2);
        List<Station> stations = new ArrayList<>(Arrays.asList(station2, station1));
        when(stationRepositoryMock.findAll()).thenReturn(stations);

        
        List<Station> actualStationList = stationServiceImpl.getAllStations();

        
        Assertions.assertEquals(expectedStationList, actualStationList);
        verify(stationRepositoryMock, times(1)).findAll();
    }

    @Test
    public void shouldReturnTravelChargeForPassengerWithSingleJourney() {
        
        int expectedTravelCharge = 200;
        MetroCard metroCard = new MetroCard("1", "MC1", 600);
        Passenger passenger = new Passenger("1", metroCard, PassengerType.ADULT, "CENTRAL");
        passenger.updateJourneyTypeCode();

        
        int actualTravelCharge = stationServiceImpl.getTravelCharge(passenger);

        
        Assertions.assertEquals(expectedTravelCharge, actualTravelCharge);
    }

    @Test
    public void shouldReturnTravelChargeForPassengerWithReturnJourney() {
        
        int expectedTravelCharge = 100;
        int expectedDiscount = 100;
        MetroCard metroCard = new MetroCard("1", "MC1", 600);
        Passenger passenger = new Passenger("1", metroCard, PassengerType.ADULT, "AIRPORT");
        Station station = new Station("1", "AIRPORT");
        passenger.updateJourneyTypeCode();
        passenger.updateJourneyTypeCode();
        when(stationRepositoryMock.findByName("AIRPORT")).thenReturn(Optional.ofNullable(station));

        
        int actualTravelCharge = stationServiceImpl.getTravelCharge(passenger);
        int actualDiscount = station.getDiscountCollection();

        
        Assertions.assertEquals(expectedTravelCharge, actualTravelCharge);
        Assertions.assertEquals(expectedDiscount, actualDiscount);
        verify(stationRepositoryMock, times(1)).findByName("AIRPORT");
    }

    @Test
    public void shouldReturnCollectionSummaryForStation() {
        
        CollectionSummary expectedCollectionSummary = new CollectionSummary("CENTRAL", 403, 50);
        Station station = new Station("1", "CENTRAL");
        station.addTravelCharge(400);
        station.addServiceFee(3);
        station.addDiscount(50);

        
        CollectionSummary actualCollectionSummary = stationServiceImpl.getCollectionSummary(station);

        
        Assertions.assertEquals(expectedCollectionSummary, actualCollectionSummary);
    }

    @Test
    public void shouldReturnPassengerSummaryForStation() {

        List<Passenger> passengers = getPassengers();
        Station station = new Station("1", "CENTRAL");

        for(Passenger passenger : passengers)
            station.addPassenger(passenger);

        PassengerTypeCount passengerTypeCount1 = new PassengerTypeCount(PassengerType.ADULT, 1);
        PassengerTypeCount passengerTypeCount2 = new PassengerTypeCount(PassengerType.KID, 1); 
        PassengerTypeCount passengerTypeCount3 = new PassengerTypeCount(PassengerType.SENIOR_CITIZEN, 1);
        List<PassengerTypeCount> passengerTypeCounts = Arrays.asList(passengerTypeCount1, passengerTypeCount2, passengerTypeCount3);
        PassengerSummary expectedPassengerSummary = new PassengerSummary(passengerTypeCounts);

        
        PassengerSummary actualPassengerSummary = stationServiceImpl.getPassengerSummary(station);

        
        Assertions.assertEquals(expectedPassengerSummary, actualPassengerSummary);
    }
}
