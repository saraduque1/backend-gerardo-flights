package co.edu.udea.sitas.persistence;

import co.edu.udea.sitas.domain.model.Flight;
import co.edu.udea.sitas.domain.model.Scale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScaleRepository extends JpaRepository<Scale, Long> {
    List<Scale> findAllByFlight(Flight flight);
}
