package org.olenazaviriukha.travel.controller;

import org.olenazaviriukha.travel.controller.exceptions.HotelValidationException;
import org.olenazaviriukha.travel.dao.DuplicateKeyException;
import org.olenazaviriukha.travel.dao.HotelDAO;
import org.olenazaviriukha.travel.entity.Hotel;
import org.olenazaviriukha.travel.utils.ValidationUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet({"/hotel_add", "/hotel_edit"})
public class HotelEditServlet extends HttpServlet {
    private static final String NAME = "name";
    private static final String HOTEL_TYPE = "hotel_type";
    private static final String DESCRIPTION = "description";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer hotelId = null;
        try{
            hotelId = Integer.valueOf(req.getParameter("hotel_id"));
        } catch (NumberFormatException e) {};
        if (hotelId != null) {
            Hotel hotel = HotelDAO.findHotelById(hotelId);
            req.setAttribute("hotel", hotel);
        }

        req.getRequestDispatcher("/hotel_add.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Hotel hotel = null;
        try {
            hotel = getHotelFromRequest(req);
        } catch (HotelValidationException e) {
            req.setAttribute("errors", e.getErrors());
            req.setAttribute("hotel", e.getHotel());
            getServletContext().getRequestDispatcher("/hotel_add.jsp").forward(req, resp);
            return;
        } catch (Exception e) {
            // Error reading hotel from request
            e.printStackTrace();
        }
        try {
            HotelDAO.createHotel(hotel);
        } catch (DuplicateKeyException e) {
            Map<String, String> errors = new HashMap<>();
            errors.put(e.getParam(), e.getMessage());
            req.setAttribute("errors", errors);
            req.setAttribute("hotel", hotel);
            getServletContext().getRequestDispatcher("/hotel_add.jsp").forward(req, resp);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.sendRedirect("/hotels");
    }

    /**
     *
     * @param req request from doPost()
     * @return hotel
     * @throws Exception if input is incorrect
     */
    private Hotel getHotelFromRequest(HttpServletRequest req) throws Exception {
        Hotel hotel = new Hotel();
        Map<String, String> errors = new HashMap<>();

        String name = req.getParameter(NAME);
        hotel.setName(name);
        String nameError = ValidationUtils.hotelNameValidationError(name);
        if (nameError != null) errors.put(NAME, nameError);

        String hotelTypeStr = req.getParameter(HOTEL_TYPE);
        if (hotelTypeStr.isBlank()) hotelTypeStr="0";
        Integer hotelType = Integer.valueOf(hotelTypeStr);
        hotel.setHotelType(hotelType);
        String hotelTypeError = ValidationUtils.hotelTypeValidationError(hotelType);
        if (hotelTypeError != null) errors.put(HOTEL_TYPE, hotelTypeError);

        String description = req.getParameter(DESCRIPTION);
        hotel.setDescription(description);

        if (errors.isEmpty()) return hotel;
        throw new HotelValidationException(hotel, errors);
    }

}
