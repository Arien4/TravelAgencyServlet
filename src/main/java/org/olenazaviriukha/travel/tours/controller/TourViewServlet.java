package org.olenazaviriukha.travel.tours.controller;

import org.olenazaviriukha.travel.orederedTours.dao.OrderedTourDAO;
import org.olenazaviriukha.travel.orederedTours.entity.OrderedTour;
import org.olenazaviriukha.travel.tours.dao.TourDAO;
import org.olenazaviriukha.travel.tours.entity.Tour;
import org.olenazaviriukha.travel.users.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/tour")
public class TourViewServlet extends HttpServlet {
    private final static String PARAM_TOUR_ID = "tour_id";
    private static final String SESSION_USER = "user";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer tourId = null;
        Tour tour = null;
        try {
            tourId = Integer.valueOf(req.getParameter(PARAM_TOUR_ID));
        } catch (NumberFormatException e) {
            // 404
        }
        try {
            tour = TourDAO.getTourById(tourId);
        } catch (Exception e) {
            // 404
        }
        if (tour != null)
        req.setAttribute("tour", tour);
        getServletContext().getRequestDispatcher("/JSP/tours/tour_view.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderedTour ot = new OrderedTour();
        Integer tourId = Integer.valueOf(req.getParameter(PARAM_TOUR_ID));
        ot.setTourId(tourId);
        HttpSession session = req.getSession();
        ot.setUserId(((User)session.getAttribute(SESSION_USER)).getId());
        Tour tour = TourDAO.getTourById(tourId);
        ot.setFixedPrice(tour.getPrice());
        OrderedTourDAO.createOrderedTour(ot);

        resp.sendRedirect(req.getContextPath() + "/account");
    }
}
