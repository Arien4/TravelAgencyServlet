package org.olenazaviriukha.travel.dao;

import org.olenazaviriukha.travel.db.DataSource;
import org.olenazaviriukha.travel.entity.OrderedTour;
import org.olenazaviriukha.travel.entity.Tour;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderedTourDAO {
    private static final String SQL_GET_ALL_ORDERED_TOURS = "SELECT * FROM ordered_tours";
    private static final String SQL_GET_ORDERED_TOUR_BY_ID = SQL_GET_ALL_ORDERED_TOURS + " WHERE ordered_tours.id=?";
//    //
//    private static final String SQL_INSERT_TOUR = "INSERT INTO tours" + "(name, tour_type,  hotel_id, guests_number, start_day, end_day, price, max_discount, discount_step," + "hot, description) " + "VALUES " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIELD_ID = "id";
    private static final String FIELD_TOUR_ID = "tour_id";
    private static final String FIELD_USER_ID = "user_id";
    private static final String FIELD_DISCOUNT = "discount";
    private static final String FIELD_TOUR_STATUS = "tour_status";
    private static final String FIELD_ANNOTATION = "annotation";

    public static OrderedTour findOrderedTourById(Integer id) {

        OrderedTour orderedTour = null;
        try (Connection con = DataSource.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_GET_ORDERED_TOUR_BY_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) orderedTour = getOrderedTourFromResultSet(rs);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return orderedTour;
    }

    public static List<Tour> getAllOrderedTours() {
        List<Tour> orderedTours = new ArrayList<>();

        try (Connection con = DataSource.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_ORDERED_TOURS); ResultSet rs = pst.executeQuery()) {
//            while (rs.next()) {
//                tours.add(getTourFromResultSet(rs));
//            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return orderedTours;
    }

//    public static int createTour(Tour tour) {
//        int result = 0;
//
//        try (Connection con = DataSource.getConnection();
//             PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT_TOUR)) {
//
//            preparedStatement.setString(1, tour.getName());
//            preparedStatement.setString(2, String.valueOf(tour.getTourType()));
//
//            preparedStatement.setObject(3, tour.getHotelId());
//            preparedStatement.setInt(4, tour.getGuestsNumber());
//            preparedStatement.setDate(5, Date.valueOf(tour.getStartDay()));
//            preparedStatement.setDate(6, Date.valueOf(tour.getEndDay()));
//            preparedStatement.setBigDecimal(7, tour.getPrice());
//            preparedStatement.setInt(8, tour.getMaxDiscount());
//            preparedStatement.setInt(9, tour.getDiscountStep());
//            preparedStatement.setBoolean(10, tour.getHot());
//            preparedStatement.setString(11, tour.getDescription());
//
//            result = preparedStatement.executeUpdate();
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return result;
//    }

    private static OrderedTour getOrderedTourFromResultSet(ResultSet rs) {
        OrderedTour orderedTour;
        try {
            orderedTour = new OrderedTour();
            orderedTour.setId(rs.getInt(FIELD_ID));
            orderedTour.setTourId(rs.getInt(FIELD_TOUR_ID));
            orderedTour.setStatus((OrderedTour.Status) rs.getObject(FIELD_TOUR_STATUS));
            orderedTour.setAnnotation(rs.getString(FIELD_ANNOTATION));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return orderedTour;
    }
}
