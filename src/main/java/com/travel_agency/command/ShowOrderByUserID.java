package com.travel_agency.command;


import com.travel_agency.controller.Command;
import com.travel_agency.controller.PageController;
import com.travel_agency.entity.Entity;
import com.travel_agency.entity.User;
import com.travel_agency.exceptions.technicalexception.TravelAgencyServiceException;
import com.travel_agency.service.OrderService;
import com.travel_agency.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


public class ShowOrderByUserID implements Command {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getRootLogger();
    }

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.debug("start ShowOrderByUserID");

        HttpSession session = request.getSession(true);
        User user = (User) request.getSession().getAttribute("user");
        LOGGER.debug("for user: " + user);

        String page;

        ServiceFactory factory = ServiceFactory.getInstance();
        OrderService orderService = factory.getOrderService();

        try {
            List<Entity> ordersByUserId = orderService.findByUserId(user.getId());
            request.setAttribute("ordersByUserId", ordersByUserId);
            if (ordersByUserId.isEmpty()) {
                request.setAttribute("errorUserMenu", "You have no orders yet.");
            }
            page = PageController.USER_MENU_PAGE;

        } catch (TravelAgencyServiceException e) {
            LOGGER.error("ShowOrderByUserID error.", e);
            page = PageController.ERROR_PAGE;
        }
        LOGGER.debug("finish ShowOrderByUserID");
        return page;
    }
}
