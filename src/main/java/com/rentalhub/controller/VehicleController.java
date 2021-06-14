package com.rentalhub.controller;

import com.rentalhub.dto.VehicleDto;
import com.rentalhub.mappers.VehicleMapper;
import com.rentalhub.model.Vehicle;
import com.rentalhub.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;
    private final VehicleMapper vehicleMapper;

    @Autowired
    public VehicleController(VehicleService vehicleService, VehicleMapper vehicleMapper) {
        this.vehicleService = vehicleService;
        this.vehicleMapper = vehicleMapper;
    }


    @PostMapping("/create-vehicle")
    public ResponseEntity<Vehicle> createVehicle(@RequestBody VehicleDto vehicleDto) {
        return ResponseEntity.ok(vehicleService.addVehicle(vehicleMapper.toVehicle(vehicleDto)));
    }

    @PutMapping("/update-vehicle")
    public ResponseEntity<Vehicle> editVehicle(@RequestBody VehicleDto vehicleDto) {

        return ResponseEntity.ok(vehicleService.editVehicle(vehicleMapper.toVehicle(vehicleDto)));
    }

    @PutMapping("/change-state/{vin}/{newState}")
    public ResponseEntity<Vehicle> block(@PathVariable String vin, @PathVariable boolean newState) {
        return ResponseEntity.ok(vehicleService.changeAvailability(vin, newState));
    }

    @GetMapping("/{vin}")
    public ResponseEntity<VehicleDto> getVehicle(@PathVariable String vin) {
        Optional<Vehicle> optionalVehicle = vehicleService.getVehicle(vin);
        return optionalVehicle.map(vehicle -> ResponseEntity.ok().body(vehicleMapper.toVehicleDto(vehicle)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
