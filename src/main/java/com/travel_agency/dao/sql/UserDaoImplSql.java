package com.travel_agency.dao.sql;

import com.travel_agency.connection.ConnectionPool;
import com.travel_agency.dao.UserDao;
import com.travel_agency.entity.*;
import com.travel_agency.exceptions.TravelAgencyDataWrongException;
import com.travel_agency.exceptions.technicalexception.TravelAgencyConnectionPoolException;
import com.travel_agency.exceptions.technicalexception.TravelAgencyDAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class UserDaoImplSql implements UserDao {

    private static final String SQL_FIND_USER_BY_LOGIN_AND_PASSWORD
            = "SELECT iduser, name, surname, discount, money, email, login, password, role_idrole FROM user WHERE login = ? AND password=?;";
    private static final String SQL_UPDATE_USER_DISCOUNT_BY_ID = "UPDATE user SET discount=? WHERE iduser=?;";
    private static final String SQL_UPDATE_USER_MONEY_BY_ID = "UPDATE user SET money=? WHERE iduser=?;";
    private static final String SQL_INSERT_USER
            = "INSERT INTO user (name, surname, discount, money, email, login, password, role_idrole) VALUES (?,?,?,?,?,?,?,?);";
    private static final String SQL_UPDATE_USER_BY_ID
            = "UPDATE user SET name=?, surname=?, discount=?, money=?, email=?, login=?, password=?, role_idrole=? WHERE iduser=?;";
    private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM user WHERE iduser = ?;";
    private static final String SQL_SELECT_USER_BY_ID
            = "SELECT iduser, name, surname, discount, money, email, login, password, role_idrole FROM user WHERE iduser = ?;";
    private static final String SQL_SELECT_ALL_USER
            = "SELECT iduser, name, surname, discount, money, email, login, password, role_idrole FROM user";

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getRootLogger();
    }

    @Override
    public User logIn(String login, String password) throws TravelAgencyDAOException {
        LOGGER.debug("start user logIn");
        User user = null;

        if (login !=null && !login.equals("") && password !=null && !password.equals("")) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                connection = connectionPool.takeConnection();
                ps = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN_AND_PASSWORD);
                ps.setString(1, login);
                ps.setString(2, password);
                rs = ps.executeQuery();

                while (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt(1));
                    user.setName(rs.getString(2));
                    user.setSurname(rs.getString(3));
                    user.setDiscount(rs.getDouble(4));
                    user.setMoney(rs.getDouble(5));
                    user.setEmail(rs.getString(6));
                    user.setLogin(rs.getString(7));
                    user.setPassword(rs.getString(8));
                    user.setRole(RoleType.getValue(rs.getInt(9)));
                }
            } catch (TravelAgencyConnectionPoolException | SQLException | TravelAgencyDataWrongException e) {
                LOGGER.error("user logIn exception ", e);
                throw new TravelAgencyDAOException("user logIn exception ", e);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        LOGGER.error("database access error occurs", e);
                        throw new TravelAgencyDAOException("database access error occurs", e);
                    }
                }
                if (connectionPool != null) {
                    connectionPool.releaseConnection(connection);
                }
                LOGGER.debug("finish user registration");
            }
        }
        return user;
    }

    @Override
    public void setDiscount(int id, double discount) throws TravelAgencyDAOException {
        LOGGER.debug("start setDiscount user by ID");

        if (id > 0 && discount >= 0 && discount <= 100) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = null;
            PreparedStatement ps = null;
            try {
                connection = connectionPool.takeConnection();
                connection.setAutoCommit(false);
                ps = connection.prepareStatement(SQL_UPDATE_USER_DISCOUNT_BY_ID);
                ps.setDouble(1, discount);
                ps.setInt(2, id);
                ps.executeUpdate();
                connection.commit();
            } catch (TravelAgencyConnectionPoolException | SQLException e) {
                LOGGER.error("user setDiscount exception ", e);
                throw new TravelAgencyDAOException("user setDiscount exception ", e);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        LOGGER.error("database access error occurs", e);
                        throw new TravelAgencyDAOException("database access error occurs", e);
                    }
                }
                if (connectionPool != null) {
                    connectionPool.releaseConnection(connection);
                }
                LOGGER.debug("finish setDiscount user by ID");
            }
        }
    }

    @Override
    public void setMoney(int id, double money) throws TravelAgencyDAOException {
        LOGGER.debug("start setMoney user by ID");

        if (id > 0 && money >= 0) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = null;
            PreparedStatement ps = null;
            try {
                connection = connectionPool.takeConnection();
                connection.setAutoCommit(false);
                ps = connection.prepareStatement(SQL_UPDATE_USER_MONEY_BY_ID);
                ps.setDouble(1, money);
                ps.setInt(2, id);
                ps.executeUpdate();
                connection.commit();
            } catch (TravelAgencyConnectionPoolException e) {
                LOGGER.error("user setMoney exception ConnectionPool ", e);
                throw new TravelAgencyDAOException("user setMoney exception ", e.getCause());
            } catch (SQLException e) {
                LOGGER.error("user setMoney exception SQLException ", e);
                throw new TravelAgencyDAOException("user setMoney exception ", e.getCause());
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        LOGGER.error("database access error occurs", e);
                        throw new TravelAgencyDAOException("database access error occurs", e);
                    }
                }
                if (connectionPool != null) {
                    connectionPool.releaseConnection(connection);
                }
                LOGGER.debug("finish setMoney user by ID");
            }
        }
    }

    /**
     * Add user to the database
     *
     * @param entity
     * @throws TravelAgencyDAOException
     */
    @Override
    public void create(Entity entity) throws TravelAgencyDAOException {
        LOGGER.debug("start user registration");

        if (entity instanceof User) {
            User user = (User) entity;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = null;
            PreparedStatement ps = null;
            try {
                connection = connectionPool.takeConnection();
                connection.setAutoCommit(false);
                ps = connection.prepareStatement(SQL_INSERT_USER);
                ps.setString(1, user.getName());
                ps.setString(2, user.getSurname());
                ps.setDouble(3, user.getDiscount());
                ps.setDouble(4, user.getMoney());
                ps.setString(5, user.getEmail());
                ps.setString(6, user.getLogin());
                ps.setString(7, user.getPassword());
                ps.setInt(8, user.getRole().getId());
                ps.executeUpdate();
                connection.commit();
            } catch (TravelAgencyConnectionPoolException | SQLException e) {
                LOGGER.error("user registration exception ", e);
                throw new TravelAgencyDAOException("user registration exception ", e);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        LOGGER.error("database access error occurs", e);
                        throw new TravelAgencyDAOException("database access error occurs", e);
                    }
                }
                if (connectionPool != null) {
                    connectionPool.releaseConnection(connection);
                }
                LOGGER.debug("finish user registration");
            }
        } else {
            throw new TravelAgencyDAOException("entity in method parameter is not instance of user");
        }
    }

    @Override
    public void update(Entity entity) throws TravelAgencyDAOException {
        LOGGER.debug("start update user by ID");

        if (entity instanceof User) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = null;
            PreparedStatement ps = null;
            User user = (User) entity;
            try {
                connection = connectionPool.takeConnection();
                connection.setAutoCommit(false);
                ps = connection.prepareStatement(SQL_UPDATE_USER_BY_ID);
                ps.setString(1, user.getName());
                ps.setString(2, user.getSurname());
                ps.setDouble(3, user.getDiscount());
                ps.setDouble(4, user.getMoney());
                ps.setString(5, user.getEmail());
                ps.setString(6, user.getLogin());
                ps.setString(7, user.getPassword());
                ps.setInt(8, user.getRole().getId());
                ps.setInt(9, user.getId());
                ps.executeUpdate();
                connection.commit();
            } catch (TravelAgencyConnectionPoolException | SQLException e) {
                LOGGER.error("user update exception ", e);
                throw new TravelAgencyDAOException("user update exception ", e);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        LOGGER.error("database access error occurs", e);
                        throw new TravelAgencyDAOException("database access error occurs", e);
                    }
                }
                if (connectionPool != null) {
                    connectionPool.releaseConnection(connection);
                }
                LOGGER.debug("finish update user by ID");
            }
        } else {
            throw new TravelAgencyDAOException("entity in method parameter is not instance of user");
        }
    }

    @Override
    public void delete(int id) throws TravelAgencyDAOException {
        LOGGER.debug("start delete user by ID");

        if(id > 0) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = null;
            PreparedStatement ps = null;
            try {
                connection = connectionPool.takeConnection();
                connection.setAutoCommit(false);
                ps = connection.prepareStatement(SQL_DELETE_USER_BY_ID);
                ps.setInt(1, id);
                ps.executeUpdate();
                connection.commit();
            } catch (TravelAgencyConnectionPoolException | SQLException e) {
                LOGGER.error("user delete by ID exception ", e);
                throw new TravelAgencyDAOException("user delete by ID exception ", e);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        LOGGER.error("database access error occurs", e);
                        throw new TravelAgencyDAOException("database access error occurs", e);
                    }
                }
                if (connectionPool != null) {
                    connectionPool.releaseConnection(connection);
                }
                LOGGER.debug("finish delete user by ID");
            }
        }
    }

    @Override
    public Entity findById(int id) throws TravelAgencyDAOException {
        LOGGER.debug("start find user by ID");
        User user = null;

        if (id > 0) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = null;
            ResultSet rs = null;
            PreparedStatement ps = null;
            try {
                connection = connectionPool.takeConnection();
                ps = connection.prepareStatement(SQL_SELECT_USER_BY_ID);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                while (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt(1));
                    user.setName(rs.getString(2));
                    user.setSurname(rs.getString(3));
                    user.setDiscount(rs.getDouble(4));
                    user.setMoney(rs.getDouble(5));
                    user.setEmail(rs.getString(6));
                    user.setLogin(rs.getString(7));
                    user.setPassword(rs.getString(8));
                    user.setRole(RoleType.getValue(rs.getInt(9)));
                }
            } catch (TravelAgencyConnectionPoolException | SQLException | TravelAgencyDataWrongException e) {
                LOGGER.error("user find by ID exception ", e);
                throw new TravelAgencyDAOException("user find by ID exception ", e);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        LOGGER.error("database access error occurs", e);
                        throw new TravelAgencyDAOException("database access error occurs", e);
                    }
                }
                if (connectionPool != null) {
                    connectionPool.releaseConnection(connection);
                }
                LOGGER.debug("finish find user by ID");
            }
        }
        return user;
    }

    @Override
    public List<Entity> findAll() throws TravelAgencyDAOException {
        LOGGER.debug("start find all users");

        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        User user = null;
        List<Entity> users= new ArrayList<>();;
        try {
            connection = connectionPool.takeConnection();
            ps = connection.prepareStatement(SQL_SELECT_ALL_USER);
            rs = ps.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setSurname(rs.getString(3));
                user.setDiscount(rs.getDouble(4));
                user.setMoney(rs.getDouble(5));
                user.setEmail(rs.getString(6));
                user.setLogin(rs.getString(7));
                user.setPassword(rs.getString(8));
                user.setRole(RoleType.getValue(rs.getInt(9)));
                users.add(user);
            }
        } catch (TravelAgencyConnectionPoolException | SQLException | TravelAgencyDataWrongException e) {
            LOGGER.error("find all users exception ", e);
            throw new TravelAgencyDAOException("find all users exception ", e);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    LOGGER.error("database access error occurs", e);
                    throw new TravelAgencyDAOException("database access error occurs", e);
                }
            }
            if (connectionPool != null) {
                connectionPool.releaseConnection(connection);
            }
            LOGGER.debug("finish find all users");
        }
        return users;
    }

}
