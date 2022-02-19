package org.olenazaviriukha.travel.main.controller;

import org.olenazaviriukha.travel.common.utils.WhereClauseJoiner;
import org.olenazaviriukha.travel.common.paginator.Paginator;
import org.olenazaviriukha.travel.tours.dao.TourDAO;
import org.olenazaviriukha.travel.tours.entity.Tour;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/main")
public class MainPageServlet extends HttpServlet {
    private static final String FILTER_TOUR_TYPE = "tour_type";
    private static final String FILTER_MIN_PRICE = "min_price";
    private static final String FILTER_MAX_PRICE = "max_price";
    private static final String FILTER_SQL_PRICE = "price";
    private static final String FILTER_GUESTS_NUMBER = "guests_number";
    private static final String FILTER_HOTEL_TYPE = "hotel_type";
    private static final String FILTER_HOTEL_ID = "hotel_id";
    private static final String VALUE_NONE = "none";

    private static final int TOURS_PER_PAGE = 6;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WhereClauseJoiner whereClauseJoiner = getFilterFromRequest(req);
        int currentPage = 1;
        try {
            currentPage = Integer.parseInt(req.getParameter(Paginator.QUERY_PARAM_NAME));
        } catch (NumberFormatException ignored) {
        }
        int tourCount = TourDAO.getFilteredToursCount(whereClauseJoiner);
        Paginator paginator = new Paginator(TOURS_PER_PAGE, tourCount, currentPage, req);
        List<Tour> tours = TourDAO.getFilteredTours(whereClauseJoiner, paginator.getLimit(), paginator.getActivePage().getOffset());//paginator);

        req.setAttribute("paginator", paginator);
        req.setAttribute("tours", tours);
        req.setAttribute("tour_types", Tour.TourType.values());
        getServletContext().getRequestDispatcher("/JSP/index.jsp").forward(req, resp);
    }

    public static WhereClauseJoiner getFilterFromRequest(HttpServletRequest req) {
        WhereClauseJoiner queryFilter = new WhereClauseJoiner();
        String tourType = req.getParameter(FILTER_TOUR_TYPE);
        if (tourType != null && !tourType.isBlank()) {
            req.setAttribute(FILTER_TOUR_TYPE, tourType);
            queryFilter.addCondition(FILTER_TOUR_TYPE, "=", tourType);
        }

        Integer minPrice = null;
        try {
            minPrice = Integer.valueOf(req.getParameter(FILTER_MIN_PRICE));
        } catch (NumberFormatException ignored) {
        }
        if (minPrice != null) {
            req.setAttribute(FILTER_MIN_PRICE, minPrice);
            queryFilter.addCondition(FILTER_SQL_PRICE, ">=", minPrice);
        }

        Integer maxPrice = null;
        try {
            maxPrice = Integer.valueOf(req.getParameter(FILTER_MAX_PRICE));
        } catch (NumberFormatException ignored) {
        }
        if (maxPrice != null) {
            req.setAttribute(FILTER_MAX_PRICE, maxPrice);
            queryFilter.addCondition(FILTER_SQL_PRICE, "<=", maxPrice);
        }

        Integer guestsNumber = null;
        try {
            guestsNumber = Integer.valueOf(req.getParameter(FILTER_GUESTS_NUMBER));
        } catch (NumberFormatException ignored) {
        }

        if (guestsNumber != null) {
            req.setAttribute(FILTER_GUESTS_NUMBER, guestsNumber);
            queryFilter.addCondition(FILTER_GUESTS_NUMBER, "=", guestsNumber);
        }

        String hotelType = req.getParameter(FILTER_HOTEL_TYPE);
        if (hotelType != null && !hotelType.isBlank()) {
            if (!hotelType.equals(VALUE_NONE)) {
                Integer hotelTypeInt = null;
                try {
                    hotelTypeInt = Integer.valueOf(hotelType);
                } catch (NumberFormatException ignored) {
                }
                if (hotelTypeInt != null) {
                    req.setAttribute(FILTER_HOTEL_TYPE, hotelType);
                    queryFilter.addCondition(FILTER_HOTEL_TYPE, "=", Integer.valueOf(hotelType));
                }
            } else {
                req.setAttribute(FILTER_HOTEL_TYPE, hotelType);
                queryFilter.addCondition(FILTER_HOTEL_ID, "IS", null);
            }
        }

        return queryFilter;
    }

}
