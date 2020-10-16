package com.travel_agency.command;

import com.travel_agency.controller.Command;
import com.travel_agency.controller.PageController;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ToUserMenu implements Command {
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getRootLogger();
    }

    public static final String TO_USER_MENU = "toUserMenu";

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.debug("start ToUserMenu");

        String toUserMenu = request.getParameter(TO_USER_MENU);
        request.setAttribute(TO_USER_MENU, toUserMenu);

        LOGGER.debug("finish ToUserMenu");
        return PageController.USER_MENU_PAGE;
    }
}
