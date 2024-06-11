package com.geektrust.backend.repositories.implementation;

import com.geektrust.backend.models.Station;
import com.geektrust.backend.repositories.StationRepository;

import java.util.*;

public class StationRepositoryImpl implements StationRepository {
    private final Map<String, Station> stationMap;
    private int autoIncrement = 0;

    public StationRepositoryImpl(Map<String, Station> stationMap) {
        this.stationMap = stationMap;
        this.autoIncrement = calculateInitialAutoIncrement(stationMap);
    }

    public StationRepositoryImpl() {
        this(new HashMap<>());
    }

    @Override
    public Station save(Station station) {
        if (station.getId() == null) {
            String newId = generateNewId();
            Station newStation = new Station(newId, station.getStationName());
            stationMap.put(newId, newStation);
            return newStation;
        }
        stationMap.put(station.getId(), station);
        return station;
    }

    @Override
    public Optional<Station> findByName(String name) {
        return stationMap.values().stream()
                         .filter(station -> station.getStationName().equals(name))
                         .findFirst();
    }

    @Override
    public List<Station> findAll() {
        return new ArrayList<>(stationMap.values());
    }

    private String generateNewId() {
        autoIncrement++;
        return Integer.toString(autoIncrement);
    }

    private int calculateInitialAutoIncrement(Map<String, Station> map) {
        return map.values().stream()
                  .mapToInt(station -> Integer.parseInt(station.getId()))
                  .max()
                  .orElse(0);
    }
}
