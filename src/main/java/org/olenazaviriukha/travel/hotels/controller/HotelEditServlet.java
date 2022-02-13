package org.olenazaviriukha.travel.hotels.controller;

import org.olenazaviriukha.travel.common.exceptions.ValidationException;
import org.olenazaviriukha.travel.dao.DuplicateKeyException;
import org.olenazaviriukha.travel.hotels.dao.HotelDAO;
import org.olenazaviriukha.travel.hotels.entity.Hotel;
import org.olenazaviriukha.travel.common.utils.ValidationUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet({"/hotel_add", "/hotel_edit"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class HotelEditServlet extends HttpServlet {
    private static final String NAME = "name";
    private static final String HOTEL_TYPE = "hotel_type";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE_URL = "/images/hotels/";
//    private static final String IMAGE_PATH = "images\\hotels\\";
//
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer hotelId = null;
        try {
            hotelId = Integer.valueOf(req.getParameter("hotel_id"));
        } catch (NumberFormatException e) {
        }
        ;
        if (hotelId != null) {
            Hotel hotel = HotelDAO.getHotelById(hotelId);
            req.setAttribute("hotel", hotel);
        }

        req.getRequestDispatcher("/JSP/hotels/hotel_edit.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Hotel hotel = null;
        try {
            hotel = getHotelFromRequest(req);
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            req.setAttribute("hotel", e.getObject());
            getServletContext().getRequestDispatcher("/JSP/hotels/hotel_edit.jsp").forward(req, resp);
            return;
        } catch (Exception e) {
            // Error reading hotel from request
            e.printStackTrace();
        }

        try {
            if (hotel.getId() == null) HotelDAO.createHotel(hotel);
            else HotelDAO.updateHotel(hotel);
        } catch (DuplicateKeyException e) {
            Map<String, String> errors = new HashMap<>();
            errors.put(e.getParam(), e.getMessage());
            req.setAttribute("errors", errors);
            req.setAttribute("hotel", hotel);
            getServletContext().getRequestDispatcher("/JSP/hotels/hotel_edit.jsp").forward(req, resp);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.sendRedirect(req.getContextPath() + "/hotels");
    }

    /**
     * @param req request from doPost()
     * @return hotel
     * @throws Exception if input is incorrect
     */
    private Hotel getHotelFromRequest(HttpServletRequest req) throws Exception {
        Hotel hotel = new Hotel();
        try {
            hotel.setId(Integer.valueOf(req.getParameter("hotel_id")));
        } catch (NumberFormatException e) {}


        Map<String, String> errors = new HashMap<>();

        String name = req.getParameter(NAME);
        hotel.setName(name);
        String nameError = ValidationUtils.hotelNameValidationError(name);
        if (nameError != null) errors.put(NAME, nameError);

        String hotelTypeStr = req.getParameter(HOTEL_TYPE);
        if (hotelTypeStr.isBlank()) hotelTypeStr = "0";
        Integer hotelType = Integer.valueOf(hotelTypeStr);
        hotel.setHotelType(hotelType);
        String hotelTypeError = ValidationUtils.hotelTypeValidationError(hotelType);
        if (hotelTypeError != null) errors.put(HOTEL_TYPE, hotelTypeError);

        String description = req.getParameter(DESCRIPTION);
        hotel.setDescription(description);
//        FileSystem fs = FileSystems.getDefault();

//        Part filePart = req.getPart("image");
//        if (filePart.getSize() > 0) {
//            String fileName = filePart.getSubmittedFileName();
//            hotel.setImage(IMAGE_URL + "_" + fileName);
//            for (Part part : req.getParts()) {
////                part.write(IMAGE_PATH + "_" + fileName);
//            }
//        }

        if (errors.isEmpty()) return hotel;
        throw new ValidationException(hotel, errors);
    }

}
