package com.travel_agency.command;

import com.travel_agency.controller.Command;
import com.travel_agency.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;

public class ChangeLocale implements Command {

    @Override
    public String execute(HttpServletRequest request) {

        String newLocalization = request.getParameter("localization");

        request.getSession().setAttribute("localization", newLocalization);
        Config.set(request.getSession(), Config.FMT_LOCALE, newLocalization);

        return PageController.INDEX_PAGE;
    }
}
