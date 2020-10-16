package com.travel_agency.service;

import com.travel_agency.entity.Entity;
import com.travel_agency.entity.Order;
import com.travel_agency.exceptions.technicalexception.TravelAgencyServiceException;

import java.util.List;

public interface OrderService extends EntityService {
    void cancelOrder (Order order) throws TravelAgencyServiceException;
    List<Entity> findByUserId (int id) throws TravelAgencyServiceException;

}
