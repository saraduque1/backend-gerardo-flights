package co.edu.udea.sitas.controllers.v1;

import co.edu.udea.sitas.domain.dto.AirportDTO;
import co.edu.udea.sitas.domain.model.Airport;
import co.edu.udea.sitas.services.AirportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AirportControllerTest {

    @Mock
    private AirportService airportService;

    @InjectMocks
    private AirportController airportController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllAirports() {
        // Arrange
        AirportDTO airportDTO1 = new AirportDTO();
        AirportDTO airportDTO2 = new AirportDTO();
        List<AirportDTO> expectedAirports = Arrays.asList(airportDTO1, airportDTO2);

        when(airportService.findAll()).thenReturn(Arrays.asList(new Airport(), new Airport()));

        // Act
        ResponseEntity<List<AirportDTO>> response = airportController.getAllAirports();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedAirports, response.getBody());
        verify(airportService, times(1)).findAll();
    }

    @Test
    void getAirportByCode() {
        // Arrange
        String airportCode = "ABC";
        AirportDTO expectedAirport = new AirportDTO();
        when(airportService.findById(airportCode)).thenReturn(Optional.of(new Airport()));

        // Act
        ResponseEntity<AirportDTO> response = airportController.getAirportByCode(airportCode);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedAirport, response.getBody());
        verify(airportService, times(1)).findById(airportCode);
    }

    @Test
    void createAirport() {
        // Arrange
        AirportDTO requestDto = new AirportDTO();
        requestDto.setAirportCode("ABC");
        requestDto.setName("Airport Name");
        // Set other properties as needed

        Airport savedAirport = new Airport();
        when(airportService.save(any())).thenReturn(savedAirport);

        // Act
        ResponseEntity<AirportDTO> response = airportController.createAirport(requestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(airportService, times(1)).save(any());
    }

    @Test
    void updateAirport() {
        // Arrange
        String airportCode = "ABC";
        AirportDTO requestDto = new AirportDTO();
        requestDto.setAirportCode("ABC");
        requestDto.setName("Updated Airport Name");
        // Set other properties as needed

        Airport updatedAirport = new Airport();
        when(airportService.update(eq(airportCode), any())).thenReturn(Optional.of(updatedAirport));

        // Act
        ResponseEntity<AirportDTO> response = airportController.updateAirport(airportCode, requestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(airportService, times(1)).update(eq(airportCode), any());
    }
}