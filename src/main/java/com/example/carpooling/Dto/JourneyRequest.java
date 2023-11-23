package com.example.carpooling.Dto;


public class JourneyRequest {
    private int id;
    private int people;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int grupSize) {
        this.people = grupSize;
    }

}
