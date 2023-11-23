package com.example.carpooling.service.impl;

import com.example.carpooling.Dto.DropoffRequest;
import com.example.carpooling.Dto.JourneyRequest;
import com.example.carpooling.Dto.LocateRequest;
import com.example.carpooling.enums.GroupStatus;
import com.example.carpooling.model.Car;
import com.example.carpooling.model.Journey;
import com.example.carpooling.repository.CarRepository;
import com.example.carpooling.repository.JourneyRepository;
import com.example.carpooling.service.JourneyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class JourneyServiceImpl implements JourneyService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private JourneyRepository journeyRepository;

    /**
     * @return
     */
    @Override
    public void performJourney(JourneyRequest journeyRequest) {

        int numberOfPeople = journeyRequest.getPeople();
        if (numberOfPeople < 1 || numberOfPeople > 6) {
            throw new IllegalArgumentException("Invalid number of people in the group.");
        }

        List<Car> availableCars = getAvailableCars(numberOfPeople);
        if (!availableCars.isEmpty()) {
            Car car = availableCars.get(0);
            car.setAvailableSeats(car.getSeats() - numberOfPeople);
            carRepository.save(car);

            Journey journey = new Journey();
            journey.setPeopleId(journeyRequest.getId());
            journey.setNumberOfPeople(journeyRequest.getPeople());
            journey.setStatus(GroupStatus.ASSIGNED);
            journey.setCar(car);
            journeyRepository.save(journey);
        } else {
            Journey journey = new Journey();
            journey.setNumberOfPeople(numberOfPeople);
            journey.setStatus(GroupStatus.WAITING);
            journeyRepository.save(journey);
        }

    }

    public void droppoffJourney(DropoffRequest dropoffRequest) {
        Journey journey = journeyRepository.findByPeopleId(dropoffRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("People not found with ID: " + dropoffRequest.getId()));

        journey.setStatus(GroupStatus.COMPLETED);
        journeyRepository.save(journey);

        Car car = journey.getCar();
        car.setAvailableSeats(car.getAvailableSeats() + journey.getNumberOfPeople());
        carRepository.save(car);
    }

    /**
     * @param locateRequest
     * @return
     */
    @Override
    public Journey locate(LocateRequest locateRequest) {
        if (Integer.valueOf(locateRequest.getId()) == null) {
            throw new IllegalArgumentException("Invalid request format: Missing group ID.");
        }
        Journey journey = journeyRepository.findByPeopleId(locateRequest.getId())
                .orElseThrow(() -> new EntityNotFoundException("Group not found with ID: " + locateRequest.getId()));
        return journey;
    }


    private List<Car> getAvailableCars(int groupSize) {
        return carRepository.findByAvailableSeatsGreaterThanEqual(groupSize);
    }
}
