package com.travel_agency.dao;

import com.travel_agency.entity.Entity;
import com.travel_agency.entity.Order;
import com.travel_agency.exceptions.technicalexception.TravelAgencyDAOException;

import java.util.List;

public interface OrderDao extends EntityDao {
    void cancelOrder (Order order) throws TravelAgencyDAOException;
    List<Entity> findByUserId (int userId) throws TravelAgencyDAOException;

}
