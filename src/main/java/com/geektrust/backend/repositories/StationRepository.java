package com.geektrust.backend.repositories;

import java.util.List;
import java.util.Optional;
import com.geektrust.backend.models.Station;

public interface StationRepository {
    Station save(Station entity);
    Optional<Station> findByName(String name);
    List<Station> findAll();
}
