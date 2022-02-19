package org.olenazaviriukha.travel.hotels.controller;

import org.olenazaviriukha.travel.hotels.dao.HotelDAO;
import org.olenazaviriukha.travel.hotels.entity.Hotel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/hotel")
public class HotelViewServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer hotelId = null;
        Hotel hotel = null;
        try {
            hotelId = Integer.valueOf(req.getParameter("hotel_id"));
        } catch (NumberFormatException e) {
            //404
        }

        try {
            hotel = HotelDAO.getHotelById(hotelId);
        } catch (Exception e) {
            //404
        }
        if (hotel != null) req.setAttribute("hotel", hotel);
        getServletContext().getRequestDispatcher("/JSP/hotels/hotel_view.jsp").forward(req, resp);
    }
}
