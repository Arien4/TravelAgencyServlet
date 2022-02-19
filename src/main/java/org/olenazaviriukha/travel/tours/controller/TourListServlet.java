package org.olenazaviriukha.travel.tours.controller;

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

@WebServlet("/tours")
public class TourListServlet extends HttpServlet {

    private static final int TOURS_PER_PAGE = 10;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int currentPage = 1;
        try {
            currentPage = Integer.parseInt(req.getParameter(Paginator.QUERY_PARAM_NAME));
        } catch (NumberFormatException ignored) {
        }

        int tourCount = TourDAO.getFilteredToursCount();
        Paginator paginator = new Paginator(TOURS_PER_PAGE, tourCount, currentPage, req);
        List<Tour> tours = TourDAO.getFilteredTours(paginator.getLimit(), paginator.getActivePage().getOffset());//paginator);

        req.setAttribute("paginator", paginator);
//        //List<Tour> tours = TourDAO.getAllTours();

        req.setAttribute("tours", tours);

        getServletContext().getRequestDispatcher("/JSP/tours/tour_list.jsp").forward(req, resp);
       // resp.sendRedirect(req.getContextPath() + "/account");
    }
}
