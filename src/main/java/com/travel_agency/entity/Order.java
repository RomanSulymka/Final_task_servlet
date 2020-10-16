package com.travel_agency.entity;

import com.travel_agency.exceptions.TravelAgencyDataWrongException;

import java.util.Objects;

public class Order extends Entity {
    private Vaucher vaucher;
    private User user;
    private Double totalPrice;

    public Order() {
    }

    public Order(Vaucher vaucher, User user) {
        this.vaucher = vaucher;
        this.user = user;
    }

    public Order(int id, Vaucher vaucher, User user, Double totalPrice) {
        super(id);
        this.vaucher = vaucher;
        this.user = user;
        this.totalPrice = totalPrice;
    }

    public Vaucher getVaucher() {
        return vaucher;
    }

    public void setVaucher(Vaucher vaucher) throws TravelAgencyDataWrongException {
        if (vaucher != null) {
            this.vaucher = vaucher;
        } else {
            throw new TravelAgencyDataWrongException("Incorrect vaucher value.");
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) throws TravelAgencyDataWrongException {
        if (user != null) {
            this.user = user;
        } else {
            throw new TravelAgencyDataWrongException("Incorrect user value.");
        }
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) throws TravelAgencyDataWrongException {
        if (totalPrice >= 0) {
            this.totalPrice = totalPrice;
        } else {
            throw new TravelAgencyDataWrongException("Incorrect totalPrice value.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return vaucher.equals(order.vaucher) &&
                user.equals(order.user) &&
                totalPrice.equals(order.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), vaucher, user, totalPrice);
    }

    @Override
    public String toString() {
        return "Order{" + super.toString() +
                "vaucher=" + vaucher +
                ", user=" + user +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
