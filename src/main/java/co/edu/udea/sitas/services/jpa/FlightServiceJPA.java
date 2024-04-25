package co.edu.udea.sitas.services.jpa;

import co.edu.udea.sitas.domain.model.Flight;
import co.edu.udea.sitas.persistence.specifications.FlightSpecification;
import co.edu.udea.sitas.persistence.FlightRepository;
import co.edu.udea.sitas.services.FlightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

@Slf4j
@Service
public class FlightServiceJPA implements FlightService {

    private final FlightRepository flightRepository;

    @Autowired
    public FlightServiceJPA(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }


    @Override
    public Flight save(Flight flight) {
        return flightRepository.save(flight);
    }

    /**
     * Finds flights based on the provided search parameters.
     *
     * @param parameters A map containing search parameters and their corresponding values.
     *                   Supported parameters include:
     *                   - "start-date": Find flights departing after this date.
     *                   - "end-date": Find flights departing before this date.
     *                   - "flight-number": Find flights with the specified flight number.
     *                   - "flight-id": Find flights with the specified flight ID.
     *                   - "min-price": Find flights with a base price greater than the specified value.
     *                   - "max-price": Find flights with a base price less than the specified value.
     *                   - "origin": Find flights with the specified origin city.
     *                   - "destination": Find flights with the specified destination city.
     *                   - "origin-airport": Find flights with the specified origin airport code.
     *                   - "destination-airport": Find flights with the specified destination airport code.
     * @return A list of flights that match the search criteria.
     */
    @Override
    public List<Flight> findAll(Map<String, String> parameters) {
        try {
        // Map which have as key the parameter and a function to get the specification
        Map<String, BiFunction<Specification<Flight>, String, Specification<Flight>> > specBuilders = Map.of(
                "start-date", (spec, value) -> spec.and(FlightSpecification.flightsDepartureAfter(LocalDateTime.parse(value))),
                "end-date", (spec, value) -> spec.and(FlightSpecification.flightsDepartureBefore(LocalDateTime.parse(value))),
                "flight-number", (spec, value) -> spec.and(FlightSpecification.withFlightNumber(value)),
                "flight-id", (spec, value) -> spec.and(FlightSpecification.withFlightId(Long.parseLong(value))),
                "min-price", (spec, value) -> spec.and(FlightSpecification.withBasePriceGreaterThan(Float.parseFloat(value))),
                "max-price", (spec, value) -> spec.and(FlightSpecification.withBasePriceLessThan(Float.parseFloat(value))),
                "origin", (spec, value) -> spec.and(FlightSpecification.withOriginCity(value)),
                "destination", (spec, value) -> spec.and(FlightSpecification.withDestinationCity(value)),
                "origin-airport", (spec, value) -> spec.and(FlightSpecification.withOriginAirport(value)),
                "destination-airport", (spec, value) -> spec.and(FlightSpecification.withDestinationAirport(value))
        );

        /*
         * Build the specification based on  the criteria of above:
         * 1. filters which functions will be used in favour of the given parameters
         * 2. retrieves the specs of the functions applying a null "where" as parameter
         * 3. reduce the list using an AND
         * 4. add specification to return all flights in case that  there aren't parameters
         */
        Specification<Flight> spec = parameters.entrySet().stream()
                .filter(entry -> specBuilders.containsKey(entry.getKey()))
                .map(entry -> specBuilders.get(entry.getKey()).apply(Specification.where(null), entry.getValue()))
                .reduce(Specification::and)
                .orElse(Specification.where(null));

        return flightRepository.findAll(spec);
        } catch(Exception e) {
            log.error("There's an error {}", e.getMessage());
        }
        return List.of();
    }

    @Override
    public Optional<Flight> findById(Long id) {
        return flightRepository.findById(id);
    }

    @Override
    public Optional<Flight> update(Long id, Flight flight) {
        Optional<Flight> optionalFlight = findById(id);
        if(optionalFlight.isPresent()){
            Flight flightToUpdate = optionalFlight.get();
            if(flight.getFlightNumber() != null) flightToUpdate.setFlightNumber(flight.getFlightNumber());
            if(flight.getBasePrice() != null) flightToUpdate.setBasePrice(flight.getBasePrice());
            if(flight.getTaxPercent() != null) flightToUpdate.setTaxPercent(flight.getTaxPercent());
            if(flight.getSurcharge() != null) flightToUpdate.setSurcharge(flight.getSurcharge());
            if(flight.getScales() != null) flightToUpdate.setScales(flight.getScales());
            flightRepository.save(flightToUpdate);
        }
        return optionalFlight;
    }

    @Override
    public Optional<Flight> delete(Long id) {
        Optional<Flight> optionalFlight = findById(id);
        if(optionalFlight.isPresent()) flightRepository.deleteById(id);
        return optionalFlight;
    }
}
