package com.travel_agency.service;

import com.travel_agency.impl.*;

public class ServiceFactory {

    private final UserService USER_SERVICE = new UserServiceImpl();
    private final TourService TOUR_SERVICE = new TourServiceImpl();
    private final HotelService HOTEL_SERVICE = new HotelServiceImpl();
    private final VaucherService VAUCHER_SERVICE = new VaucherServiceImpl();
    private final OrderService ORDER_SERVICE = new OrderServiceImpl();

    private static final ServiceFactory instance = new ServiceFactory();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return USER_SERVICE;
    }

    public TourService getTourService() {
        return TOUR_SERVICE;
    }

    public HotelService getHotelService() {
        return HOTEL_SERVICE;
    }

    public VaucherService getVaucherService() {
        return VAUCHER_SERVICE;
    }

    public OrderService getOrderService() {
        return ORDER_SERVICE;
    }
}

