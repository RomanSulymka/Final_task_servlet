package com.travel_agency.command;

import com.travel_agency.controller.Command;
import com.travel_agency.controller.PageController;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ToLoginPageCommand implements Command {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getRootLogger();
    }

    public static final String TO_LOGIN = "toLogin";

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.debug("start ToLoginPageCommand");

        String toLogin = request.getParameter(TO_LOGIN);
        request.setAttribute(TO_LOGIN, toLogin);

        LOGGER.debug("finish ToLoginPageCommand");
        return PageController.LOGIN_PAGE;
    }
}
