package org.olenazaviriukha.travel.orederedTours.dao;

import org.olenazaviriukha.travel.common.db.DataSource;
import org.olenazaviriukha.travel.common.exceptions.DuplicateKeyException;
import org.olenazaviriukha.travel.common.utils.WhereClauseJoiner;
import org.olenazaviriukha.travel.hotels.entity.Hotel;
import org.olenazaviriukha.travel.orederedTours.entity.OrderedTour;
import org.olenazaviriukha.travel.tours.dao.TourDAO;
import org.olenazaviriukha.travel.tours.entity.Tour;
import org.olenazaviriukha.travel.users.dao.UserDAO;
import org.olenazaviriukha.travel.users.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderedTourDAO {
    private static final String FIELD_ORDERED_TOUR_COUNT = "ordered_tour_count";
    private static final String FIELD_ID = "id";
    private static final String FIELD_TOUR_ID = "tour_id";
    private static final String FIELD_STATUS = "status";
    private static final String FIELD_DISCOUNT = "discount";
    private static final String FIELD_FIXED_PRICE = "fixed_price";
    private static final String FIELD_TOUR_NAME = "tour_name";
    private static final String FIELD_HOTEL_ID = "hotel_id";
    private static final String FIELD_TOUR_TYPE = "tour_type";
    private static final String FIELD_TOUR_GUESTS_NUM = "guests_number";
    private static final String FIELD_TOUR_MAX_DISCOUNT = "max_discount";
    private static final String FIELD_TOUR_DISCOUNT_STEP = "discount_step";
    private static final String FIELD_START_DAY = "start_day";
    private static final String FIELD_END_DAY = "end_day";
    private static final String FIELD_TOUR_PRICE = "price";
    private static final String FIELD_TOUR_IS_HOT = "hot";
    private static final String FIELD_HOTEL_NAME = "hotel_name";
    private static final String FIELD_HOTEL_TYPE = "hotel_type";
    private static final String FIELD_USER_ID = "user_id";
    private static final String FIELD_USER_FIRST_NAME = "first_name";
    private static final String FIELD_USER_LAST_NAME = "last_name";
    private static final String FIELD_USER_EMAIL = "email";
//    private static final String FIELD_ANNOTATION = "annotation";

    private static final String SQL_SELECT_ORDERED_TOURS =
            "SELECT " +
                    "ot.id, ot.tour_id, ot.status, ot.discount, ot.fixed_price, ot.user_id, ot.tour_id, " +
                    "t.name as tour_name, t.tour_type, t.hotel_id, t.guests_number, t.start_day, t.end_day, t.price, t.hot, " +
                    "t.max_discount, t.discount_step, " +
                    "h.name as hotel_name, h.hotel_type, ot.discount, ot.fixed_price, " +
                    "u.first_name, u.last_name, u.email " +
                    "FROM ordered_tour ot " +
                    "JOIN user u on u.id = ot.user_id " +
                    "JOIN tour t ON t.id = ot.tour_id " +
                    "LEFT JOIN hotel h ON h.id = t.hotel_id ";

    private static final String SQL_GET_ORDERED_TOUR_BY_ID = SQL_SELECT_ORDERED_TOURS + " WHERE ot.id=? ";
    private static final String SQL_CREATE_ORDERED_TOUR =
            "INSERT INTO ordered_tour (tour_id, user_id, fixed_price, status) VALUES (?, ?, ?, 'REGISTERED')";

    private static final String SQL_SELECT_COUNT_ORDERED_TOURS =
            "SELECT count(id) as ordered_tour_count FROM ordered_tour WHERE status in ('REGISTERED', 'PAID')";

    private static final String SQL_SELECT_ORDERED_TOURS_BY_USER = SQL_SELECT_ORDERED_TOURS + " WHERE ot.user_id=? ";
    private static final String ORDER_BY_START_DAY = " ORDER BY start_day ASC ";

    private static final String SQL_UPDATE_ORDERED_TOUR = "UPDATE ordered_tour ot SET status=?, discount=? WHERE ot.id=?";

    public static int createOrderedTour(OrderedTour orderedTour) {
        int result = 0;

        try (Connection con = DataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_CREATE_ORDERED_TOUR)) {

            pst.setInt(1, orderedTour.getTourId());
            pst.setInt(2, orderedTour.getUserId());
            pst.setBigDecimal(3, orderedTour.getFixedPrice());

            result = pst.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static List<OrderedTour> getUserOrderedTours(User user) {

        List<OrderedTour> orderedTours = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_SELECT_ORDERED_TOURS_BY_USER + ORDER_BY_START_DAY)) {

            pst.setInt(1, user.getId());

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                orderedTours.add(getObjFromResultSet(rs));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return orderedTours;
    }

    private static OrderedTour getObjFromResultSet(ResultSet rs) throws SQLException {
        Hotel hotel = null;
        Integer hotelId = (Integer) rs.getObject(FIELD_HOTEL_ID);

        if (hotelId != null) {
            hotel = new Hotel();
            hotel.setId(hotelId);
            hotel.setHotelType(rs.getInt(FIELD_HOTEL_TYPE));
            hotel.setName(rs.getString(FIELD_HOTEL_NAME));
        }
        Tour tour = new Tour();
        tour.setId(rs.getInt(FIELD_TOUR_ID));
        tour.setName(rs.getString(FIELD_TOUR_NAME));
        tour.setTourType(Tour.TourType.valueOf(rs.getString(FIELD_TOUR_TYPE).toUpperCase()));
        tour.setHotel(hotel);
        tour.setGuestsNumber(rs.getInt(FIELD_TOUR_GUESTS_NUM));
        tour.setStartDay(rs.getDate(FIELD_START_DAY).toLocalDate());
        tour.setEndDay(rs.getDate(FIELD_END_DAY).toLocalDate());
        tour.setPrice(rs.getBigDecimal(FIELD_TOUR_PRICE));
        tour.setHot(rs.getBoolean(FIELD_TOUR_IS_HOT));
        tour.setDiscountStep(rs.getInt(FIELD_TOUR_DISCOUNT_STEP));
        tour.setMaxDiscount(rs.getInt(FIELD_TOUR_MAX_DISCOUNT));

        User user = new User();
        user.setId(rs.getInt(FIELD_USER_ID));
        user.setFirstName(rs.getString(FIELD_USER_FIRST_NAME));
        user.setLastName(rs.getString(FIELD_USER_LAST_NAME));
        user.setEmail(rs.getString(FIELD_USER_EMAIL));

        OrderedTour obj = new OrderedTour();
        obj.setId(rs.getInt(FIELD_ID));
        obj.setDiscount(rs.getInt(FIELD_DISCOUNT));
        obj.setFixedPrice(rs.getBigDecimal(FIELD_FIXED_PRICE));
        obj.setStatus(OrderedTour.Status.valueOf(rs.getString(FIELD_STATUS).toUpperCase()));
        obj.setUser(user);
        obj.setTour(tour);
        return obj;
    }

    public static List<OrderedTour> getOrderedTours(int limit, int offset) {
        List<OrderedTour> orderedTours = new ArrayList<>();
        String limitString = (limit == 0) ? "" : " LIMIT " + limit + " ";
        String offsetString = (offset == 0) ? "" : " OFFSET " + offset + " ";
        try (Connection con = DataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_SELECT_ORDERED_TOURS + ORDER_BY_START_DAY +
                     limitString + offsetString)) {

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                orderedTours.add(getObjFromResultSet(rs));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return orderedTours;
    }

    public static int getFilteredCount() {
        return getFilteredCount(null);
    }

    public static int getFilteredCount(WhereClauseJoiner queryFilter) {
        int count = 0;
        String whereClause = queryFilter != null ? queryFilter.getWhereClause() : "";
        try (
                Connection con = DataSource.getConnection();
                PreparedStatement pst = con.prepareStatement(SQL_SELECT_COUNT_ORDERED_TOURS + whereClause);
        ) {
            if (queryFilter != null) queryFilter.fillPreparedStatement(pst);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                count = rs.getInt(FIELD_ORDERED_TOUR_COUNT);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return count;
    }

    public static OrderedTour getOrderedTourById(Integer id) {
        OrderedTour orderedTour = null;
        try (Connection con = DataSource.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_GET_ORDERED_TOUR_BY_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) orderedTour = getObjFromResultSet(rs);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return orderedTour;
    }

    public static int updateOrderedTour(OrderedTour orderedTour) {
        int rowsAffected = 0;

        try (Connection con = DataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_UPDATE_ORDERED_TOUR)) {

            ps.setString(1, orderedTour.getStatus().toString());
            ps.setInt(2, orderedTour.getDiscount());
            ps.setInt(3, orderedTour.getId());

            rowsAffected = ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }
}
