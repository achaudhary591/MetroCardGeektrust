package com.geektrust.backend.repositories.implementation;

import com.geektrust.backend.models.Station;
import com.geektrust.backend.repositories.StationRepository;

import java.util.*;

public class StationRepositoryImpl implements StationRepository {
    private final Map<String, Station> stationMap;
    private int autoIncrement = 0;

    public StationRepositoryImpl(Map<String, Station> stationMap) {
        this.stationMap = stationMap;
        this.autoIncrement = stationMap.size();
    }

    public StationRepositoryImpl() {
        this.stationMap = new HashMap<>();
    }

    @Override
    public Station save(Station station) {
        if (station.getId() == null) {
            autoIncrement++;
            Station newStation = new Station(Integer.toString(autoIncrement), station.getStationName());
            stationMap.put(newStation.getId(), newStation);
            return newStation;
        }
        stationMap.put(station.getId(), station);
        return station;
    }

    @Override
    public Optional<Station> findByName(String name) {
        return stationMap.values().stream().filter(station -> station.getStationName().equals(name)).findFirst();
    }

    @Override
    public List<Station> findAll() {
        return new ArrayList<>(stationMap.values());
    }
}
