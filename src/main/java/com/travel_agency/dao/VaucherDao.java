package com.travel_agency.dao;

import com.travel_agency.entity.Vaucher;
import com.travel_agency.exceptions.technicalexception.TravelAgencyDAOException;

import java.util.List;

public interface VaucherDao extends EntityDao{
    List<Vaucher> getVauchersByCountry(String country) throws TravelAgencyDAOException;
    List<Vaucher> getVauchersByTourType(String type) throws TravelAgencyDAOException;
}
