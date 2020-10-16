package com.travel_agency.service;


import com.travel_agency.entity.Vaucher;
import com.travel_agency.exceptions.technicalexception.TravelAgencyServiceException;

import java.util.List;

public interface VaucherService extends EntityService {

    List<Vaucher> getVauchersByCountry(String country) throws TravelAgencyServiceException;
    List<Vaucher> getVauchersByTourType(String type) throws TravelAgencyServiceException;

}
