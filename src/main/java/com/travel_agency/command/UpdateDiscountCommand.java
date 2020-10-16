package com.travel_agency.command;

import com.travel_agency.controller.Command;
import com.travel_agency.controller.PageController;
import com.travel_agency.exceptions.technicalexception.TravelAgencyServiceException;
import com.travel_agency.service.ServiceFactory;
import com.travel_agency.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class UpdateDiscountCommand implements Command {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getRootLogger();
    }

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.debug("start UpdateDiscountCommand");
        String page;

        ServiceFactory factory = ServiceFactory.getInstance();
        UserService userService = factory.getUserService();

        try {
            // LOGGER.debug(request.getParameter("discountVal"));
            int idUser = Integer.parseInt(request.getParameter("idUser"));
            LOGGER.debug(idUser);
            double discount = Double.parseDouble(request.getParameter("discountVal"));


            LOGGER.debug(discount);
            userService.setDiscount(idUser, discount);

            request.setAttribute("acceptedMessageAdminDiscount", "update discount accepted");
            page = PageController.ADMIN_MENU_PAGE;

        } catch (TravelAgencyServiceException e) {
            LOGGER.error("UpdateDiscountCommand error.", e);
            page = PageController.ERROR_PAGE;
        }
        LOGGER.debug("finish UpdateDiscountCommand");
        return page;
    }

}
