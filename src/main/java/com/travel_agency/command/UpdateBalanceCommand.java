package com.travel_agency.command;

import com.travel_agency.controller.Command;
import com.travel_agency.controller.PageController;
import com.travel_agency.entity.User;
import com.travel_agency.exceptions.technicalexception.TravelAgencyCommandException;
import com.travel_agency.exceptions.technicalexception.TravelAgencyServiceException;
import com.travel_agency.service.ServiceFactory;
import com.travel_agency.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UpdateBalanceCommand implements Command {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getRootLogger();
    }

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.debug("start UpdateBalanceCommand");

        String page;
        HttpSession session = request.getSession(true);
        User currentUser = (User) request.getSession().getAttribute("user");
        int userID = currentUser.getId();

        ServiceFactory factory = ServiceFactory.getInstance();
        UserService userService = factory.getUserService();
        Double newBalance;

        try {
            try {
                newBalance = Double.parseDouble(request.getParameter("newBalance"));
                session.setAttribute("usermoney", newBalance);

            } catch (NumberFormatException e) {
                LOGGER.error("TravelAgencyCommandException error.", e);
                throw new TravelAgencyCommandException();
            }

            if (newBalance >= 0) {
                userService.setMoney(userID, newBalance);
                request.setAttribute("acceptedMessage", "accepted");
                page = PageController.ACCOUNT_PAGE;
            } else {
                LOGGER.error("Incorrect balace value.");
                request.setAttribute("error", "Incorrect balance value.");
                page = PageController.ERROR_PAGE;
            }
        } catch (TravelAgencyServiceException | TravelAgencyCommandException e) {
            LOGGER.error("TravelAgencyServiceException error.", e);
            request.setAttribute("error", "Incorrect balance value.");
            page = PageController.ERROR_PAGE;
        }
        LOGGER.debug("finish UpdateBalanceCommand");
        return page;
    }
}
