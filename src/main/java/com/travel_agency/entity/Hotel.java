package com.travel_agency.entity;

import com.travel_agency.exceptions.TravelAgencyDataWrongException;

import java.util.Objects;

public class Hotel extends Entity {
    private String name;
    private double pricePerDay;

    public Hotel() {

    }

    public Hotel(int id, String name, double pricePerDay) {
        super(id);
        this.name = name;
        this.pricePerDay = pricePerDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws TravelAgencyDataWrongException {
        if (name != null && name != "") {
            this.name = name;
        } else {
            throw new TravelAgencyDataWrongException("Incorrect name value.");
        }
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) throws TravelAgencyDataWrongException {
        if (pricePerDay >= 0) {
            this.pricePerDay = pricePerDay;
        } else {
            throw new TravelAgencyDataWrongException("Incorrect pricePerDay value.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Hotel hotel = (Hotel) o;
        return Double.compare(hotel.pricePerDay, pricePerDay) == 0 &&
                name.equals(hotel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, pricePerDay);
    }

    @Override
    public String toString() {
        return "Hotel{" + super.toString() +
                "name='" + name + '\'' +
                ", pricePerDay=" + pricePerDay +
                '}';
    }
}
