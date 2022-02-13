package org.olenazaviriukha.travel.tours.controller;

import org.olenazaviriukha.travel.hotels.dao.HotelDAO;
import org.olenazaviriukha.travel.hotels.entity.Hotel;
import org.olenazaviriukha.travel.tours.dao.TourDAO;
import org.olenazaviriukha.travel.tours.entity.Tour;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/tours")
public class TourListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Tour> tours = TourDAO.getAllTours();
        req.setAttribute("tours", tours);
        getServletContext().getRequestDispatcher("/JSP/tours/tour_list.jsp").forward(req, resp);
    }
}
