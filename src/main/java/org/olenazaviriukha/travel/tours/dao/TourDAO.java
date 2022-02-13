package org.olenazaviriukha.travel.tours.dao;

import org.olenazaviriukha.travel.dao.DuplicateKeyException;
import org.olenazaviriukha.travel.common.db.DataSource;
import org.olenazaviriukha.travel.hotels.dao.HotelDAO;
import org.olenazaviriukha.travel.hotels.entity.Hotel;
import org.olenazaviriukha.travel.tours.entity.Tour;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TourDAO {
    private static final String SQL_GET_ALL_TOURS = "SELECT tour.id, tour.name, tour.tour_type, " +
            "tour.guests_number, tour.hotel_id, hotel.name as hotel_name, hotel.hotel_type, tour.guests_number,  tour.start_day, " +
            "tour.end_day, tour.price, tour.max_discount, tour.discount_step, tour.hot, " +
            "tour.description " +
            "FROM tour LEFT JOIN hotel ON tour.hotel_id = hotel.id";
    //    private static final String SQL_GET_HOTEL_BY_NAME = SQL_GET_ALL_HOTELS + " WHERE LOWER(hotels.name)=LOWER(?)";
    private static final String SQL_GET_TOUR_BY_ID = SQL_GET_ALL_TOURS + " WHERE tour.id=?";
    //
    private static final String SQL_INSERT_TOUR = "INSERT INTO tour" + "(name, tour_type,  hotel_id, guests_number, start_day, end_day, price, max_discount, discount_step," + "hot, description) " + "VALUES " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_TOUR = "UPDATE tour " +
            "SET name=?, tour_type=?,  hotel_id=?, guests_number=?, start_day=?, end_day=?, " +
            "price=?, max_discount=?, discount_step=?, hot=?, description=? " +
            "WHERE id=?";
    private static final String SQL_TYPE_CONDITION = " tour_type=?";
    private static final String SQL_PRICE_CONDITION = " price BETWEEN ? AND ?";
    private static final String SQL_GUESTS_NUMBER_CONDITION = " guests_number BETWEEN ? AND ?";
    private static final String SQL_HOTEL_TYPE_CONDITION = " hotel_type=?";

    private static final String FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_TOUR_TYPE = "tour_type";
    private static final String FIELD_GUESTS_NUMBER = "guests_number";
    private static final String FIELD_HOTEL_NAME = "hotel_name";
    private static final String FIELD_HOTEL_ID = "hotel_id";
    private static final String FIELD_HOTEL_TYPE = "hotel_type";
    private static final String FIELD_START_DATE = "start_day";
    private static final String FIELD_END_DATE = "end_day";
    private static final String FIELD_PRICE = "price";
    private static final String FIELD_MAX_DISCOUNT = "max_discount";
    private static final String FIELD_DISCOUNT_STEP = "discount_step";
    private static final String FIELD_HOT = "hot";
    private static final String FIELD_DESCRIPTION = "description";



    public static Tour getTourById(Integer id) {

        Tour tour = null;
        try (Connection con = DataSource.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_GET_TOUR_BY_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) tour = getTourFromResultSet(rs);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return tour;
    }

    public static List<Tour> getAllTours() {
        List<Tour> tours = new ArrayList<>();

        try (Connection con = DataSource.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_TOURS); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                tours.add(getTourFromResultSet(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tours;
    }

    public static List<Tour> getToursByTypePriceGuestsHotelType() {
        List<Tour> tours = new ArrayList<>();
        return tours;
    }

    public static int createTour(Tour tour) {
        int result = 0;

        try (Connection con = DataSource.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT_TOUR)) {

            preparedStatement.setString(1, tour.getName());
            preparedStatement.setString(2, String.valueOf(tour.getTourType()));
            preparedStatement.setObject(3, tour.getHotelId());
            preparedStatement.setInt(4, tour.getGuestsNumber());
            preparedStatement.setDate(5, Date.valueOf(tour.getStartDay()));
            preparedStatement.setDate(6, Date.valueOf(tour.getEndDay()));
            preparedStatement.setBigDecimal(7, tour.getPrice());
            preparedStatement.setInt(8, tour.getMaxDiscount());
            preparedStatement.setInt(9, tour.getDiscountStep());
            preparedStatement.setBoolean(10, tour.getHot());
            preparedStatement.setString(11, tour.getDescription());

            result = preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private static Tour getTourFromResultSet(ResultSet rs) {
        Tour tour;
        try {
            tour = new Tour();
            tour.setId(rs.getInt(FIELD_ID));
            tour.setName(rs.getString(FIELD_NAME));
            tour.setTourType(Tour.TourType.valueOf(rs.getString(FIELD_TOUR_TYPE).toUpperCase()));

            Hotel hotel = new Hotel();
            hotel.setId(rs.getInt(FIELD_HOTEL_ID));
            hotel.setName(rs.getString(FIELD_HOTEL_NAME));
            hotel.setHotelType(rs.getInt(FIELD_HOTEL_TYPE));
            tour.setHotel(hotel);

            tour.setGuestsNumber(rs.getInt(FIELD_GUESTS_NUMBER));
            tour.setStartDay(rs.getDate(FIELD_START_DATE).toLocalDate());
            tour.setEndDay(rs.getDate(FIELD_END_DATE).toLocalDate());
            tour.setPrice(rs.getBigDecimal(FIELD_PRICE));
            tour.setMaxDiscount(rs.getInt(FIELD_MAX_DISCOUNT));
            tour.setDiscountStep(rs.getInt(FIELD_DISCOUNT_STEP));
            tour.setHot(rs.getBoolean(FIELD_HOT));
            tour.setDescription(rs.getString(FIELD_DESCRIPTION));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return tour;
    }

    public static int updateTour(Tour tour) throws DuplicateKeyException {
        int rowsAffected = 0;

        try (Connection con = DataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_UPDATE_TOUR)) {

            ps.setString(1, tour.getName());
            ps.setString(2, String.valueOf(tour.getTourType()));
            ps.setInt(2, tour.getHotelId());
            ps.setObject(3, tour.getHotelId());
            ps.setInt(4, tour.getGuestsNumber());
            ps.setDate(5, Date.valueOf(tour.getStartDay()));
            ps.setDate(6, Date.valueOf(tour.getEndDay()));
            ps.setBigDecimal(7, tour.getPrice());
            ps.setInt(8, tour.getMaxDiscount());
            ps.setInt(9, tour.getDiscountStep());
            ps.setBoolean(10, tour.getHot());
            ps.setString(11, tour.getDescription());
            ps.setInt(12, tour.getId());

            rowsAffected = ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateKeyException("name", "Tour with provided characteristics currently exists");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;

    }
}
