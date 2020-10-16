package com.travel_agency.command;

import com.travel_agency.controller.Command;
import com.travel_agency.controller.PageController;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ToRegisterPageCommand implements Command {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getRootLogger();
    }

    public static final String TO_REGISTR = "toRegister";

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.debug("start ToRegisterPageCommand");

        String toRegister = request.getParameter(TO_REGISTR);
        request.setAttribute(TO_REGISTR, toRegister);

        LOGGER.debug("finish ToRegisterPageCommand");
        return PageController.REGISTER_PAGE;
    }
}
