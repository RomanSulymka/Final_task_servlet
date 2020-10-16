package com.travel_agency.dao.sql;

import com.travel_agency.connection.ConnectionPool;
import com.travel_agency.dao.TourDao;
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

public class TourDaoImplSql implements TourDao {

    private static final String SQL_UPDATE_TOUR_HOT_BY_ID = "UPDATE tour SET hot=? WHERE idtour=?;";
    private static final String SQL_INSERT_TOUR = "INSERT INTO tour (type, price, hot) VALUES (?,?,?);";
    private static final String SQL_UPDATE_TOUR ="UPDATE tour SET type=?, price=?, hot=? WHERE idtour=?;";
    private static final String SQL_DELETE_TOUR_BY_ID = "DELETE FROM tour WHERE idtour = ?;";
    private static final String SQL_SELECT_TOUR_BY_ID = "SELECT idtour, type, price, hot FROM tour WHERE idtour = ?;";
    private static final String SQL_SELECT_ALL_TOUR = "SELECT idtour, type, price, hot FROM tour;";

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getRootLogger();
    }

    @Override
    public void setHotTour(int id, boolean isHot) throws TravelAgencyDAOException {
        LOGGER.debug("start setHotTour tour by ID");

        if (id > 0) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = null;
            PreparedStatement ps = null;
            try {
                connection = connectionPool.takeConnection();
                connection.setAutoCommit(false);
                ps = connection.prepareStatement(SQL_UPDATE_TOUR_HOT_BY_ID);
                ps.setBoolean(1, isHot);
                ps.setInt(2, id);
                ps.executeUpdate();
                connection.commit();
            } catch (TravelAgencyConnectionPoolException | SQLException e) {
                LOGGER.error("setHotTour tour by ID exception ", e);
                throw new TravelAgencyDAOException("setHotTour tour by ID exception", e);
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
                LOGGER.debug("finish setHotTour tour by ID");
            }
        }
    }

    @Override
    public void create(Entity entity) throws TravelAgencyDAOException {
        LOGGER.debug("start tour registration");

        if (entity instanceof Tour) {
            Tour tour = (Tour) entity;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = null;
            PreparedStatement ps = null;
            try {
                connection = connectionPool.takeConnection();
                connection.setAutoCommit(false);
                ps = connection.prepareStatement(SQL_INSERT_TOUR);
                ps.setString(1, tour.getType());
                ps.setDouble(2, tour.getPrice());
                ps.setBoolean(3, tour.isHot());
                ps.executeUpdate();
                connection.commit();
            } catch (TravelAgencyConnectionPoolException | SQLException e) {
                LOGGER.error("user tour registration exception ", e);
                throw new TravelAgencyDAOException("user tour registration exception", e);
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
                LOGGER.debug("finish tour registration");
            }
        } else {
            throw new TravelAgencyDAOException("entity in method parameter is not instance of tour");
        }
    }

    @Override
    public void update(Entity entity) throws TravelAgencyDAOException {
        LOGGER.debug("start update tour by ID");

        if (entity instanceof Tour) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = null;
            PreparedStatement ps = null;
            Tour tour = (Tour) entity;
            try {
                connection = connectionPool.takeConnection();
                connection.setAutoCommit(false);
                ps = connection.prepareStatement(SQL_UPDATE_TOUR);
                ps.setString(1, tour.getType());
                ps.setDouble(2, tour.getPrice());
                ps.setBoolean(3, tour.isHot());
                ps.setInt(4, tour.getId());
                ps.executeUpdate();
                connection.commit();
            } catch (TravelAgencyConnectionPoolException | SQLException e) {
                LOGGER.error("tour update exception ", e);
                throw new TravelAgencyDAOException("tour update exception", e);
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
                LOGGER.debug("finish update tour by ID");
            }
        } else {
            throw new TravelAgencyDAOException("entity in method parameter is not instance of tour");
        }
    }

    @Override
    public void delete(int id) throws TravelAgencyDAOException {
        LOGGER.debug("start delete tour by ID");

        if(id > 0) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = null;
            PreparedStatement ps = null;
            try {
                connection = connectionPool.takeConnection();
                connection.setAutoCommit(false);
                ps = connection.prepareStatement(SQL_DELETE_TOUR_BY_ID);
                ps.setInt(1, id);
                ps.executeUpdate();
                connection.commit();
            } catch (TravelAgencyConnectionPoolException | SQLException e) {
                LOGGER.error("tour delete by ID exception ", e);
                throw new TravelAgencyDAOException("tour delete by ID exception", e);
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
                LOGGER.debug("finish delete tour by ID");
            }
        }
    }

    @Override
    public Entity findById(int id) throws TravelAgencyDAOException {
        LOGGER.debug("start find tour by ID");
        Tour tour = null;

        if (id > 0) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = null;
            ResultSet rs = null;
            PreparedStatement ps = null;
            try {
                connection = connectionPool.takeConnection();
                ps = connection.prepareStatement(SQL_SELECT_TOUR_BY_ID);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                while (rs.next()) {
                    tour = new Tour();
                    tour.setId(rs.getInt(1));
                    tour.setType(rs.getString(2));
                    tour.setPrice(rs.getDouble(3));
                    tour.setHot(rs.getBoolean(4));
                }
            } catch (TravelAgencyConnectionPoolException | SQLException | TravelAgencyDataWrongException e) {
                LOGGER.error("tour find by ID exception ", e);
                throw new TravelAgencyDAOException("tour find by ID exception", e);
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
                LOGGER.debug("finish find tour by ID");
            }
        }
        return tour;
    }

    @Override
    public List<Entity> findAll() throws TravelAgencyDAOException {
        LOGGER.debug("start find all tours");

        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        Tour tour = null;
        List<Entity> tours= new ArrayList<>();;
        try {
            connection = connectionPool.takeConnection();
            ps = connection.prepareStatement(SQL_SELECT_ALL_TOUR);
            rs = ps.executeQuery();
            while (rs.next()) {
                tour = new Tour();
                tour.setId(rs.getInt(1));
                tour.setType(rs.getString(2));
                tour.setPrice(rs.getDouble(3));
                tour.setHot(rs.getBoolean(4));
                tours.add(tour);
            }
        } catch (TravelAgencyConnectionPoolException | SQLException | TravelAgencyDataWrongException e) {
            LOGGER.error("find all tours exception ", e);
            throw new TravelAgencyDAOException("find all tours exception", e);
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
            LOGGER.debug("finish find all tours");
        }
        return tours;
    }
}
