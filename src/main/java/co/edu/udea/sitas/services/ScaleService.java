package co.edu.udea.sitas.services;

import co.edu.udea.sitas.domain.model.Flight;
import co.edu.udea.sitas.domain.model.Scale;

import java.util.List;
import java.util.Optional;

public interface ScaleService {
    List<Scale> findAll();
    Scale save(Scale scale);
    Optional<Scale> findById(Long id);
    Optional<Scale> update(Long id, Scale scale);
    Optional<Scale> delete(Long id);
    List<Scale> findAllByFlight(Flight flight);
}
