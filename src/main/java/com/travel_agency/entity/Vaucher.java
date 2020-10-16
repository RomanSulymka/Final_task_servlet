package com.travel_agency.entity;

import com.travel_agency.exceptions.TravelAgencyDataWrongException;

import java.util.Date;
import java.util.Objects;

public class Vaucher extends Entity{

    private String country;
    private Date dateFrom;
    private Date dateTo;
    private Tour tour;
    private TransportType transport;
    private Hotel hotel;

    public Vaucher() {
    }

    public Vaucher(int id, String country, Date dateFrom, Date dateTo, Tour tour, TransportType transport, Hotel hotel) {
        super(id);
        this.country = country;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.tour = tour;
        this.transport = transport;
        this.hotel = hotel;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) throws TravelAgencyDataWrongException {
        if (country != null) {
            this.country = country;
        } else {
            throw new TravelAgencyDataWrongException("Incorrect country value.");
        }
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) throws TravelAgencyDataWrongException {
        if (dateFrom != null) {
            this.dateFrom = dateFrom;
        } else {
            throw new TravelAgencyDataWrongException("Incorrect dateFrom value.");
        }
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) throws TravelAgencyDataWrongException {
        if (dateTo != null) {
            this.dateTo = dateTo;
        } else {
            throw new TravelAgencyDataWrongException("Incorrect dateTo value.");
        }
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) throws TravelAgencyDataWrongException {
        if (tour != null) {
            this.tour = tour;
        } else {
            throw new TravelAgencyDataWrongException("Incorrect tour value.");
        }
    }

    public TransportType getTransport() {
        return transport;
    }

    public void setTransport(TransportType transport) throws TravelAgencyDataWrongException {
        if (transport != null) {
            this.transport = transport;
        } else {
            throw new TravelAgencyDataWrongException("Incorrect transport value.");
        }
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) throws TravelAgencyDataWrongException {
        if (hotel != null) {
            this.hotel = hotel;
        } else {
            throw new TravelAgencyDataWrongException("Incorrect hotel value.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Vaucher vaucher = (Vaucher) o;
        return country.equals(vaucher.country) &&
                dateFrom.equals(vaucher.dateFrom) &&
                dateTo.equals(vaucher.dateTo) &&
                tour.equals(vaucher.tour) &&
                transport == vaucher.transport &&
                hotel.equals(vaucher.hotel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), country, dateFrom, dateTo, tour, transport, hotel);
    }

    @Override
    public String toString() {
        return "Vaucher{" + super.toString() +
                "country='" + country + '\'' +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", tour=" + tour +
                ", transport=" + transport +
                ", hotel=" + hotel +
                '}';
    }
}
