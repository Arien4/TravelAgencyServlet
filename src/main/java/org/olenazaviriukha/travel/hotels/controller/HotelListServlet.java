package org.olenazaviriukha.travel.hotels.controller;

import org.olenazaviriukha.travel.hotels.dao.HotelDAO;
import org.olenazaviriukha.travel.hotels.model.Hotel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/hotels")
public class HotelListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Hotel> hotels = HotelDAO.getAllHotels();
        req.setAttribute("hotels", hotels);
        getServletContext().getRequestDispatcher("/JSP/hotels/hotel_list.jsp").forward(req, resp);
    }
}
