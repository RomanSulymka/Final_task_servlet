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

public class LogInCommand implements Command {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getRootLogger();
    }

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.debug("start LogInCommand");

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String page = null;

        ServiceFactory factory = ServiceFactory.getInstance();
        UserService userService = factory.getUserService();
        HttpSession session = request.getSession(true);
        User user = null;
        try {
            user = userService.logIn(login, password);

            if (user != null) {
                request.setAttribute("user", user);
                session.setAttribute("user", user);
                session.setAttribute("id", user.getId());
                session.setAttribute("name", user.getName());
                session.setAttribute("surname", user.getSurname());
                session.setAttribute("money", user.getMoney());
                session.setAttribute("email", user.getEmail());
                session.setAttribute("login", user.getLogin());
                session.setAttribute("userRole", user.getRole());

                if (user.getRole().toString().equals("CLIENT")) {
                    page = PageController.USER_MENU_PAGE;
                } else if (user.getRole().toString().equals("ADMIN")) {
                    page = PageController.ADMIN_MENU_PAGE;
                }
            } else {
                session.setAttribute("error", "User not found. Create your account, please.");
                page = PageController.ERROR_PAGE;
            }
        } catch (TravelAgencyServiceException e) {
            LOGGER.error("LogInCommand error.", e);
            page = PageController.ERROR_PAGE;
        }
        LOGGER.debug("finish LogInCommand: " + user);
        return page;
    }
}
