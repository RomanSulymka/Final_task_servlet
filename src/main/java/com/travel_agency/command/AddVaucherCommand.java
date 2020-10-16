package com.travel_agency.command;


import com.travel_agency.controller.Command;
import com.travel_agency.controller.PageController;
import com.travel_agency.entity.*;
import com.travel_agency.exceptions.TravelAgencyDataWrongException;
import com.travel_agency.exceptions.technicalexception.TravelAgencyDAOException;
import com.travel_agency.exceptions.technicalexception.TravelAgencyServiceException;
import com.travel_agency.service.HotelService;
import com.travel_agency.service.ServiceFactory;
import com.travel_agency.service.TourService;
import com.travel_agency.service.VaucherService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class AddVaucherCommand implements Command {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getRootLogger();
    }

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.debug("start AddVaucherCommand");
        String page = null;

        ServiceFactory factory = ServiceFactory.getInstance();
        TourService tourService = factory.getTourService();
        HotelService hotelService = factory.getHotelService();
        VaucherService vaucherService = factory.getVaucherService();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        try {
            List<Entity> tours = tourService.findAll();
            List<Entity> hotels = hotelService.findAll();
            request.setAttribute("tours", tours);
            LOGGER.debug("tours " + tours);
            request.setAttribute("hotels", hotels);
            LOGGER.debug("tours " + tours);

            String country = request.getParameter("countryVal");
            String dateFrom = request.getParameter("dateFromVal");
            String dateTo = request.getParameter("dateToVal");

            int idTour = Integer.parseInt(request.getParameter("idTour"));
            int idTransport = Integer.parseInt(request.getParameter("idTransport"));
            int idHotel = Integer.parseInt(request.getParameter("idHotel"));

            Vaucher vaucher = new Vaucher();
            vaucher.setCountry(country);
            vaucher.setDateFrom(dateFormat.parse(dateFrom));
            vaucher.setDateTo(dateFormat.parse(dateTo));
            vaucher.setTour((Tour) tourService.findById(idTour));
            vaucher.setTransport(TransportType.getValue(idTransport));
            vaucher.setHotel((Hotel) hotelService.findById(idHotel));

            LOGGER.debug("add vaucher " + vaucher);

            vaucherService.create(vaucher);
            request.setAttribute("acceptedMessageAdminAdd", "add vaucher accepted");
            page = PageController.ADMIN_MENU_PAGE;

        } catch (TravelAgencyServiceException | TravelAgencyDAOException e) {
            LOGGER.error("AddVaucherCommand error.", e);
            page = PageController.ERROR_PAGE;
        } catch (ParseException | NumberFormatException e) {
            LOGGER.error("AddVaucherCommand error.", e);
            request.setAttribute("error", "Incorrect data value. Please try again.");
            page = PageController.ERROR_PAGE;
        } catch (TravelAgencyDataWrongException e) {
            e.printStackTrace();
        }

        LOGGER.debug("finish AddVaucherCommand");
        return page;
    }
}
