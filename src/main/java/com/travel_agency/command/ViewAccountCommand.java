package com.travel_agency.command;

import com.travel_agency.controller.Command;
import com.travel_agency.controller.PageController;
import com.travel_agency.entity.User;
import com.travel_agency.exceptions.technicalexception.TravelAgencyServiceException;
import com.travel_agency.service.ServiceFactory;
import com.travel_agency.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ViewAccountCommand implements Command {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getRootLogger();
    }

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.debug("start ViewAccountCommand");
        String page;
        HttpSession session = request.getSession(true);
        User currentUser = (User) request.getSession().getAttribute("user");
        int userID = currentUser.getId();

        LOGGER.debug("ViewAccountCommand user: " + currentUser);

        ServiceFactory factory = ServiceFactory.getInstance();
        UserService userService = factory.getUserService();

        try {
            User user = (User) userService.findById(userID);
            if (user != null) {
                request.setAttribute("user", user);
                session.setAttribute("username", user.getName());
                session.setAttribute("usersurname", user.getSurname());
                session.setAttribute("useremail", user.getEmail());
                session.setAttribute("usermoney", user.getMoney());
                session.setAttribute("userdiscount", user.getDiscount());
                session.setAttribute("userlogin", user.getLogin());

                page = PageController.ACCOUNT_PAGE;
            } else {
                LOGGER.error("User not found.");
                session.setAttribute("error", "User not found.");
                page = PageController.ERROR_PAGE;
            }

        } catch (TravelAgencyServiceException e) {
            LOGGER.error("ViewAccountCommand error.", e);
            page = PageController.ERROR_PAGE;
        }
        LOGGER.debug("finish ViewAccountCommand");
        return page;
    }
}
