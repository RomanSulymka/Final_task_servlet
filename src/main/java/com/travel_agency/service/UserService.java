package com.travel_agency.service;


import com.travel_agency.entity.User;
import com.travel_agency.exceptions.technicalexception.TravelAgencyServiceException;

public interface UserService extends EntityService {

    User logIn(String login, String password) throws TravelAgencyServiceException;
    void setDiscount(int id, double discount) throws TravelAgencyServiceException;
    void setMoney(int id, double money) throws TravelAgencyServiceException;

}
