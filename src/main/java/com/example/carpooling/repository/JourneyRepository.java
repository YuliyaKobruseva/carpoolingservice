package com.example.carpooling.repository;

import com.example.carpooling.model.Car;
import com.example.carpooling.model.Journey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface JourneyRepository extends JpaRepository<Journey, Long> {

    Optional<Journey> findByPeopleId(int peopleId);

}
