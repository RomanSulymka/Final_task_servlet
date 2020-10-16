package com.travel_agency.dao.sql;


import com.travel_agency.connection.ConnectionPool;
import com.travel_agency.dao.VaucherDao;
import com.travel_agency.entity.*;
import com.travel_agency.exceptions.TravelAgencyDataWrongException;
import com.travel_agency.exceptions.technicalexception.TravelAgencyConnectionPoolException;
import com.travel_agency.exceptions.technicalexception.TravelAgencyDAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VaucherDaoImplSql implements VaucherDao {

    private static final String SQL_SELECT_VAUCHER_BY_COUNTRY
            = "SELECT idvaucher, country, dateFrom, dateTo,\n" +
            "tour.idtour, tour.type, tour.price, tour.hot, \n" +
            "hotel.idhotel, hotel.name_hotel, hotel.pricePerDay, \n" +
            "transport.transport_type\n" +
            "FROM vaucher \n" +
            "INNER JOIN tour ON vaucher.tour_idtour = tour.idtour \n" +
            "INNER JOIN hotel ON vaucher.hotel_idhotel = hotel.idhotel\n" +
            "INNER JOIN transport ON vaucher.transport_idtransport = transport.idtransport \n" +
            "WHERE country = ?;";
    private static final String SQL_SELECT_VAUCHER_BY_TOUR_TYPE
            = "SELECT idvaucher, country, dateFrom, dateTo,\n" +
            "tour.idtour, tour.type, tour.price, tour.hot, \n" +
            "hotel.idhotel, hotel.name_hotel, hotel.pricePerDay, \n" +
            "transport.transport_type\n" +
            "FROM vaucher \n" +
            "INNER JOIN tour ON vaucher.tour_idtour = tour.idtour \n" +
            "INNER JOIN hotel ON vaucher.hotel_idhotel = hotel.idhotel\n" +
            "INNER JOIN transport ON vaucher.transport_idtransport = transport.idtransport \n" +
            "WHERE tour.type = ?;";
    private static final String SQL_INSERT_VAUCHER
            = "INSERT INTO vaucher (country, dateFrom, dateTo, tour_idtour, transport_idtransport, hotel_idhotel) VALUES (?,?,?,?,?,?);";
    private static final String SQL_UPDATE_VAUCHER_BY_ID
            = "UPDATE vaucher SET country=?, dateFrom=?, dateTo=?, tour_idtour=?, transport_idtransport=?, hotel_idhotel=? WHERE idvaucher=?;";
    private static final String SQL_DELETE_VAUCHER_BY_ID = "DELETE FROM vaucher WHERE idvaucher=?;";
    private static final String SQL_SELECT_VAUCHER_BY_ID
            = "SELECT idvaucher, country, dateFrom, dateTo,\n" +
            "tour.idtour, tour.type, tour.price, tour.hot, \n" +
            "hotel.idhotel, hotel.name_hotel, hotel.pricePerDay, \n" +
            "transport.transport_type\n" +
            "FROM vaucher \n" +
            "INNER JOIN tour ON vaucher.tour_idtour = tour.idtour \n" +
            "INNER JOIN hotel ON vaucher.hotel_idhotel = hotel.idhotel\n" +
            "INNER JOIN transport ON vaucher.transport_idtransport = transport.idtransport \n" +
            "WHERE idvaucher = ?;";
    private static final String SQL_SELECT_ALL_VAUCHER
            = "SELECT idvaucher, country, dateFrom, dateTo,\n" +
            "tour.idtour, tour.type, tour.price, tour.hot, \n" +
            "hotel.idhotel, hotel.name_hotel, hotel.pricePerDay, \n" +
            "transport.transport_type\n" +
            "FROM vaucher \n" +
            "INNER JOIN tour ON vaucher.tour_idtour = tour.idtour \n" +
            "INNER JOIN hotel ON vaucher.hotel_idhotel = hotel.idhotel\n" +
            "INNER JOIN transport ON vaucher.transport_idtransport = transport.idtransport;";

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getRootLogger();
    }


    @Override
    public List<Vaucher> getVauchersByCountry(String country) throws TravelAgencyDAOException {
        LOGGER.debug("start find vauchers by country");
        List<Vaucher> vauchers = new ArrayList<>();

        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.takeConnection();
            ps = connection.prepareStatement(SQL_SELECT_VAUCHER_BY_COUNTRY);
            ps.setString(1, country);
            rs = ps.executeQuery();
            while (rs.next()) {

                Vaucher vaucher = new Vaucher();
                vaucher.setId(rs.getInt(1));
                vaucher.setCountry(rs.getString(2));
                vaucher.setDateFrom(rs.getDate(3));
                vaucher.setDateTo(rs.getDate(4));

                Tour tour = new Tour();
                tour.setId(rs.getInt(5));
                tour.setType(rs.getString(6));
                tour.setPrice(rs.getDouble(7));
                tour.setHot(rs.getBoolean(8));

                Hotel hotel = new Hotel();
                hotel.setId(rs.getInt(9));
                hotel.setName(rs.getString(10));
                hotel.setPricePerDay(rs.getDouble(11));

                vaucher.setTransport(TransportType.valueOf(rs.getString(12)));

                vaucher.setTour(tour);
                vaucher.setHotel(hotel);
                vauchers.add(vaucher);
            }
        } catch (TravelAgencyConnectionPoolException | SQLException | TravelAgencyDataWrongException e) {
            LOGGER.error("find vauchers by country exception ", e);
            throw new TravelAgencyDAOException("find vauchers by country exception", e);
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
            LOGGER.debug("finish find vauchers by country");
        }
        return vauchers;
    }

    @Override
    public List<Vaucher> getVauchersByTourType(String type) throws TravelAgencyDAOException {
        LOGGER.debug("start find vauchers by TourType");
        List<Vaucher> vauchers = new ArrayList<>();

        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.takeConnection();
            ps = connection.prepareStatement(SQL_SELECT_VAUCHER_BY_TOUR_TYPE);
            ps.setString(1, type);
            rs = ps.executeQuery();
            while (rs.next()) {

                Vaucher vaucher = new Vaucher();
                vaucher.setId(rs.getInt(1));
                vaucher.setCountry(rs.getString(2));
                vaucher.setDateFrom(rs.getDate(3));
                vaucher.setDateTo(rs.getDate(4));

                Tour tour = new Tour();
                tour.setId(rs.getInt(5));
                tour.setType(rs.getString(6));
                tour.setPrice(rs.getDouble(7));
                tour.setHot(rs.getBoolean(8));

                Hotel hotel = new Hotel();
                hotel.setId(rs.getInt(9));
                hotel.setName(rs.getString(10));
                hotel.setPricePerDay(rs.getDouble(11));

                vaucher.setTransport(TransportType.valueOf(rs.getString(12)));

                vaucher.setTour(tour);
                vaucher.setHotel(hotel);
                vauchers.add(vaucher);
            }
        } catch (TravelAgencyConnectionPoolException | SQLException | TravelAgencyDataWrongException e) {
            LOGGER.error("find vauchers by TourType exception ", e);
            throw new TravelAgencyDAOException("find vauchers by TourType exception", e);
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
            LOGGER.debug("finish find vauchers by TourType");
        }
        return vauchers;
    }

    @Override
    public void create(Entity entity) throws TravelAgencyDAOException {
        LOGGER.debug("start vaucher registration");

        if (entity instanceof Vaucher) {
            Vaucher vaucher = (Vaucher) entity;
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = null;
            PreparedStatement ps = null;
            try {
                connection = connectionPool.takeConnection();
                connection.setAutoCommit(false);
                ps = connection.prepareStatement(SQL_INSERT_VAUCHER);
                ps.setString(1, vaucher.getCountry());
                ps.setString(2, dateFormat.format(vaucher.getDateFrom()));
                ps.setString(3, dateFormat.format(vaucher.getDateTo()));
                ps.setInt(4, vaucher.getTour().getId());
                ps.setInt(5, vaucher.getTransport().getId());
                ps.setInt(6, vaucher.getHotel().getId());
                ps.executeUpdate();
                connection.commit();
            } catch (TravelAgencyConnectionPoolException | SQLException e) {
                LOGGER.error("vaucher registration exception ", e);
                throw new TravelAgencyDAOException("vaucher registration exception", e);
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
                LOGGER.debug("finish vaucher registration");
            }
        } else {
            throw new TravelAgencyDAOException("entity in method parameter is not instance of vaucher");
        }
    }

    @Override
    public void update(Entity entity) throws TravelAgencyDAOException {
        LOGGER.debug("start update vaucher by ID");

        if (entity instanceof Vaucher) {
            Vaucher vaucher = (Vaucher) entity;
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = null;
            PreparedStatement ps = null;
            try {
                connection = connectionPool.takeConnection();
                connection.setAutoCommit(false);
                ps = connection.prepareStatement(SQL_UPDATE_VAUCHER_BY_ID);
                ps.setString(1, vaucher.getCountry());
                ps.setString(2, dateFormat.format(vaucher.getDateFrom()));
                ps.setString(3, dateFormat.format(vaucher.getDateTo()));
                ps.setInt(4, vaucher.getTour().getId());
                ps.setInt(5, vaucher.getTransport().getId());
                ps.setInt(6, vaucher.getHotel().getId());
                ps.setInt(7, vaucher.getId());
                ps.executeUpdate();
                connection.commit();
            } catch (TravelAgencyConnectionPoolException | SQLException e) {
                LOGGER.error("vaucher update exception ", e);
                throw new TravelAgencyDAOException("vaucher update exception", e);
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
                LOGGER.debug("finish update vaucher by ID");
            }
        } else {
            throw new TravelAgencyDAOException("entity in method parameter is not instance of vaucher");
        }
    }

    @Override
    public void delete(int id) throws TravelAgencyDAOException {
        LOGGER.debug("start delete vaucher by ID");

        if(id > 0) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = null;
            PreparedStatement ps = null;
            try {
                connection = connectionPool.takeConnection();
                connection.setAutoCommit(false);
                ps = connection.prepareStatement(SQL_DELETE_VAUCHER_BY_ID);
                ps.setInt(1, id);
                ps.executeUpdate();
                connection.commit();
            } catch (TravelAgencyConnectionPoolException | SQLException e) {
                LOGGER.error("vaucher delete by ID exception ", e);
                throw new TravelAgencyDAOException("vaucher delete by ID exception", e);
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
                LOGGER.debug("finish delete vaucher by ID");
            }
        }
    }

    @Override
    public Entity findById(int id) throws TravelAgencyDAOException {
        LOGGER.debug("start find vaucher by ID: " + id);
        Vaucher vaucher = null;

        if (id > 0) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = null;
            ResultSet rs = null;
            PreparedStatement ps = null;
            try {
                connection = connectionPool.takeConnection();
                ps = connection.prepareStatement(SQL_SELECT_VAUCHER_BY_ID);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                while (rs.next()) {

                    vaucher = new Vaucher();
                    vaucher.setId(rs.getInt(1));
                    vaucher.setCountry(rs.getString(2));
                    vaucher.setDateFrom(rs.getDate(3));
                    vaucher.setDateTo(rs.getDate(4));

                    Tour tour = new Tour();
                    tour.setId(rs.getInt(5));
                    tour.setType(rs.getString(6));
                    tour.setPrice(rs.getDouble(7));
                    tour.setHot(rs.getBoolean(8));

                    Hotel hotel = new Hotel();
                    hotel.setId(rs.getInt(9));
                    hotel.setName(rs.getString(10));
                    hotel.setPricePerDay(rs.getDouble(11));

                    vaucher.setTransport(TransportType.valueOf(rs.getString(12)));

                    vaucher.setTour(tour);
                    vaucher.setHotel(hotel);
                }
            } catch (TravelAgencyConnectionPoolException | SQLException | TravelAgencyDataWrongException e) {
                LOGGER.error("vaucher find by ID exception ", e);
                throw new TravelAgencyDAOException("vaucher find by ID exception", e);
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
                LOGGER.debug("finish find vaucher by ID: " + vaucher);
            }
        }
        return vaucher;
    }

    @Override
    public List<Entity> findAll() throws TravelAgencyDAOException {
        LOGGER.debug("start find all vauchers");
        List<Entity> vauchers = new ArrayList<>();

            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = null;
            ResultSet rs = null;
            PreparedStatement ps = null;
            try {
                connection = connectionPool.takeConnection();
                ps = connection.prepareStatement(SQL_SELECT_ALL_VAUCHER);
                rs = ps.executeQuery();
                while (rs.next()) {

                    Vaucher vaucher = new Vaucher();
                    vaucher.setId(rs.getInt(1));
                    vaucher.setCountry(rs.getString(2));
                    vaucher.setDateFrom(rs.getDate(3));
                    vaucher.setDateTo(rs.getDate(4));

                    Tour tour = new Tour();
                    tour.setId(rs.getInt(5));
                    tour.setType(rs.getString(6));
                    tour.setPrice(rs.getDouble(7));
                    tour.setHot(rs.getBoolean(8));

                    Hotel hotel = new Hotel();
                    hotel.setId(rs.getInt(9));
                    hotel.setName(rs.getString(10));
                    hotel.setPricePerDay(rs.getDouble(11));

                    vaucher.setTransport(TransportType.valueOf(rs.getString(12)));

                    vaucher.setTour(tour);
                    vaucher.setHotel(hotel);
                    vauchers.add(vaucher);
                }
            } catch (TravelAgencyConnectionPoolException | SQLException | TravelAgencyDataWrongException e) {
                LOGGER.error("find all vauchers exception ", e);
                throw new TravelAgencyDAOException("find all vauchers exception", e);
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
                LOGGER.debug("finish find all vauchers");
            }
        return vauchers;
    }
}
