package com.travel_agency.impl;

import com.travel_agency.dao.DAOFactory;
import com.travel_agency.dao.VaucherDao;
import com.travel_agency.entity.Entity;
import com.travel_agency.entity.Vaucher;
import com.travel_agency.exceptions.technicalexception.TravelAgencyDAOException;
import com.travel_agency.exceptions.technicalexception.TravelAgencyServiceException;
import com.travel_agency.service.VaucherService;

import java.util.List;

public class VaucherServiceImpl implements VaucherService {

    private DAOFactory daoFactory = DAOFactory.getInstance();
    private VaucherDao vaucherDao = daoFactory.getVaucherDao();

    public VaucherServiceImpl() {
    }

    @Override
    public List<Vaucher> getVauchersByCountry(String country) throws TravelAgencyServiceException {
        if (country != null) {
            try {
                List<Vaucher> vauchers = vaucherDao.getVauchersByCountry(country);
                return vauchers;
            } catch (TravelAgencyDAOException e) {
                throw new TravelAgencyServiceException(e);
            }
        } else {
            throw new TravelAgencyServiceException("Incorrect parameters.");
        }
    }

    @Override
    public List<Vaucher> getVauchersByTourType(String type) throws TravelAgencyServiceException {
        if (type != null) {
            try {
                List<Vaucher> vauchers = vaucherDao.getVauchersByTourType(type);
                return vauchers;
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
                vaucherDao.create(entity);
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
                vaucherDao.update(entity);
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
                vaucherDao.delete(id);
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
                Entity vauher = vaucherDao.findById(id);
                return vauher;
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
            List<Entity> vauchers = vaucherDao.findAll();
            return vauchers;
        } catch (TravelAgencyDAOException e) {
            throw new TravelAgencyServiceException(e);
        }
    }
}
