package com.travel_agency.dao;

import com.travel_agency.entity.User;
import com.travel_agency.exceptions.technicalexception.TravelAgencyDAOException;

public interface UserDao extends EntityDao {
    User logIn(String login, String password) throws TravelAgencyDAOException;
    void setDiscount(int id, double discount) throws TravelAgencyDAOException;
    void setMoney(int id, double money) throws TravelAgencyDAOException;
}
