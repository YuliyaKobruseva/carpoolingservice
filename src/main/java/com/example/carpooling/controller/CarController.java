package com.example.carpooling.controller;

import com.example.carpooling.exceptions.CarServiceException;
import com.example.carpooling.model.Car;
import com.example.carpooling.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carpooling")

public class CarController {

    private final CarService carService;


    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PutMapping("/cars")
    public ResponseEntity<String> resetApp(@RequestBody List<Car> cars) {
        try {
            carService.resetApp(cars);
            return ResponseEntity.ok("Application has been successfully restarted.");
        } catch (CarServiceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}





