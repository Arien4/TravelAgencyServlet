package servlets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.olenazaviriukha.travel.auth.controller.LoginServlet;
import org.olenazaviriukha.travel.common.utils.SecurityUtils;
import org.olenazaviriukha.travel.users.dao.UserDAO;
import org.olenazaviriukha.travel.users.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.mockito.Mockito.*;

public class LoginServletTest {
    private final String PARAM_USER = "user";
    private final String PARAM_EMAIL = "email";
    private final String PARAM_PASSWORD = "password";
    private final String PARAM_REMEMBER = "remember";
    private final String PARAM_ERROR = "error";

    private final String VALUE_EMAIL = "test@example.com";
    private final String VALUE_PASSWORD = "passwd";
    private final String MESSAGE_AUTH_ERROR = "Authentication error";
    private final String MESSAGE_BLOCKED = "User is blocked";


    HttpServletRequest req;
    HttpServletResponse resp;
    HttpSession session;


    @BeforeEach
    public void testCaseSetup() {
        req = Mockito.mock(HttpServletRequest.class);
        resp = Mockito.spy(HttpServletResponse.class);
        session = Mockito.mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        when(req.getContextPath()).thenReturn("");
        when(req.getRequestDispatcher(anyString())).thenReturn(Mockito.mock(RequestDispatcher.class));
        when(req.getParameter(PARAM_EMAIL)).thenReturn(VALUE_EMAIL);
        when(req.getParameter(PARAM_PASSWORD)).thenReturn(VALUE_PASSWORD);
        when(req.getParameter(PARAM_REMEMBER)).thenReturn("on");
    }

    @Test
    public void shouldRedirectIfUserIsLoggedInGET() throws ServletException, IOException {
        User user = new User();
        when(session.getAttribute(PARAM_USER)).thenReturn(user);
        new LoginServlet().doGet(req, resp);
        verify(resp, times(1)).sendRedirect("/main");
    }

    @Test
    public void shouldRedirectIfUserIsLoggedInPOST() throws ServletException, IOException {
        User user = new User();
        when(session.getAttribute(PARAM_USER)).thenReturn(user);
        new LoginServlet().doPost(req, resp);
        verify(resp, times(1)).sendRedirect("/main");
    }

    @Test
    public void shouldForwardToLoginJSP() throws ServletException, IOException {
        when(session.getAttribute(PARAM_USER)).thenReturn(null);
        new LoginServlet().doGet(req, resp);
        verify(req, times(1)).getRequestDispatcher("/login.jsp");
    }

    @Test
    public void shouldReturnErrorMessageIfUserNotExists() throws ServletException, IOException {
        try (MockedStatic<UserDAO> userDAO = mockStatic(UserDAO.class)) {
            userDAO.when(() -> UserDAO.getUserByEmail(VALUE_EMAIL)).thenReturn(null);
            new LoginServlet().doPost(req, resp);
            verify(req).setAttribute(PARAM_ERROR, MESSAGE_AUTH_ERROR);

        }
    }

    @Test
    public void shouldReturnErrorMessageIfPasswordValidationThrowException() throws ServletException, IOException {
        try (
                MockedStatic<UserDAO> userDAOMockedStatic = mockStatic(UserDAO.class);
                MockedStatic<SecurityUtils> securityUtilsMockedStatic = mockStatic(SecurityUtils.class)
        ) {
            userDAOMockedStatic.when(() -> UserDAO.getUserByEmail(VALUE_EMAIL)).thenReturn(new User());
            securityUtilsMockedStatic.when(() -> SecurityUtils.validatePassword(any(), any())).thenThrow(new NoSuchAlgorithmException());
            new LoginServlet().doPost(req, resp);
            verify(req).setAttribute(PARAM_ERROR, MESSAGE_AUTH_ERROR);

        }
    }

    @Test
    public void shouldReturnErrorMessageIfPasswordValidationFails() throws ServletException, IOException {
        try (
                MockedStatic<UserDAO> ususerDAOMockedStaticrDAO = mockStatic(UserDAO.class);
                MockedStatic<SecurityUtils> securityUtilsMockedStatic = mockStatic(SecurityUtils.class)
        ) {
            ususerDAOMockedStaticrDAO.when(() -> UserDAO.getUserByEmail(VALUE_EMAIL)).thenReturn(new User());
            securityUtilsMockedStatic.when(() -> SecurityUtils.validatePassword(anyString(), anyString())).thenReturn(false);
            new LoginServlet().doPost(req, resp);
            verify(req).setAttribute(PARAM_ERROR, MESSAGE_AUTH_ERROR);

        }
    }

    @Test
    public void shouldReturnErrorMessageIfUserIsBlocked() throws ServletException, IOException {
        try (
                MockedStatic<UserDAO> ususerDAOMockedStaticrDAO = mockStatic(UserDAO.class);
                MockedStatic<SecurityUtils> securityUtilsMockedStatic = mockStatic(SecurityUtils.class)
        ) {
            User blockedUser = new User();
            blockedUser.setBlocked(true);
            blockedUser.setPassword("test");
            ususerDAOMockedStaticrDAO.when(() -> UserDAO.getUserByEmail(VALUE_EMAIL)).thenReturn(blockedUser);
            securityUtilsMockedStatic.when(() -> SecurityUtils.validatePassword(anyString(), anyString())).thenReturn(true);
            new LoginServlet().doPost(req, resp);
            verify(req).setAttribute(PARAM_ERROR, MESSAGE_BLOCKED);
        }
    }

    @Test
    public void shouldSetUserInSessionOnSuccess() throws ServletException, IOException {
        try (
                MockedStatic<UserDAO> ususerDAOMockedStaticrDAO = mockStatic(UserDAO.class);
                MockedStatic<SecurityUtils> securityUtilsMockedStatic = mockStatic(SecurityUtils.class)
        ) {
            User validUser = new User();
            validUser.setBlocked(false);
            validUser.setPassword("test");
            ususerDAOMockedStaticrDAO.when(() -> UserDAO.getUserByEmail(VALUE_EMAIL)).thenReturn(validUser);
            securityUtilsMockedStatic.when(() -> SecurityUtils.validatePassword(anyString(), anyString())).thenReturn(true);
            new LoginServlet().doPost(req, resp);
            verify(session).setAttribute(PARAM_USER, validUser);
        }
    }
}
