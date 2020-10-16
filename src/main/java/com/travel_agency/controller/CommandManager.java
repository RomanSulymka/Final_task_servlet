package com.travel_agency.controller;

import com.travel_agency.command.*;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private static CommandManager instance = new CommandManager();
    private Map<CommandContainer, Command> commands = new HashMap<>();

    private CommandManager() {
        commands.put(CommandContainer.EMPTY_COMMAND, new EmptyCommand());
        commands.put(CommandContainer.REGISTER, new RegisterUserCommand());
        commands.put(CommandContainer.LOGIN, new LogInCommand());
        commands.put(CommandContainer.LOGOUT, new LogOutCommand());
        commands.put(CommandContainer.TO_REGISTER, new ToRegisterPageCommand());
        commands.put(CommandContainer.TO_LOGIN, new ToLoginPageCommand());
        commands.put(CommandContainer.UA, new ChangeLocale());
        commands.put(CommandContainer.EN, new ChangeLocale());
        commands.put(CommandContainer.CHANGE_LOCALE, new ChangeLocale());
        commands.put(CommandContainer.VIEW_ALL_VAUCHERS, new ViewAllVauchers());
        commands.put(CommandContainer.VIEW_VAUCHERS_BY_COUNTRY, new ViewVauchersByCountry());
        commands.put(CommandContainer.VIEW_VAUCHERS_BY_TOUR_TYPE, new ViewVauchersByTourType());
        commands.put(CommandContainer.CHOOSE_VAUCHER, new ToChooseVaucherCommand());
        commands.put(CommandContainer.BOOK_VAUCHER, new BookVaucherCommand());
        commands.put(CommandContainer.CONTINUE, new ContinueRegistrCommand());
        commands.put(CommandContainer.VIEW_ACCOUNT, new ViewAccountCommand());
        commands.put(CommandContainer.UPDATE_BALANCE, new UpdateBalanceCommand());
        commands.put(CommandContainer.TO_USER_MENU, new ToUserMenu());
        commands.put(CommandContainer.SHOW_ORDER_BY_USER_ID, new ShowOrderByUserID());
        commands.put(CommandContainer.CANCEL_ORDER, new CancelOrderCommand());
        commands.put(CommandContainer.ADD_VAUCHER, new AddVaucherCommand());
        commands.put(CommandContainer.UPDATE_TOUR, new UpdateTourCommand());
        commands.put(CommandContainer.UPDATE_DISCOUNT, new UpdateDiscountCommand());

    }

    public static CommandManager getInstance() {
        return instance;
    }

    public Command getCommand(String commandName) {
        CommandContainer command = CommandContainer.valueOf(commandName.toUpperCase());
        return commands.get(command);
    }
}
