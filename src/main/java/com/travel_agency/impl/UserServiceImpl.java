package com.travel_agency.impl;
import com.travel_agency.dao.DAOFactory;
import com.travel_agency.dao.UserDao;
import com.travel_agency.entity.Entity;
import com.travel_agency.entity.User;
import com.travel_agency.exceptions.technicalexception.TravelAgencyDAOException;
import com.travel_agency.exceptions.technicalexception.TravelAgencyServiceException;
import com.travel_agency.service.UserService;

import java.util.List;


public class UserServiceImpl implements UserService {

    private DAOFactory daoFactory = DAOFactory.getInstance();
    private UserDao userDao = daoFactory.getUserDao();

    public UserServiceImpl() {
    }

    @Override
    public User logIn(String login, String password) throws TravelAgencyServiceException {
        if (login != null && password != null) {
            try {
                User user = userDao.logIn(login, password);
                return user;
            } catch (TravelAgencyDAOException e) {
                throw new TravelAgencyServiceException(e);
            }
        } else {
            throw new TravelAgencyServiceException("Incorrect parameters.");
        }
    }

    @Override
    public void setDiscount(int id, double discount) throws TravelAgencyServiceException {
        if (id > 0 && discount >= 0) {
            try {
                userDao.setDiscount(id, discount);
            } catch (TravelAgencyDAOException e) {
                throw new TravelAgencyServiceException(e);
            }
        } else {
            throw new TravelAgencyServiceException("Incorrect parameters.");
        }
    }

    @Override
    public void setMoney(int id, double money) throws TravelAgencyServiceException {
        if (id > 0 && money >= 0) {
            try {
                userDao.setMoney(id, money);
            } catch (TravelAgencyDAOException e) {
                throw new TravelAgencyServiceException(e);
            }
        } else {
            throw new TravelAgencyServiceException("Incorrect parameters.");
        }
    }

    @Override
    public void create(Entity entity) throws TravelAgencyServiceException {
        if (entity != null) {
            try {
                userDao.create(entity);
            } catch (TravelAgencyDAOException e) {
                throw new TravelAgencyServiceException(e);
            }
        } else {
            throw new TravelAgencyServiceException("Incorrect parameters.");
        }
    }

    @Override
    public void update(Entity entity) throws TravelAgencyServiceException {
        if (entity != null) {
            try {
                userDao.update(entity);
            } catch (TravelAgencyDAOException e) {
                throw new TravelAgencyServiceException(e);
            }
        } else {
            throw new TravelAgencyServiceException("Incorrect parameters.");
        }
    }

    @Override
    public void delete(int id) throws TravelAgencyServiceException {
        if (id > 0) {
            try {
                userDao.delete(id);
            } catch (TravelAgencyDAOException e) {
                throw new TravelAgencyServiceException(e);
            }
        } else {
            throw new TravelAgencyServiceException("Incorrect parameters.");
        }
    }

    @Override
    public Entity findById(int id) throws TravelAgencyServiceException {
        if (id > 0) {
            try {
                Entity user = userDao.findById(id);
                return user;
            } catch (TravelAgencyDAOException e) {
                throw new TravelAgencyServiceException(e);
            }
        } else {
            throw new TravelAgencyServiceException("Incorrect parameters.");
        }
    }

    @Override
    public List<Entity> findAll() throws TravelAgencyServiceException {
        try {
            List<Entity> users = userDao.findAll();
            return users;
        } catch (TravelAgencyDAOException e) {
            throw new TravelAgencyServiceException(e);
        }
    }

}
