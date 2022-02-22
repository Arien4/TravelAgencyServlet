package org.olenazaviriukha.travel.users.controllers;

import org.olenazaviriukha.travel.orederedTours.dao.OrderedTourDAO;
import org.olenazaviriukha.travel.orederedTours.entity.OrderedTour;
import org.olenazaviriukha.travel.users.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/account")
public class AccountViewServlet extends HttpServlet {
    private static final String SESSION_USER = "user";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(SESSION_USER);
        req.setAttribute("user", user);
        List<OrderedTour> orderedTours = OrderedTourDAO.getUserOrderedTours(user);
        req.setAttribute("orderedTours", orderedTours);
        req.getRequestDispatcher("/JSP/users/account.jsp").forward(req, resp);
    }
}
