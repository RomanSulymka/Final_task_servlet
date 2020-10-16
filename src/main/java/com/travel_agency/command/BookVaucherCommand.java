package com.travel_agency.command;

import com.travel_agency.controller.Command;
import com.travel_agency.controller.PageController;
import com.travel_agency.entity.Entity;
import com.travel_agency.entity.Order;
import com.travel_agency.entity.User;
import com.travel_agency.entity.Vaucher;
import com.travel_agency.exceptions.TravelAgencyDataWrongException;
import com.travel_agency.exceptions.technicalexception.TravelAgencyDAOException;
import com.travel_agency.exceptions.technicalexception.TravelAgencyServiceException;
import com.travel_agency.service.OrderService;
import com.travel_agency.service.ServiceFactory;
import com.travel_agency.service.UserService;
import com.travel_agency.service.VaucherService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BookVaucherCommand implements Command {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getRootLogger();
    }

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.debug("start BookVaucherCommand");

        String page = null;

        ServiceFactory factory = ServiceFactory.getInstance();
        OrderService orderService = factory.getOrderService();
        VaucherService vaucherService = factory.getVaucherService();
        UserService userService = factory.getUserService();

        String vaucherID = request.getParameter("idvaucher");
        LOGGER.debug("vaucherID: " + vaucherID);
        String userID = request.getParameter("id");
        LOGGER.debug("userID: " + userID);
        List<Entity> vauchers;

        Order order = null;

        try {
            vauchers = vaucherService.findAll();

            Vaucher vaucher = (Vaucher) vaucherService.findById(Integer.parseInt(vaucherID));
            LOGGER.debug("vaucher: " + vaucher);
            User user = (User) userService.findById(Integer.parseInt(userID));
            LOGGER.debug("user: " + user);

            order = new Order();
            double totalPrice = calculateTotalPrice(vaucher, user);

            if (user.getMoney() >= totalPrice) {
                order.setUser(user);
                order.setVaucher(vaucher);
                orderService.create(order);

                LOGGER.debug("order: " + order);

                request.setAttribute("vauchers", vauchers);

                request.setAttribute("userName", user.getName());
                request.setAttribute("countryOrder", order.getVaucher().getCountry());
                request.setAttribute("totalPrice", totalPrice);

                page = PageController.FINISH_ORDER;

            } else {
                request.setAttribute("notEnouthMoneyMessage", "Sorry, not enough money to pay.");
                page = PageController.USER_MENU_PAGE;
            }

        } catch (TravelAgencyDAOException e) {
            LOGGER.error("BookVaucherCommand error.", e);
            page = PageController.ERROR_PAGE;

        } catch (TravelAgencyServiceException e) {
            LOGGER.error("BookVaucherCommand error.", e);
            page = PageController.ERROR_PAGE;

        } catch (NumberFormatException e) {
            LOGGER.error("BookVaucherCommand error.", e);
            request.setAttribute("error", "Create order, please.");
            page = PageController.ERROR_PAGE;
        } catch (TravelAgencyDataWrongException e) {
            e.printStackTrace();
        }
        LOGGER.debug("finish BookVaucherCommand: " + order);
        return page;
    }

    private double calculateTotalPrice(Vaucher vaucher, User user) {
        int nights = (int)(vaucher.getDateTo().getTime() - vaucher.getDateFrom().getTime())/(24 * 60 * 60 * 1000);
        double totalPrice = (nights * vaucher.getHotel().getPricePerDay() + vaucher.getTour().getPrice())*(100 - user.getDiscount())/100;
        return totalPrice;
    }
}
