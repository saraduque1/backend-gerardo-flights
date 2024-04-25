package co.edu.udea.sitas.services;

import co.edu.udea.sitas.domain.model.Flight;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FlightService {
    List<Flight> findAll(Map<String, String> params);
    Flight save(Flight flight);
    Optional<Flight> findById(Long id);
    Optional<Flight>   update(Long id, Flight body);
    Optional<Flight>   delete(Long id);
}
