package com.travel_agency.command;

import com.travel_agency.controller.Command;
import com.travel_agency.controller.PageController;
import com.travel_agency.exceptions.technicalexception.TravelAgencyServiceException;
import com.travel_agency.service.ServiceFactory;
import com.travel_agency.service.TourService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class UpdateTourCommand implements Command {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getRootLogger();
    }

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.debug("start UpdateTourCommand");
        String page;

        ServiceFactory factory = ServiceFactory.getInstance();
        TourService tourService = factory.getTourService();

        try {
            int idTour = Integer.parseInt(request.getParameter("idTour"));
            boolean isHot = Boolean.parseBoolean(request.getParameter("isHot"));
            tourService.setHotTour(idTour, isHot);

            request.setAttribute("acceptedMessageAdminUpdate", "update tour accepted");
            page = PageController.ADMIN_MENU_PAGE;

        } catch (TravelAgencyServiceException e) {
            LOGGER.error("UpdateTourCommand error.", e);
            page = PageController.ERROR_PAGE;
        }
        LOGGER.debug("finish UpdateTourCommand");
        return page;
    }
}
