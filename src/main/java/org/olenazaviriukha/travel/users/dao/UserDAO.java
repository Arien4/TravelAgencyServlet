package org.olenazaviriukha.travel.users.dao;

import org.olenazaviriukha.travel.common.exceptions.DuplicateKeyException;
import org.olenazaviriukha.travel.common.db.DataSource;
import org.olenazaviriukha.travel.users.entity.Role;
import org.olenazaviriukha.travel.users.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public static final String SQL_GET_ALL_USERS =
            "SELECT " +
                    "user.id, user.email, user.first_name, " +
                    "user.last_name, user.password, role.name as role_name, user.role_id, user.blocked " +
                    "FROM user " +
                    "LEFT JOIN role ON user.role_id=role.id";
    public static final String SQL_GET_USER_BY_EMAIL = SQL_GET_ALL_USERS + " WHERE user.email=?";
    public static final String SQL_GET_USER_BY_ID = SQL_GET_ALL_USERS + " WHERE user.id=?";
    private static final String SQL_INSERT_USER =
            "INSERT INTO user (email, first_name, last_name, password) VALUES (?, ?, ?, ?)";
    public static final String SQL_UPDATE_USER =
            "UPDATE user" +
                    " SET email=?, first_name=?, last_name=?, role_id=?, blocked=?" +
                    " WHERE id=?";

    private static final String FIELD_ID = "id";
    private static final String FIELD_EMAIL = "email";
    private static final String FIELD_FIRST_NAME = "first_name";
    private static final String FIELD_LAST_NAME = "last_name";
    private static final String FIELD_ROLE_ID = "role_id";
    private static final String FIELD_ROLE_NAME = "role_name";
    private static final String FIELD_PASS = "password";
    private static final String FIELD_BLOCKED = "blocked";


    public static User getUserByEmail(String email) {
        User user = null;
        try (Connection con = DataSource.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_GET_USER_BY_EMAIL)) {
            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) user = getUserFromResultSet(rs);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return user;

    }

    public static User getUserById(Integer userId) {
        User user = null;
        try (Connection con = DataSource.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_GET_USER_BY_ID)) {
            pst.setInt(1, userId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) user = getUserFromResultSet(rs);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return user;

    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection con = DataSource.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_USERS); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                users.add(getUserFromResultSet(rs));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;
    }

    public static int registerUser(User user) throws DuplicateKeyException {
        int result = 0;

        try (Connection con = DataSource.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT_USER)) {

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getPassword());

            result = preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateKeyException("email", "Duplicate value");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static int updateUser(User user) {
        int result = 0;
        try (Connection con = DataSource.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQL_UPDATE_USER)) {

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setInt(4, user.getRoleId());
            preparedStatement.setBoolean(5, user.isBlocked());
            preparedStatement.setInt(6, user.getId());

            result = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private static User getUserFromResultSet(ResultSet rs) {
        User user;
        try {
            user = new User();
            user.setId(rs.getInt(FIELD_ID));
            user.setEmail(rs.getString(FIELD_EMAIL));
            user.setFirstName(rs.getString(FIELD_FIRST_NAME));
            user.setLastName(rs.getString(FIELD_LAST_NAME));
            Role role = new Role();
            role.setId(rs.getInt(FIELD_ROLE_ID));
            role.setName(rs.getString(FIELD_ROLE_NAME));
            user.setRole(role);
            user.setPassword(rs.getString(FIELD_PASS));
            user.setBlocked(rs.getBoolean(FIELD_BLOCKED));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return user;
    }

}
