package com.travel_agency.command;

import com.travel_agency.controller.Command;
import com.travel_agency.controller.PageController;
import com.travel_agency.entity.Entity;
import com.travel_agency.entity.Order;
import com.travel_agency.entity.Vaucher;
import com.travel_agency.exceptions.technicalexception.TravelAgencyServiceException;
import com.travel_agency.service.OrderService;
import com.travel_agency.service.ServiceFactory;
import com.travel_agency.service.VaucherService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
public class ToChooseVaucherCommand implements Command {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getRootLogger();
    }

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.debug("start ToChooseVaucherCommand");

        String page;

        ServiceFactory factory = ServiceFactory.getInstance();
        VaucherService vaucherService = factory.getVaucherService();
        OrderService orderService = factory.getOrderService();

        List<Entity> vauchers;
        List<Double> vaucherPrice = new ArrayList<>();

        try {
            List<Entity> orders = orderService.findAll();
            vauchers = vaucherService.findAll();
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
            //calculate vaucher total price and create array
            for (Entity vaucher : vauchers) {
                vaucherPrice.add(calculateVaucherTotalPrice((Vaucher) vaucher));
            }

            if (!vauchers.isEmpty()) {
                request.setAttribute("vauchers", vauchers);
                request.setAttribute("vaucherPrice", vaucherPrice);
            } else {
                request.setAttribute("errorUserMenu", "Vauchers not found");
            }
            request.setAttribute("vauchers", vauchers);
            page = PageController.USER_MENU_PAGE;

        } catch (TravelAgencyServiceException e) {
            LOGGER.error("LogInCommand error.", e);
            page = PageController.ERROR_PAGE;
        }

        LOGGER.debug("start ToChooseVaucherCommand");
        return page;
    }

    private double calculateVaucherTotalPrice(Vaucher vaucher) {
        int nights = (int)(vaucher.getDateTo().getTime() - vaucher.getDateFrom().getTime())/(24 * 60 * 60 * 1000);
        return (nights * vaucher.getHotel().getPricePerDay() + vaucher.getTour().getPrice());
    }
}
