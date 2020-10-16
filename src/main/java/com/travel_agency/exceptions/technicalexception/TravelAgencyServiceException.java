package com.travel_agency.exceptions.technicalexception;

public class TravelAgencyServiceException extends TravelAgencyTechnicException {

    public TravelAgencyServiceException() {
    }

    public TravelAgencyServiceException(String message) {
        super(message);
    }

    public TravelAgencyServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public TravelAgencyServiceException(Throwable cause) {
        super(cause);
    }
}
