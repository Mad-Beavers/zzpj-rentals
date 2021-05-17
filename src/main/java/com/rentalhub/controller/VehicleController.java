package com.rentalhub.controller;

import com.rentalhub.dto.ClientDto;
import com.rentalhub.dto.VehicleDto;
import com.rentalhub.mappers.ClientMapper;
import com.rentalhub.mappers.VehicleMapper;
import com.rentalhub.service.UserService;
import com.rentalhub.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

    private VehicleService vehicleService;
    private VehicleMapper vehicleMapper;

    @Autowired
    public VehicleController(VehicleService vehicleService, VehicleMapper vehicleMapper) {
        this.vehicleService = vehicleService;
        this.vehicleMapper = vehicleMapper;
    }


    @PostMapping("/editClient")
    public ResponseEntity createVehicle(VehicleDto vehicleDto) {
        vehicleService.addVehicle(vehicleMapper.toVehicle(vehicleDto));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/editVehicle")
    public ResponseEntity editVehicle(VehicleDto vehicleDto) {
        vehicleService.editVehicle(vehicleMapper.toVehicle(vehicleDto));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/blockVehicle")
    public ResponseEntity block(String vin) {
        vehicleService.changeAvailability(vin, false);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/unblockVehicle")
    public ResponseEntity unblock(String vin) {
        vehicleService.changeAvailability(vin, false);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/getVehicle")
    public ResponseEntity getVehicle(String vin) {
        return ResponseEntity.ok().body(vehicleService.getVehicle(vin));
    }
}
