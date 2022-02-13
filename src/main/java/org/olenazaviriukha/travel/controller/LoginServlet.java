package org.olenazaviriukha.travel.controller;

import org.olenazaviriukha.travel.users.dao.UserDAO;
import org.olenazaviriukha.travel.users.entity.User;
import org.olenazaviriukha.travel.common.utils.SecurityUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final String USER = "user";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String REMEMBER = "remember";
    private static final String ERROR = "error";
    private static final String AUTHENTICATION_ERROR = "Authentication error";
    private static final String USER_BLOCKED = "User is blocked";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        if (session.getAttribute("user") != null) {
            // already logged in
            resp.sendRedirect(req.getContextPath() + "/main");
        } else {
//            req.getRequestDispatcher("/login").forward(req, resp);
            //req.setAttribute(REMEMBER, "");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        HttpSession session = req.getSession();
        if (session.getAttribute("user") != null) {
            // already logged in
            resp.sendRedirect("/main");
            return;
        }

        String email = req.getParameter(EMAIL);
        String password = req.getParameter(PASSWORD);
        String remember = req.getParameter(REMEMBER); // "on" or ""

        User user = UserDAO.getUserByEmail(email);
        try {
            if (user == null) {
                // authentication error: no such user
                req.setAttribute(EMAIL, email);
                req.setAttribute(ERROR, AUTHENTICATION_ERROR);
                getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
//                resp.sendRedirect("/login.jsp");
                return;
            }
            if (!SecurityUtils.validatePassword(password, user.getPassword())) {
                req.setAttribute(EMAIL, email);
                req.setAttribute(ERROR, AUTHENTICATION_ERROR);
                getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
                return;
            }
        } catch (Exception e) {
            // can't authenticate
            req.setAttribute(EMAIL, email);
            req.setAttribute(ERROR, AUTHENTICATION_ERROR);
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        if (user.isBlocked()) {
            // user is blocked
            req.setAttribute(ERROR, USER_BLOCKED);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        session.setAttribute(USER, user);

            if(remember == "on")
                session.setMaxInactiveInterval(604800); // 7 days
            else
                session.setMaxInactiveInterval(1800); //  30 minutes


        resp.sendRedirect(req.getContextPath() + "/main");


    }


}
