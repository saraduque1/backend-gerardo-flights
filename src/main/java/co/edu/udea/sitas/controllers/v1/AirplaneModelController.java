package co.edu.udea.sitas.controllers.v1;
import co.edu.udea.sitas.domain.dto.AirplaneModelDTO;
import co.edu.udea.sitas.domain.model.AirplaneModel;
import co.edu.udea.sitas.services.AirplaneModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/airplane-models")
public class AirplaneModelController {

    private final AirplaneModelService airplaneModelService;

    @Autowired
    public AirplaneModelController(AirplaneModelService airplaneModelService) {
        this.airplaneModelService = airplaneModelService;
    }

    @GetMapping
    public ResponseEntity<List<AirplaneModelDTO>> getAllAirplaneModels() {
        List<AirplaneModelDTO> airplaneModelDTOs = airplaneModelService.findAll().stream()
                .map(AirplaneModelDTO::buildAirplaneModelDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(airplaneModelDTOs, HttpStatus.OK);
    }

    @GetMapping("/{airplaneModel}")
    public ResponseEntity<AirplaneModelDTO> getAirplaneModelByModel(@PathVariable String airplaneModel) {
        Optional<AirplaneModel> airplaneModelOptional = airplaneModelService.findById(airplaneModel);
        return airplaneModelOptional.map(model -> new ResponseEntity<>(AirplaneModelDTO.buildAirplaneModelDTO(model), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<AirplaneModelDTO> createAirplaneModel(@RequestBody AirplaneModelDTO airplaneModelDTO) {
        AirplaneModel airplaneModel = AirplaneModel.builder()
                .airplaneModel(airplaneModelDTO.getAirplaneModel())
                .family(airplaneModelDTO.getFamily())
                .capacity(airplaneModelDTO.getCapacity())
                .cargoCapacity(airplaneModelDTO.getCargoCapacity())
                .volumeCapacity(airplaneModelDTO.getVolumeCapacity())
                .build();
        AirplaneModel savedAirplaneModel = airplaneModelService.save(airplaneModel);
        return new ResponseEntity<>(AirplaneModelDTO.buildAirplaneModelDTO(savedAirplaneModel), HttpStatus.CREATED);
    }

    @PutMapping("/{airplaneModel}")
    public ResponseEntity<AirplaneModelDTO> updateAirplaneModel(@PathVariable String airplaneModel, @RequestBody AirplaneModelDTO airplaneModelDTO) {
        Optional<AirplaneModel> airplaneModelOptional = airplaneModelService.update(airplaneModel, AirplaneModel.builder()
                .airplaneModel(airplaneModelDTO.getAirplaneModel())
                .family(airplaneModelDTO.getFamily())
                .capacity(airplaneModelDTO.getCapacity())
                .cargoCapacity(airplaneModelDTO.getCargoCapacity())
                .volumeCapacity(airplaneModelDTO.getVolumeCapacity())
                .build());
        return airplaneModelOptional.map(model -> new ResponseEntity<>(AirplaneModelDTO.buildAirplaneModelDTO(model), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}