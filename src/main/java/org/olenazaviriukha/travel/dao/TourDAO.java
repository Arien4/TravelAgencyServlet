package org.olenazaviriukha.travel.dao;

import org.olenazaviriukha.travel.db.DataSource;
import org.olenazaviriukha.travel.entity.Tour;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TourDAO {
    private static final String SQL_GET_ALL_TOURS = "SELECT \n" + "    tours.id, " + "    tours.name, " + "    tours.tour_type," + "    tours.guests_number," + "    hotels.name, " + "    hotels.hotel_type, " + "    tours.guests_number, " + "    tours.start_day, " + "    tours.end_day, " + "    tours.price, " + "    tours.max_discount, " + "    tours.discount_step, " + "    tours.hot, " + "    tours.description " + "FROM " + "    tours " + "        LEFT JOIN " + "    hotels ON tours.hotel_id = hotels.id";
    //    private static final String SQL_GET_HOTEL_BY_NAME = SQL_GET_ALL_HOTELS + " WHERE LOWER(hotels.name)=LOWER(?)";
    private static final String SQL_GET_TOUR_BY_ID = SQL_GET_ALL_TOURS + " WHERE tours.id=?";
    //
    private static final String SQL_INSERT_TOUR = "INSERT INTO tours" + "(name, tour_type,  hotel_id, guests_number, start_day, end_day, price, max_discount, discount_step," + "hot, description) " + "VALUES " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_TOUR_TYPE = "tour_type";
    private static final String FIELD_GUESTS_NUMBER = "guests_number";
    private static final String FIELD_HOTEL = "hotel";
    private static final String FIELD_START_DATE = "start_date";
    private static final String FIELD_END_DATE = "end_date";
    private static final String FIELD_PRICE = "price";
    private static final String FIELD_MAX_DISCOUNT = "max_discount";
    private static final String FIELD_DISCOUNT_STEP = "discount_step";
    private static final String FIELD_HOT = "hot";
    private static final String FIELD_DESCRIPTION = "description";


    public static Tour findTourById(Integer id) {

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
            tour.setTourType((Tour.TourType) rs.getObject(FIELD_TOUR_TYPE));
            tour.setGuestsNumber(rs.getInt(FIELD_GUESTS_NUMBER));
            //                       tour.setHotel(rs.getInt(FIELD_HOTEL_ID)); // як дістати id готелю?!
            tour.setDescription(rs.getString(FIELD_DESCRIPTION));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return tour;
    }
}
