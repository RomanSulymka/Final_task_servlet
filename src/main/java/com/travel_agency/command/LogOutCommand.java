package com.travel_agency.command;

import com.travel_agency.controller.Command;
import com.travel_agency.controller.PageController;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getRootLogger();
    }

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.debug("start LogOutCommand");

        request.getSession().invalidate();

        LOGGER.debug("finish LogOutCommand");
        return PageController.INDEX_PAGE;
    }
}
