package com.travel_agency.exceptions.technicalexception;

public class TravelAgencyConnectionPoolException extends TravelAgencyTechnicException {

    public TravelAgencyConnectionPoolException() {
    }

    public TravelAgencyConnectionPoolException(String message) {
        super(message);
    }

    public TravelAgencyConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public TravelAgencyConnectionPoolException(Throwable cause) {
        super(cause);
    }
}
