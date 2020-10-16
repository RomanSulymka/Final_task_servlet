package com.travel_agency.dao.sql;

import com.travel_agency.connection.ConnectionPool;
import com.travel_agency.dao.OrderDao;
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


public class OrderDaoImplSql implements OrderDao {

    private static final String SQL_INSERT_ORDER = "INSERT INTO order_history (vaucher_idvaucher, user_iduser, totalPrice) VALUES (?,?,?);";
    private static final String SQL_DELETE_ORDER_BY_ID = "DELETE FROM order_history WHERE idorder_history = ?;";
    private static final String SQL_SELECT_ORDER_BY_ID
            = "SELECT idorder_history, idvaucher, country, dateFrom, dateTo,\n" +
            "tour.idtour, tour.type, tour.price, tour.hot, \n" +
            "hotel.idhotel, hotel.name_hotel, hotel.pricePerDay, \n" +
            "transport.transport_type,\n" +
            "user.iduser, user.name, user.surname, \n" +
            "user.discount, user.money, user.email, user.login, user.password, user.role_idrole,\n" +
            "order_history.totalPrice\n" +
            "FROM order_history \n" +
            "INNER JOIN vaucher ON order_history.vaucher_idvaucher = vaucher.idvaucher\n" +
            "INNER JOIN user ON order_history.user_iduser = user.iduser\n" +
            "INNER JOIN tour ON vaucher.tour_idtour = tour.idtour \n" +
            "INNER JOIN hotel ON vaucher.hotel_idhotel = hotel.idhotel\n" +
            "INNER JOIN transport ON vaucher.transport_idtransport = transport.idtransport \n" +
            "WHERE idorder_history = ?;";

    private static final String SQL_SELECT_ORDER_BY_USER_ID
            = "SELECT idorder_history, idvaucher, country, dateFrom, dateTo,\n" +
            "tour.idtour, tour.type, tour.price, tour.hot,\n" +
            "hotel.idhotel, hotel.name_hotel, hotel.pricePerDay, \n" +
            "transport.transport_type,\n" +
            "user.iduser, user.name, user.surname, \n" +
            "user.discount, user.money, user.email, user.login, user.password, user.role_idrole,\n" +
            "order_history.totalPrice\n" +
            "FROM order_history \n" +
            "INNER JOIN vaucher ON order_history.vaucher_idvaucher = vaucher.idvaucher\n" +
            "INNER JOIN user ON order_history.user_iduser = user.iduser\n" +
            "INNER JOIN tour ON vaucher.tour_idtour = tour.idtour \n" +
            "INNER JOIN hotel ON vaucher.hotel_idhotel = hotel.idhotel\n" +
            "INNER JOIN transport ON vaucher.transport_idtransport = transport.idtransport \n" +
            "WHERE user_iduser = ?;";

    private static final String SQL_SELECT_ALL_ORDER
            = "SELECT idorder_history, idvaucher, country, dateFrom, dateTo,\n" +
            "tour.idtour, tour.type, tour.price, tour.hot, \n" +
            "hotel.idhotel, hotel.name_hotel, hotel.pricePerDay, \n" +
            "transport.transport_type,\n" +
            "user.iduser, user.name, user.surname, \n" +
            "user.discount, user.money, user.email, user.login, user.password, user.role_idrole,\n" +
            "order_history.totalPrice\n" +
            "FROM order_history \n" +
            "INNER JOIN vaucher ON order_history.vaucher_idvaucher = vaucher.idvaucher\n" +
            "INNER JOIN user ON order_history.user_iduser = user.iduser\n" +
            "INNER JOIN tour ON vaucher.tour_idtour = tour.idtour \n" +
            "INNER JOIN hotel ON vaucher.hotel_idhotel = hotel.idhotel\n" +
            "INNER JOIN transport ON vaucher.transport_idtransport = transport.idtransport;";

    private static final String SQL_UPDATE_USER_MONEY_BY_ID = "UPDATE user SET money=? WHERE iduser=?;";

    private static final String SQL_DELETE_VAUCHER_BY_ID = "DELETE FROM vaucher WHERE idvaucher=?;";

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getRootLogger();
    }

    @Override
    public void create(Entity entity) throws TravelAgencyDAOException {
        LOGGER.debug("start order registration");

        if (entity instanceof Order) {
            Order order = (Order) entity;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = null;
            PreparedStatement ps = null;
            PreparedStatement psUser = null;
            try {
                connection = connectionPool.takeConnection();
                connection.setAutoCommit(false);
                ps = connection.prepareStatement(SQL_INSERT_ORDER);
                ps.setInt(1, order.getVaucher().getId());
                ps.setInt(2, order.getUser().getId());
                order.setTotalPrice(calculateTotalPrice(order));
                ps.setDouble(3, order.getTotalPrice());
                ps.executeUpdate();

                psUser = connection.prepareStatement(SQL_UPDATE_USER_MONEY_BY_ID);
                psUser.setDouble(1, (order.getUser().getMoney() - order.getTotalPrice()));
                psUser.setInt(2, order.getUser().getId());
                psUser.executeUpdate();

                connection.commit();

            } catch (TravelAgencyConnectionPoolException | SQLException | TravelAgencyDataWrongException e) {
                LOGGER.error("order registration exception ", e);
                throw new TravelAgencyDAOException("order registration exception", e);
            } finally {
                if (ps != null || psUser != null) {
                    try {
                        ps.close();
                        psUser.close();
                    } catch (SQLException e) {
                        LOGGER.error("database access error occurs", e);
                        throw new TravelAgencyDAOException("database access error occurs", e);
                    }
                }
                if (connectionPool != null) {
                    connectionPool.releaseConnection(connection);
                }
                LOGGER.debug("finish order registration");
            }
        } else {
            throw new TravelAgencyDAOException("entity in method parameter is not instance of order");
        }
    }

    /**
     * Returns order total price. It depends on tour price and hotel price per day for the entire stay.
     *
     * @param order
     * @return
     */
    private double calculateTotalPrice(Order order) {
        int nights = (int)(order.getVaucher().getDateTo().getTime() - order.getVaucher().getDateFrom().getTime())/(24 * 60 * 60 * 1000);
        double totalPrice = (nights * order.getVaucher().getHotel().getPricePerDay() + order.getVaucher().getTour().getPrice()) * (100 - order.getUser().getDiscount())/100;
        return totalPrice;
    }

    @Override
    public void update(Entity entity) {
        throw new UnsupportedOperationException("Not implemented method");
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not implemented method");
    }

    @Override
    public void cancelOrder(Order order) throws TravelAgencyDAOException {
        LOGGER.debug("start delete order by ID");

        if(order != null) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = null;
            PreparedStatement ps = null;
            PreparedStatement psUser = null;
            try {
                connection = connectionPool.takeConnection();
                connection.setAutoCommit(false);
                ps = connection.prepareStatement(SQL_DELETE_ORDER_BY_ID);
                ps.setInt(1, order.getId());
                ps.executeUpdate();

                psUser = connection.prepareStatement(SQL_UPDATE_USER_MONEY_BY_ID);
                psUser.setDouble(1, (order.getUser().getMoney() + order.getTotalPrice()));
                psUser.setInt(2, order.getUser().getId());
                psUser.executeUpdate();

                connection.commit();
            } catch (TravelAgencyConnectionPoolException | SQLException e) {
                LOGGER.error("order delete by ID exception ", e);
                throw new TravelAgencyDAOException("order delete by ID exception", e);
            } finally {
                if (ps != null || psUser != null) {
                    try {
                        ps.close();
                        psUser.close();
                    } catch (SQLException e) {
                        LOGGER.error("database access error occurs", e);
                        throw new TravelAgencyDAOException("database access error occurs", e);
                    }
                }
                if (connectionPool != null) {
                    connectionPool.releaseConnection(connection);
                }
                LOGGER.debug("finish delete order by ID");
            }
        }
    }

    @Override
    public Entity findById(int id) throws TravelAgencyDAOException {
        LOGGER.debug("start find order by ID");
        Order order = null;

        if (id > 0) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = null;
            ResultSet rs = null;
            PreparedStatement ps = null;
            try {
                connection = connectionPool.takeConnection();
                ps = connection.prepareStatement(SQL_SELECT_ORDER_BY_ID);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                while (rs.next()) {
                    order = new Order();
                    order.setId(rs.getInt(1));

                    Vaucher vaucher = new Vaucher();
                    vaucher.setId(rs.getInt(2));
                    vaucher.setCountry(rs.getString(3));
                    vaucher.setDateFrom(rs.getDate(4));
                    vaucher.setDateTo(rs.getDate(5));

                    Tour tour = new Tour();
                    tour.setId(rs.getInt(6));
                    tour.setType(rs.getString(7));
                    tour.setPrice(rs.getDouble(8));
                    tour.setHot(rs.getBoolean(9));

                    Hotel hotel = new Hotel();
                    hotel.setId(rs.getInt(10));
                    hotel.setName(rs.getString(11));
                    hotel.setPricePerDay(rs.getDouble(12));

                    vaucher.setTransport(TransportType.valueOf(rs.getString(13)));

                    vaucher.setTour(tour);
                    vaucher.setHotel(hotel);

                    User user = new User();
                    user.setId(rs.getInt(14));
                    user.setName(rs.getString(15));
                    user.setSurname(rs.getString(16));
                    user.setDiscount(rs.getDouble(17));
                    user.setMoney(rs.getDouble(18));
                    user.setEmail(rs.getString(19));
                    user.setLogin(rs.getString(20));
                    user.setPassword(rs.getString(21));
                    user.setRole(RoleType.getValue(rs.getInt(22)));

                    order.setVaucher(vaucher);
                    order.setUser(user);
                    order.setTotalPrice(rs.getDouble(23));
                }
            } catch (TravelAgencyConnectionPoolException | SQLException | TravelAgencyDataWrongException e) {
                LOGGER.error("order find by ID exception ", e);
                throw new TravelAgencyDAOException("order find by ID exception", e);
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
                LOGGER.debug("finish find order by ID");
            }
        }
        return order;
    }

    @Override
    public List<Entity> findAll() throws TravelAgencyDAOException {
        LOGGER.debug("start find all vauchers");
        List<Entity> orders = new ArrayList<>();

        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.takeConnection();
            ps = connection.prepareStatement(SQL_SELECT_ALL_ORDER);
            rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt(1));

                Vaucher vaucher = new Vaucher();
                vaucher.setId(rs.getInt(2));
                vaucher.setCountry(rs.getString(3));
                vaucher.setDateFrom(rs.getDate(4));
                vaucher.setDateTo(rs.getDate(5));

                Tour tour = new Tour();
                tour.setId(rs.getInt(6));
                tour.setType(rs.getString(7));
                tour.setPrice(rs.getDouble(8));
                tour.setHot(rs.getBoolean(9));

                Hotel hotel = new Hotel();
                hotel.setId(rs.getInt(10));
                hotel.setName(rs.getString(11));
                hotel.setPricePerDay(rs.getDouble(12));

                vaucher.setTransport(TransportType.valueOf(rs.getString(13)));

                vaucher.setTour(tour);
                vaucher.setHotel(hotel);

                User user = new User();
                user.setId(rs.getInt(14));
                user.setName(rs.getString(15));
                user.setSurname(rs.getString(16));
                user.setDiscount(rs.getDouble(17));
                user.setMoney(rs.getDouble(18));
                user.setEmail(rs.getString(19));
                user.setLogin(rs.getString(20));
                user.setPassword(rs.getString(21));
                user.setRole(RoleType.getValue(rs.getInt(22)));

                order.setVaucher(vaucher);
                order.setUser(user);
                order.setTotalPrice(rs.getDouble(23));

                orders.add(order);
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
        return orders;
    }

    @Override
    public List<Entity> findByUserId(int userId) throws TravelAgencyDAOException {
        LOGGER.debug("start find order by userId");
        Order order = null;
        List<Entity> ordersByUserId = new ArrayList<>();

        if (userId > 0) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = null;
            ResultSet rs = null;
            PreparedStatement ps = null;
            try {
                connection = connectionPool.takeConnection();
                ps = connection.prepareStatement(SQL_SELECT_ORDER_BY_USER_ID);
                ps.setInt(1, userId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    order = new Order();
                    order.setId(rs.getInt(1));

                    Vaucher vaucher = new Vaucher();
                    vaucher.setId(rs.getInt(2));
                    vaucher.setCountry(rs.getString(3));
                    vaucher.setDateFrom(rs.getDate(4));
                    vaucher.setDateTo(rs.getDate(5));

                    Tour tour = new Tour();
                    tour.setId(rs.getInt(6));
                    tour.setType(rs.getString(7));
                    tour.setPrice(rs.getDouble(8));
                    tour.setHot(rs.getBoolean(9));

                    Hotel hotel = new Hotel();
                    hotel.setId(rs.getInt(10));
                    hotel.setName(rs.getString(11));
                    hotel.setPricePerDay(rs.getDouble(12));

                    vaucher.setTransport(TransportType.valueOf(rs.getString(13)));

                    vaucher.setTour(tour);
                    vaucher.setHotel(hotel);

                    User user = new User();
                    user.setId(rs.getInt(14));
                    user.setName(rs.getString(15));
                    user.setSurname(rs.getString(16));
                    user.setDiscount(rs.getDouble(17));
                    user.setMoney(rs.getDouble(18));
                    user.setEmail(rs.getString(19));
                    user.setLogin(rs.getString(20));
                    user.setPassword(rs.getString(21));
                    user.setRole(RoleType.getValue(rs.getInt(22)));

                    order.setVaucher(vaucher);
                    order.setUser(user);
                    order.setTotalPrice(rs.getDouble(23));

                    ordersByUserId.add(order);
                }
            } catch (TravelAgencyConnectionPoolException | SQLException | TravelAgencyDataWrongException e) {
                LOGGER.error("order find by userId exception ", e);
                throw new TravelAgencyDAOException("order find by userId exception", e);
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
                LOGGER.debug("finish find order by userId");
            }
        }
        return ordersByUserId;
    }

}
