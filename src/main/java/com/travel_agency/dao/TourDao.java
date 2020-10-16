package com.travel_agency.dao;

import com.travel_agency.exceptions.technicalexception.TravelAgencyDAOException;

public interface TourDao extends EntityDao {
    void setHotTour(int id, boolean isHot) throws TravelAgencyDAOException;
}
