package com.example.carpooling.service;

import com.example.carpooling.Dto.DropoffRequest;
import com.example.carpooling.Dto.JourneyRequest;
import com.example.carpooling.Dto.LocateRequest;
import com.example.carpooling.model.Car;
import com.example.carpooling.model.Journey;

public interface JourneyService {

    void performJourney(JourneyRequest journeyRequest);

    void droppoffJourney(DropoffRequest dropoffRequest);

    Journey locate(LocateRequest locateRequest);
}
