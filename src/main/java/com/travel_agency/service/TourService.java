package com.travel_agency.service;

import com.travel_agency.exceptions.technicalexception.TravelAgencyServiceException;

public interface TourService extends EntityService {
    void setHotTour(int id, boolean isHot) throws TravelAgencyServiceException;
}
