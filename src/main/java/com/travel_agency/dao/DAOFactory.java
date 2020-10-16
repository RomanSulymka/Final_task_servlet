package com.travel_agency.dao;

import com.travel_agency.dao.sql.*;

public class DAOFactory {

    private final UserDao USER_DAO = new UserDaoImplSql();
    private final VaucherDao VAUCHER_DAO = new VaucherDaoImplSql();
    private final TourDao TOUR_DAO = new TourDaoImplSql();
    private final HotelDao HOTEL_DAO = new HotelDaoImplSql();
    private final OrderDao ORDER_DAO = new OrderDaoImplSql();

    private static final DAOFactory instance = new DAOFactory();

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return instance;
    }

    public UserDao getUserDao() {
        return USER_DAO;
    }

    public VaucherDao getVaucherDao() {
        return VAUCHER_DAO;
    }

    public TourDao getTourDao() {
        return TOUR_DAO;
    }

    public HotelDao getHotelDao() {
        return HOTEL_DAO;
    }

    public OrderDao getOrderDao() {
        return ORDER_DAO;
    }

}
