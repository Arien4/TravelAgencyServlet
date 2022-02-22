package users.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.olenazaviriukha.travel.common.db.DataSource;
import org.olenazaviriukha.travel.users.dao.UserDAO;
import org.olenazaviriukha.travel.users.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserDAOTest {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @BeforeEach
    public void testCaseSetup() throws SQLException {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void shouldProvideEmailIntoReturnUserByEmail() throws SQLException {
        String email = "test@example.com";
        try (MockedStatic<DataSource> dataSource = mockStatic(DataSource.class)) {
            dataSource.when(() -> DataSource.getConnection()).thenReturn(connection);
            UserDAO.getUserByEmail(email);
            verify(connection).prepareStatement(UserDAO.SQL_GET_USER_BY_EMAIL);
            verify(preparedStatement, times(1)).setString(1, email);
        }
    }

    @Test
    public void shouldReturnUserByEmail() throws SQLException {
        String email = "test@example.com";
        when(resultSet.next()).thenReturn(true).thenThrow(SQLException.class);
        when(resultSet.getString("email")).thenReturn(email);
        User user = null;
        try (MockedStatic<DataSource> dataSource = mockStatic(DataSource.class)) {
            dataSource.when(() -> DataSource.getConnection()).thenReturn(connection);
            user = UserDAO.getUserByEmail(email);
        }
        Assertions.assertNotNull(user);
        assertEquals(user.getEmail(), email);
    }

    @Test
    public void shouldReturnNullIfEmailNotExists() throws SQLException {
        String email = "test@example.com";
        User user = null;
        when(resultSet.next()).thenThrow(SQLException.class);
        try (
                MockedStatic<DataSource> dataSource = mockStatic(DataSource.class);
        ) {
            dataSource.when(() -> DataSource.getConnection()).thenReturn(connection);
            user = UserDAO.getUserByEmail(email);
        }
        assertNull(user);
    }

    @Test
    public void shouldProvideIdIntoReturnUserById() throws SQLException {
        int userId = 45;
        try (MockedStatic<DataSource> dataSource = mockStatic(DataSource.class)) {
            dataSource.when(() -> DataSource.getConnection()).thenReturn(connection);
            UserDAO.getUserById(userId);
            verify(connection).prepareStatement(UserDAO.SQL_GET_USER_BY_ID);
            verify(preparedStatement, times(1)).setInt(1, userId);
        }
    }

    @Test
    public void shouldReturnUserById() throws SQLException {
        Integer id = 1;
        when(resultSet.next()).thenReturn(true).thenThrow(SQLException.class);
        when(resultSet.getInt("id")).thenReturn(id);
        User user = null;
        try (MockedStatic<DataSource> dataSource = mockStatic(DataSource.class)) {
            dataSource.when(() -> DataSource.getConnection()).thenReturn(connection);
            user = UserDAO.getUserById(id);
        }
        Assertions.assertNotNull(user);
        assertEquals(user.getId(), id);
    }

    @Test
    public void shouldReturnNullIfIdNotExists() throws SQLException {
        Integer id = 3;
        User user = null;
        when(resultSet.next()).thenThrow(SQLException.class);
        try (
                MockedStatic<DataSource> dataSource = mockStatic(DataSource.class);
        ) {
            dataSource.when(() -> DataSource.getConnection()).thenReturn(connection);
            user = UserDAO.getUserById(id);
        }
        assertNull(user);
    }

    @Test
    public void shouldUpdateUserRecord() throws SQLException {
        User user = new User();
        user.setId(1);
        user.setEmail("email@mail.com");
        user.setFirstName("John");
        user.setLastName("Smith");
        user.setRoleId(0);
        user.setBlocked(false);
        try (MockedStatic<DataSource> dataSource = mockStatic(DataSource.class)) {
            dataSource.when(DataSource::getConnection).thenReturn(connection);
            UserDAO.updateUser(user);
            verify(connection).prepareStatement(UserDAO.SQL_UPDATE_USER);
            verify(preparedStatement, times(1)).setString(1, user.getEmail());
            verify(preparedStatement, times(1)).setString(2, user.getFirstName());
            verify(preparedStatement, times(1)).setString(3, user.getLastName());
            verify(preparedStatement, times(1)).setInt(4, user.getRoleId());
            verify(preparedStatement, times(1)).setBoolean(5, user.isBlocked());
            verify(preparedStatement, times(1)).setInt(6, user.getId());
        }
    }
}
