package com.travel_agency.exceptions.technicalexception;

import com.travel_agency.exceptions.TravelAgencyException;

public class TravelAgencyTechnicException extends TravelAgencyException {

    public TravelAgencyTechnicException() {
    }

    public TravelAgencyTechnicException(String message) {
        super(message);
    }

    public TravelAgencyTechnicException(String message, Throwable cause) {
        super(message, cause);
    }

    public TravelAgencyTechnicException(Throwable cause) {
        super(cause);
    }
}