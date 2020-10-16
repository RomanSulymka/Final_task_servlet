package com.travel_agency.impl;

import com.travel_agency.dao.DAOFactory;
import com.travel_agency.dao.HotelDao;
import com.travel_agency.entity.Entity;
import com.travel_agency.exceptions.technicalexception.TravelAgencyDAOException;
import com.travel_agency.exceptions.technicalexception.TravelAgencyServiceException;
import com.travel_agency.service.HotelService;

import java.util.List;

public class HotelServiceImpl implements HotelService {

    private DAOFactory daoFactory = DAOFactory.getInstance();
    private HotelDao hotelDao = daoFactory.getHotelDao();

    public HotelServiceImpl() {
    }

    @Override
    public void create(Entity entity) throws TravelAgencyServiceException {
        if (entity != null) {
            try {
                hotelDao.create(entity);
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
                hotelDao.update(entity);
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
                hotelDao.delete(id);
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
                Entity hotel = hotelDao.findById(id);
                return hotel;
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
            List<Entity> hotels = hotelDao.findAll();
            return  hotels;
        } catch (TravelAgencyDAOException e) {
            throw new TravelAgencyServiceException(e);
        }
    }
}
