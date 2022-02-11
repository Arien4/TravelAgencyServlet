package org.olenazaviriukha.travel.dao;

import org.olenazaviriukha.travel.db.DataSource;
import org.olenazaviriukha.travel.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public static final String SQL_GET_ALL_USERS =
            "SELECT " +
                    "users.id, users.email, users.first_name, " +
                    "users.last_name, users.password, roles.role, users.blocked " +
                    "FROM users " +
                    "LEFT JOIN roles ON users.role=roles.id";
    public static final String SQL_GET_USER_BY_EMAIL = SQL_GET_ALL_USERS + " WHERE users.email=?";
    private static final String FIELD_ID = "id";
    private static final String FIELD_EMAIL = "email";
    private static final String FIELD_FIRST_NAME = "first_name";
    private static final String FIELD_LAST_NAME = "last_name";
    private static final String FIELD_ROLE = "role";
    private static final String FIELD_PASS = "password";
    private static final String SQL_INSERT_USER = "INSERT INTO users" + "(email, first_name, last_name, password) VALUES " + "(?, ?, ?, ?);";
    private static final String BLOCKED = "blocked";


    public static User findUserByEmail(String email) {
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

    private static User getUserFromResultSet(ResultSet rs) {
        User user;
        try {
            user = new User();
            user.setId(rs.getInt(FIELD_ID));
            user.setEmail(rs.getString(FIELD_EMAIL));
            user.setFirstName(rs.getString(FIELD_FIRST_NAME));
            user.setLastName(rs.getString(FIELD_LAST_NAME));
            user.setRole(rs.getString(FIELD_ROLE));
            user.setPassword(rs.getString(FIELD_PASS));
            user.setBlocked(rs.getBoolean(BLOCKED));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return user;
    }

}
