package com.travel_agency.dao;

import com.travel_agency.entity.Entity;
import com.travel_agency.exceptions.technicalexception.TravelAgencyDAOException;

import java.util.List;

public interface EntityDao {
    void create(Entity entity) throws TravelAgencyDAOException;
    void update (Entity entity) throws TravelAgencyDAOException;
    void delete (int id) throws TravelAgencyDAOException;
    Entity findById (int id) throws TravelAgencyDAOException;
    List<Entity> findAll () throws TravelAgencyDAOException;

}
