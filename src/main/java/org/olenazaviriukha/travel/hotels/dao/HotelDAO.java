package org.olenazaviriukha.travel.hotels.dao;

import org.olenazaviriukha.travel.dao.DuplicateKeyException;
import org.olenazaviriukha.travel.common.db.DataSource;
import org.olenazaviriukha.travel.hotels.entity.Hotel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelDAO {
    private static final String SQL_GET_ALL_HOTELS =
            "SELECT * from hotel";
    private static final String SQL_GET_HOTEL_BY_NAME = SQL_GET_ALL_HOTELS + " WHERE LOWER(hotel.name)=LOWER(?)";
    private static final String SQL_GET_HOTEL_BY_ID = SQL_GET_ALL_HOTELS + " WHERE hotel.id=?";

    private static final String SQL_INSERT_HOTEL =
            "INSERT INTO hotel (name, hotel_type, description, image) " +
                    "VALUES " + "(?, ?, ?, ?)";
    private static final String SQL_UPDATE_HOTEL =
            "UPDATE hotel " +
                    "SET name=?, hotel_type=?, description=?, image=? " +
                    "WHERE id=?";
    private static final String FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_HOTEL_TYPE = "hotel_type";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_IMAGE = "image";


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

    public static Hotel getHotelById(Integer id) {

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
            preparedStatement.setString(4, hotel.getImage());

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
            hotel.setImage(rs.getString(FIELD_IMAGE));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return hotel;
    }

    public static int updateHotel(Hotel hotel) throws DuplicateKeyException {
        int rowsAffected = 0;

        try (Connection con = DataSource.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQL_UPDATE_HOTEL)) {

            preparedStatement.setString(1, hotel.getName());
            preparedStatement.setInt(2, hotel.getHotelType());
            preparedStatement.setString(3, hotel.getDescription());
            preparedStatement.setString(4, hotel.getImage());
            preparedStatement.setInt(5, hotel.getId());

            rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateKeyException("name", "Hotel with provided name currently exists");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;

    }
}
