package com.example.carpooling.service.impl;

import com.example.carpooling.exceptions.CarServiceException;
import com.example.carpooling.model.Car;
import com.example.carpooling.repository.CarRepository;
import com.example.carpooling.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        super();
        this.carRepository = carRepository;
    }

    /**
     * @param cars
     * @return
     */
    @Override
    public void resetApp(List<Car> cars) {
        try {
            carRepository.deleteAll();

            List<Car> carsList = cars.stream()
                    .map(car -> {
                        Car newCar = new Car();
                        newCar.setId(car.getId());
                        newCar.setSeats(car.getSeats());
                        newCar.setAvailableSeats(car.getSeats());
                        return newCar;
                    })
                    .collect(Collectors.toList());

            carRepository.saveAll(carsList);
        } catch (Exception e) {
            throw new CarServiceException("Failed to register cars: " + e.getMessage());
        }

    }

}
