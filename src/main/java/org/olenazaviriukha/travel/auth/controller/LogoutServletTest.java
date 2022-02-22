package org.olenazaviriukha.travel.auth.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class LogoutServletTest {
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
        when(req.getContextPath()).thenReturn("");
        when(req.getRequestDispatcher(anyString())).thenReturn(Mockito.mock(RequestDispatcher.class));
    }

    @Test
    public void shouldRedirectToMain() throws ServletException, IOException {
        new LogoutServlet().doGet(req, resp);
        verify(resp, times(1)).sendRedirect("/main");
    }

}
