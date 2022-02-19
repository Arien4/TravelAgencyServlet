package org.olenazaviriukha.travel.orederedTours.controller;

import org.olenazaviriukha.travel.orederedTours.dao.OrderedTourDAO;
import org.olenazaviriukha.travel.orederedTours.entity.OrderedTour;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ordered_tour")
public class OrderedTourEditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer orderedTourId = null;
        try {
            orderedTourId = Integer.valueOf(req.getParameter("ordered_tour_id"));
        } catch (NumberFormatException e) {}

        if (orderedTourId != null) {
            OrderedTour orderedTour =  OrderedTourDAO.getOrderedTourById(orderedTourId);
            req.setAttribute("orderedTour", orderedTour);
        }
        req.setAttribute("orderedTourStatusList", OrderedTour.Status.values());
        req.getRequestDispatcher("/JSP/ordered_tours/ordered_tour_edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderedTour orderedTour =  getObjectFromRequest(req);
        OrderedTourDAO.updateOrderedTour(orderedTour);
        resp.sendRedirect(req.getContextPath() + "/ordered_tours");
    }

    private OrderedTour getObjectFromRequest(HttpServletRequest req) {
        String ORDERED_TOUR_ID = "ordered_tour_id";
        String STATUS = "status";
        String DISCOUNT = "discount";
        OrderedTour orderedTour = new OrderedTour();
        orderedTour.setId(Integer.valueOf(req.getParameter(ORDERED_TOUR_ID)));
        orderedTour.setStatus(OrderedTour.Status.valueOf(req.getParameter(STATUS)));
        orderedTour.setDiscount(Integer.valueOf(req.getParameter(DISCOUNT)));
        return orderedTour;
    }
}
