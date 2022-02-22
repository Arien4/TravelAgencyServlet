package org.olenazaviriukha.travel.tours.controller;

import org.olenazaviriukha.travel.common.exceptions.DuplicateKeyException;
import org.olenazaviriukha.travel.common.exceptions.ValidationException;
import org.olenazaviriukha.travel.common.utils.ValidationUtils;
import org.olenazaviriukha.travel.hotels.dao.HotelDAO;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@WebServlet({"/tour_add", "/tour_edit", "/tour_edit_limited"})
public class TourEditServlet extends HttpServlet {
    private static final String TOUR_ID = "tour_id";
    private static final String NAME = "name";
    private static final String TOUR_TYPE = "tour_type";
    private static final String HOTEL_ID = "hotel_id";
    private static final String GUESTS_NUMBER = "guests_number";
    private static final String START_DAY = "start_day";
    private static final String END_DAY = "end_day";
    private static final String DAYS = "days";
    private static final String PRICE = "price";
    private static final String MAX_DISCOUNT = "max_discount";
    private static final String DISCOUNT_STEP = "discount_step";
    private static final String HOT = "hot";
    private static final String DESCRIPTION = "description";

    private void setRequestDefaultValues(HttpServletRequest req) {
        req.setAttribute("tour_types", Tour.TourType.values());
        req.setAttribute("hotels", HotelDAO.getAllHotels());
    }

    private void setRequestValues(HttpServletRequest req, Tour tour, Map<String, String> errors) {
        req.setAttribute("errors", errors);
        req.setAttribute("tour", tour);
        setRequestDefaultValues(req);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        this.setRequestDefaultValues(req);
        Integer tourId = null;
        try {
            tourId = Integer.valueOf(req.getParameter("tour_id"));
        } catch (NumberFormatException e) {}

        if (tourId != null) {
            Tour tour = TourDAO.getTourById(tourId);
            req.setAttribute("tour", tour);
        }
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user.isAdmin()) req.getRequestDispatcher("/JSP/tours/tour_edit.jsp").forward(req, resp);
        else if (user.isManager()) req.getRequestDispatcher("/JSP/tours/tour_edit_limited.jsp").forward(req, resp);
        else resp.sendError(HttpServletResponse.SC_NOT_FOUND);

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Tour tour = null;
        try {
            tour = getTourFromRequest(req);
        } catch (ValidationException e) {
            setRequestValues(req, (Tour) e.getObject(), e.getErrors());
            getServletContext().getRequestDispatcher("/JSP/tours/tour_edit.jsp").forward(req, resp);
            return;
        } catch (Exception e) {
            // Error reading tour from request
            e.printStackTrace();
        }

        try {
            if (tour.getId() == null) TourDAO.createTour(tour);
            else TourDAO.updateTour(tour);
        } catch (DuplicateKeyException e) {
            Map<String, String> errors = new HashMap<>();
            errors.put(e.getParam(), e.getMessage());
            req.setAttribute("errors", errors);
            req.setAttribute("tour", tour);
            getServletContext().getRequestDispatcher("/JSP/tours/tour_edit.jsp").forward(req, resp);
            return;
        } catch (Exception e) {
            setRequestValues(req, tour, null);
            getServletContext().getRequestDispatcher("/JSP/tours/tour_edit.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/tours");
    }

    /**
     * @param req request from doPost()
     * @return tour
     * @throws Exception if input is incorrect
     */
    private Tour getTourFromRequest(HttpServletRequest req) throws Exception {
        Tour tour = new Tour();
        Map<String, String> errors = new HashMap<>();

        Integer tourId = null;
        try {
            tourId = Integer.valueOf(req.getParameter(TOUR_ID));
        } catch (NumberFormatException ignored) {}
        tour.setId(tourId);

        tour.setName(req.getParameter(NAME));
        tour.setTourType(Tour.TourType.valueOf(req.getParameter(TOUR_TYPE)));
        try {
            tour.setHotelId(Integer.valueOf(req.getParameter(HOTEL_ID)));
        } catch (NumberFormatException e) {
            tour.setHotelId(null);
        }

        Integer guests = null;
        try {
            guests = Integer.parseInt(req.getParameter(GUESTS_NUMBER));
        } catch (NumberFormatException e) {

        }
        tour.setGuestsNumber(guests);
        String guestsNumberError = ValidationUtils.guestsNumberValidationError(guests);
        if (guestsNumberError != null) errors.put(GUESTS_NUMBER, guestsNumberError);

        LocalDate startDate = null, endDate = null;
        try {
            startDate = LocalDate.parse(req.getParameter(START_DAY));
        } catch (DateTimeParseException e) {

        } finally {
            tour.setStartDay(startDate);
            String dayError = ValidationUtils.tourDateError(startDate);
            if (dayError != null) errors.put(START_DAY, dayError);
        }
        try {
            endDate = LocalDate.parse(req.getParameter(END_DAY));
        } catch (DateTimeParseException e) {

        } finally {
            tour.setEndDay(endDate);
            String dayError = ValidationUtils.tourDateError(endDate);
            if (dayError != null) errors.put(END_DAY, dayError);
        }
        if (startDate != null && endDate != null) {
            String daysError = ValidationUtils.tourDatesError(startDate, endDate);
            if (daysError != null) errors.put(DAYS, daysError);
        }
        try {
            tour.setPrice(new BigDecimal(req.getParameter(PRICE)));
        } catch (NumberFormatException e) {
            tour.setPrice(BigDecimal.valueOf(0));
        }

        Integer maxDiscount = null;
        try {
            maxDiscount = Integer.parseInt(req.getParameter(MAX_DISCOUNT));
        } catch (NumberFormatException e) {

        }
        String maxDiscountError = ValidationUtils.maxDiscountValidationError(maxDiscount);
        tour.setMaxDiscount(maxDiscount);
        if (maxDiscountError != null) errors.put(MAX_DISCOUNT, maxDiscountError);

        int discountStep;
        try {
            discountStep = Integer.parseInt(req.getParameter(DISCOUNT_STEP));
        } catch (NumberFormatException e) {
            discountStep = 1;
        }
        tour.setDiscountStep(discountStep);
        String discountStepError = ValidationUtils.discountStepValidationError(discountStep, maxDiscount);
        if (discountStepError != null) errors.put(DISCOUNT_STEP, discountStepError);

        tour.setHot(req.getParameter(HOT) != null);

        tour.setDescription(req.getParameter(DESCRIPTION));

        if (errors.isEmpty()) return tour;
        throw new ValidationException(tour, errors);
    }


}
