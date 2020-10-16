package com.travel_agency.controller;

import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    public static final String COMMAND = "command";
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getRootLogger();
    }

    public Controller() {
    }

    public void init() {
        LOGGER.debug("servlet init");
    }

    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("start get method");
        processRequest(request, response);
    }

    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("start post method");
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(COMMAND);

        Command command = CommandManager.getInstance().getCommand(commandName);
        LOGGER.debug("command: " + command.getClass().getSimpleName());

        String page = command.execute(request);
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);

        try {
            dispatcher.forward(request,response);
        } catch (ServletException | IOException e) {
            LOGGER.error("Controller error.", e);
            page = PageController.ERROR_PAGE;
            dispatcher = request.getRequestDispatcher(page);
            dispatcher.forward(request, response);
        }
    }

    public void destroy() {
        LOGGER.debug("servlet destroy");
        super.destroy();
    }
}
