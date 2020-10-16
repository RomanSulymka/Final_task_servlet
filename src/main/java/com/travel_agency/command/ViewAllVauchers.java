package com.travel_agency.command;

import com.travel_agency.controller.Command;
import com.travel_agency.controller.PageController;
import com.travel_agency.entity.Entity;
import com.travel_agency.entity.Order;
import com.travel_agency.exceptions.technicalexception.TravelAgencyServiceException;
import com.travel_agency.service.OrderService;
import com.travel_agency.service.ServiceFactory;
import com.travel_agency.service.VaucherService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ViewAllVauchers implements Command {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getRootLogger();
    }

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.debug("start ViewAllVauchers");

        List<Entity> vauchers;
        String page = null;

        ServiceFactory factory = ServiceFactory.getInstance();
        VaucherService vaucherService = factory.getVaucherService();
        OrderService orderService = factory.getOrderService();

        try {
            vauchers = vaucherService.findAll();
            List<Entity> orders = orderService.findAll();
            Order order;

            //not show not available to order vauchers
            for (int i = 0; i < vauchers.size(); i++) {
                for (Entity entity : orders) {
                    order = (Order) entity;
                    if (vauchers.get(i).getId() == order.getVaucher().getId()) {
                        vauchers.remove(i);
                    }
                }
            }

            if (!vauchers.isEmpty()) {
                request.setAttribute("vauchers", vauchers);
            } else {
                request.setAttribute("error", "Vauchers not found");
            }
            page = PageController.VIEW_ALL_VAUCHERS;
        } catch (TravelAgencyServiceException e) {
            LOGGER.error("ViewAllVauchers error.", e);
            page = PageController.ERROR_PAGE;
        }
        LOGGER.debug("finish ViewAllVauchers");
        return page;
    }
}
