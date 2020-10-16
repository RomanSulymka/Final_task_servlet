package com.travel_agency.entity;

import com.travel_agency.exceptions.TravelAgencyDataWrongException;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tour extends Entity{
    private String type;
    private double price;
    private boolean hot;

    public Tour() {
    }

    public Tour(int id, String type, double price, boolean hot) {
        super(id);
        this.type = type;
        this.price = price;
        this.hot = hot;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) throws TravelAgencyDataWrongException {
        if (type != null) {
            this.type = type;
        } else {
            throw new TravelAgencyDataWrongException("Incorrect Tour Type value.");
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) throws TravelAgencyDataWrongException {
        if (price >= 0) {
            this.price = price;
        } else {
            throw new TravelAgencyDataWrongException("Incorrect price value.");
        }
    }

    public boolean isHot() {
        return hot;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tour tour = (Tour) o;
        return Double.compare(tour.price, price) == 0 &&
                hot == tour.hot &&
                type == tour.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type, price, hot);
    }

    @Override
    public String toString() {
        return "Tour{" + super.toString() +
                "type=" + type +
                ", price=" + price +
                ", hot=" + hot +
                '}';
    }
}
