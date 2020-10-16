package com.travel_agency.exceptions;

public class TravelAgencyDataWrongException extends TravelAgencyLogicException {

    public TravelAgencyDataWrongException() {
    }

    public TravelAgencyDataWrongException(String message) {
        super(message);
    }
}
