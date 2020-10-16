package com.travel_agency.command;

import com.travel_agency.controller.Command;
import com.travel_agency.controller.PageController;
import com.travel_agency.entity.RoleType;
import com.travel_agency.entity.User;
import com.travel_agency.exceptions.TravelAgencyDataWrongException;
import com.travel_agency.exceptions.technicalexception.TravelAgencyDAOException;
import com.travel_agency.exceptions.technicalexception.TravelAgencyServiceException;
import com.travel_agency.service.ServiceFactory;
import com.travel_agency.service.UserService;
import com.travel_agency.validator.Validator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class RegisterUserCommand implements Command {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getRootLogger();
    }

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.debug("start RegisterUserCommand");

        User user = null;
        String page;
        if (validate(request)) {
            LOGGER.debug(request.getParameter("roleUser"));
            Validator validator = Validator.getInstance();

            try {
                if (validator.validateUniqeLogin(request.getParameter("login"))) {
                    user = new User();
                    user.setName(request.getParameter("name"));
                    user.setSurname(request.getParameter("surname"));
                    user.setMoney(Double.parseDouble(request.getParameter("money")));
                    user.setEmail(request.getParameter("email"));
                    user.setLogin(request.getParameter("login"));
                    user.setPassword(request.getParameter("password"));
                    user.setRole(RoleType.valueOf(request.getParameter("userRole").toUpperCase()));

                    String role = request.getParameter("userRole");

                    ServiceFactory factory = ServiceFactory.getInstance();
                    UserService userService = factory.getUserService();

                    userService.create(user);
                    if (user != null) {
                        request.setAttribute("user", user);
                        request.setAttribute("name", user.getName());
                        request.getSession().setAttribute("user", user);
                    }
                    page = PageController.RESULT_REGISTER_PAGE;
                } else {
                    request.setAttribute("error", "Login is not uniqe. Please try again.");
                    page = PageController.ERROR_PAGE;
                }
            } catch (TravelAgencyServiceException | TravelAgencyDAOException | TravelAgencyDataWrongException e) {
                LOGGER.error("RegisterUserCommand error.", e);
                page = PageController.ERROR_PAGE;
            }
        } else {
            request.setAttribute("error", "The data entered is not correct. Please try again.");
            page = PageController.ERROR_PAGE;
        }
        LOGGER.debug("finish RegisterUserCommand" + user);
        return page;
    }

    private boolean validate(HttpServletRequest request) {
        LOGGER.debug("start boolean validate");

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        Double money = Double.parseDouble(request.getParameter("money"));
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        Validator validator = Validator.getInstance();

        boolean result = validator.validateNameOrSurname(name) &&
                validator.validateNameOrSurname(surname) &&
                validator.validateMoney(money) &&
                validator.validateEmail(email) &&
                validator.validateLoginOrPassword(login) &&
                validator.validateLoginOrPassword(password);

        LOGGER.debug("finish boolean validate with: " + result);

        return result;
    }
}
