package com.example.carpooling.repository;

import com.example.carpooling.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByAvailableSeatsGreaterThanEqual(int availableSeats);

}
