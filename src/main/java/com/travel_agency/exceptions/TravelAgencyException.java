package com.travel_agency.exceptions;

public class TravelAgencyException extends Exception {

    public TravelAgencyException() {
    }

    public TravelAgencyException(String message) {
        super(message);
    }

    public TravelAgencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public TravelAgencyException(Throwable cause) {
        super(cause);
    }
}