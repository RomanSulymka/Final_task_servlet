package com.travel_agency.connection;

import org.apache.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DatabaseManager {

    public static final String JDBS_MYSQL_DRIVER = "db.driver";
    public static final String DATABASE_URL = "db.url";
    public static final String DATABASE_USER = "db.user";
    public static final String DATABASE_PASSWORD = "db.password";
    public static final String CONNECTION_COUNT = "db.connectionCount";
    private static final String CONFIG_FILE_NAME = "database";
    private static final Logger LOGGER;

    private static final ResourceBundle bundle = ResourceBundle.getBundle(CONFIG_FILE_NAME);

    static {
        LOGGER = Logger.getRootLogger();
    }

    public DatabaseManager() {
    }

    public static String getValue(String key) {
        String result = null;
        if (key != null) {
            try {
                result = bundle.getString(key);
            } catch (MissingResourceException e) {
                LOGGER.error("Object for the given key cannot be found");
            }
        }
        return result;
    }
}
