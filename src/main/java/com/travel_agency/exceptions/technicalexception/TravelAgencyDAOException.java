package com.travel_agency.exceptions.technicalexception;

public class TravelAgencyDAOException extends TravelAgencyTechnicException {

    public TravelAgencyDAOException() {
    }

    public TravelAgencyDAOException(String message) {
        super(message);
    }

    public TravelAgencyDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public TravelAgencyDAOException(Throwable cause) {
        super(cause);
    }
}