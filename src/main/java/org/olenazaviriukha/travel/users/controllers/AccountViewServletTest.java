package org.olenazaviriukha.travel.users.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.olenazaviriukha.travel.orederedTours.dao.OrderedTourDAO;
import org.olenazaviriukha.travel.users.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class AccountViewServletTest {
    private final String PARAM_USER = "user";

    HttpServletRequest req;
    HttpServletResponse resp;
    HttpSession session;


    @BeforeEach
    public void testCaseSetup() {
        req = Mockito.mock(HttpServletRequest.class);
        resp = Mockito.spy(HttpServletResponse.class);
        session = Mockito.mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(PARAM_USER)).thenReturn(new User());
        when(req.getContextPath()).thenReturn("");
        when(req.getRequestDispatcher(anyString())).thenReturn(Mockito.mock(RequestDispatcher.class));
    }

    @Test
    public void shouldForwardToAccountJSP() throws ServletException, IOException {
        User user = new User();
        when(session.getAttribute(PARAM_USER)).thenReturn(user);
        try (MockedStatic<OrderedTourDAO> orderedTourDAO = Mockito.mockStatic(OrderedTourDAO.class)) {
            orderedTourDAO.when(() -> OrderedTourDAO.getUserOrderedTours(user)).thenReturn(null);
            new AccountViewServlet().doGet(req, resp);
            verify(req, times(1)).getRequestDispatcher("/JSP/users/account.jsp");

        }

    }

}
