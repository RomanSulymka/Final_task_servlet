package com.travel_agency.command;

import com.travel_agency.controller.Command;
import com.travel_agency.controller.PageController;

import javax.servlet.http.HttpServletRequest;
public class EmptyCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        return PageController.ERROR_PAGE;
    }
}
