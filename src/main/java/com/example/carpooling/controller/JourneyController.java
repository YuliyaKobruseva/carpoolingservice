package com.example.carpooling.controller;

import com.example.carpooling.Dto.DropoffRequest;
import com.example.carpooling.Dto.JourneyRequest;
import com.example.carpooling.Dto.LocateRequest;
import com.example.carpooling.model.Car;
import com.example.carpooling.service.JourneyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carpooling")

public class JourneyController {

    @Autowired
    JourneyService journeyService;

    @PostMapping("/journey")
    public ResponseEntity<Void> performJourney(@RequestBody JourneyRequest journeyRequest) {
        try {
            journeyService.performJourney(journeyRequest);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/dropoff")
    public ResponseEntity<String> requestDropoff(@RequestBody DropoffRequest dropoffRequest) {
        try {
            journeyService.droppoffJourney(dropoffRequest);
            return ResponseEntity.ok("Dropoff request processed successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid dropoff request: " + e.getMessage());
        }
    }

    @PostMapping(value = "/locate", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> locateGroup(@ModelAttribute LocateRequest locateRequest) {
        try {
            Car car = journeyService.locate(locateRequest).getCar();
            if (car == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(car);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Bad Request: " + e.getMessage());
        }
    }
}





