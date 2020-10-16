package com.travel_agency.service;

import com.travel_agency.entity.Entity;
import com.travel_agency.exceptions.technicalexception.TravelAgencyDAOException;
import com.travel_agency.exceptions.technicalexception.TravelAgencyServiceException;

import java.util.List;

public interface EntityService {

    void create(Entity entity) throws TravelAgencyDAOException, TravelAgencyServiceException;
    void update (Entity entity) throws TravelAgencyDAOException, TravelAgencyServiceException;
    void delete (int id) throws TravelAgencyServiceException;
    Entity findById (int id) throws TravelAgencyServiceException;
    List<Entity> findAll () throws TravelAgencyServiceException;
}
