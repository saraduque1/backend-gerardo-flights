package co.edu.udea.sitas.persistence;

import co.edu.udea.sitas.domain.model.AirplaneModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirplaneModelRepository extends JpaRepository<AirplaneModel, String> {
}
