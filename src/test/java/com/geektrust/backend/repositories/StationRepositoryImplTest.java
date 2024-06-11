package com.geektrust.backend.repositories;

import com.geektrust.backend.models.Station;
import com.geektrust.backend.repositories.implementation.StationRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StationRepositoryImplTest {
    private StationRepository stationRepository;

    @BeforeEach
    public void setup() {
        Map<String, Station> stationMap = new HashMap<>();
        stationMap.put("1", new Station("1", "CENTRAL"));

        stationRepository = new StationRepositoryImpl(stationMap);
    }

    @Test
    public void saveStationShouldSaveStation() {

        Station station2 = new Station("AIRPORT");
        Station expectedStation = new Station("2", "AIRPORT");


        Station actualStation = stationRepository.save(station2);


        Assertions.assertEquals(expectedStation, actualStation);
    }

    @Test
    public void findByNameShouldReturnStationGivenName() {

        Station expectedStation = new Station("1", "CENTRAL");


        Station actualStation = stationRepository.findByName("CENTRAL").get();


        Assertions.assertEquals(expectedStation, actualStation);
    }

    @Test
    public void findByNameShouldReturnEmptyIfNameNotFound() {

        Optional<Station> expectedStation = Optional.empty();


        Optional<Station> actualStation = stationRepository.findByName("MG ROAD");


        Assertions.assertEquals(expectedStation, actualStation);
    }

    @Test
    public void findAllStationShouldReturnAllStations() {

        int expectedCount = 2;
        Station station2 = new Station("AIRPORT");
        stationRepository.save(station2);


        int actualCount = stationRepository.findAll().size();


        Assertions.assertEquals(expectedCount, actualCount);
    }
}
