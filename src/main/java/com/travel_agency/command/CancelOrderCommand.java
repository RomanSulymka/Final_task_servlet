package com.travel_agency.command;

import com.travel_agency.controller.Command;
import com.travel_agency.controller.PageController;
import com.travel_agency.entity.Order;
import com.travel_agency.exceptions.technicalexception.TravelAgencyServiceException;
import com.travel_agency.service.OrderService;
import com.travel_agency.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class CancelOrderCommand implements Command {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getRootLogger();
    }

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.debug("start CancelOrderCommand");

        String page = null;

        ServiceFactory factory = ServiceFactory.getInstance();
        OrderService orderService = factory.getOrderService();

        String orderID = request.getParameter("myOrderId");
        LOGGER.debug("orderID: " + orderID);

        try {
            Order order = (Order) orderService.findById(Integer.parseInt(orderID));
            LOGGER.debug("cancel order: " + order);
            if (order != null) {
                orderService.cancelOrder(order);
            } else {
                request.setAttribute("errorUserMenu", "Order not found.");
            }
            page = PageController.USER_MENU_PAGE;

        } catch (TravelAgencyServiceException e) {
            LOGGER.error("CancelOrderCommand error.", e);
            page = PageController.ERROR_PAGE;
        } catch (NumberFormatException e) {
            LOGGER.error("CancelOrderCommand error.", e);
            request.setAttribute("error", "Select order to cancel");
            page = PageController.ERROR_PAGE;
        }

        LOGGER.debug("finish CancelOrderCommand");
        return page;
    }
}
