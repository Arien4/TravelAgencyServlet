package org.olenazaviriukha.travel.dao;

import org.olenazaviriukha.travel.db.DataSource;
import org.olenazaviriukha.travel.entity.Hotel;
import org.olenazaviriukha.travel.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelDAO {
    private static final String SQL_GET_ALL_HOTELS =
            "SELECT * from hotels";
    private static final String SQL_GET_HOTEL_BY_NAME = SQL_GET_ALL_HOTELS + " WHERE LOWER(hotels.name)=LOWER(?)";
    private static final String SQL_GET_HOTEL_BY_ID = SQL_GET_ALL_HOTELS + " WHERE hotels.id=?";

    private static final String SQL_INSERT_HOTEL = "INSERT INTO hotels" + "(name, hotel_type, description) VALUES " + "(?, ?, ?)";
    private static final String FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_HOTEL_TYPE = "hotel_type";
    private static final String FIELD_DESCRIPTION = "description";


    public static Hotel findHotelByName(String name) {

        Hotel hotel = null;
        try (Connection con = DataSource.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_GET_HOTEL_BY_NAME)) {
            pst.setString(1, name);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) hotel = getHotelFromResultSet(rs);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return hotel;

    }

    public static Hotel findHotelById(Integer id) {

        Hotel hotel = null;
        try (Connection con = DataSource.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_GET_HOTEL_BY_ID)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) hotel = getHotelFromResultSet(rs);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return hotel;
    }

        public static List<Hotel> getAllHotels() {
        List<Hotel> hotels = new ArrayList<>();

        try (Connection con = DataSource.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_HOTELS); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                hotels.add(getHotelFromResultSet(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return hotels;
    }

    public static int createHotel(Hotel hotel) throws DuplicateKeyException {
        int result = 0;

        try (Connection con = DataSource.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT_HOTEL)) {

            preparedStatement.setString(1, hotel.getName());
            preparedStatement.setInt(2, hotel.getHotelType());
            preparedStatement.setString(3, hotel.getDescription());

            result = preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateKeyException("name", "Hotel with provided name currently exists");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private static Hotel getHotelFromResultSet(ResultSet rs) {
        Hotel hotel;
        try {
            hotel = new Hotel();
            hotel.setId(rs.getInt(FIELD_ID));
            hotel.setName(rs.getString(FIELD_NAME));
            hotel.setHotelType(rs.getInt(FIELD_HOTEL_TYPE));
            hotel.setDescription(rs.getString(FIELD_DESCRIPTION));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return hotel;
    }
}
