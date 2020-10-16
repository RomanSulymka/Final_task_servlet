package com.travel_agency.impl;

import com.travel_agency.dao.DAOFactory;
import com.travel_agency.dao.OrderDao;
import com.travel_agency.entity.Entity;
import com.travel_agency.entity.Order;
import com.travel_agency.exceptions.technicalexception.TravelAgencyDAOException;
import com.travel_agency.exceptions.technicalexception.TravelAgencyServiceException;
import com.travel_agency.service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private DAOFactory daoFactory = DAOFactory.getInstance();
    private OrderDao orderDao = daoFactory.getOrderDao();

    @Override
    public void cancelOrder(Order order) throws TravelAgencyServiceException {
        try {
            orderDao.cancelOrder(order);
        } catch (TravelAgencyDAOException e) {
            throw new TravelAgencyServiceException(e);
        }
    }

    @Override
    public void create(Entity entity) throws TravelAgencyServiceException {
        if (entity instanceof Order) {
            Order order = (Order) entity;
            try {
                orderDao.create(order);
            } catch (TravelAgencyDAOException  e) {
                throw new TravelAgencyServiceException(e);
            }
        } else {
            throw new TravelAgencyServiceException("Incorrect parameters.");
        }
    }

    @Override
    public void update(Entity entity) {
        throw new UnsupportedOperationException("Not implemented method");
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not implemented method");
    }

    @Override
    public Entity findById(int id) throws TravelAgencyServiceException {
        if (id > 0) {
            try {
                Entity order = orderDao.findById(id);
                return order;
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
            List<Entity> orders = orderDao.findAll();
            return orders;
        } catch (TravelAgencyDAOException e) {
            throw new TravelAgencyServiceException(e);
        }
    }

    @Override
    public List<Entity> findByUserId(int userID) throws TravelAgencyServiceException {
        try {
            List<Entity> orders = orderDao.findByUserId(userID);
            return orders;
        } catch (TravelAgencyDAOException e) {
            throw new TravelAgencyServiceException(e);
        }
    }
}
