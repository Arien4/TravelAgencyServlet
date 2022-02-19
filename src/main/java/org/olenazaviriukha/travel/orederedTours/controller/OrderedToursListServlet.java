package org.olenazaviriukha.travel.orederedTours.controller;

import org.olenazaviriukha.travel.common.paginator.Paginator;
import org.olenazaviriukha.travel.orederedTours.dao.OrderedTourDAO;
import org.olenazaviriukha.travel.orederedTours.entity.OrderedTour;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ordered_tours")
public class OrderedToursListServlet extends HttpServlet {
    private static final int TOURS_PER_PAGE = 10;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int currentPage = 1;
        try {
            currentPage = Integer.parseInt(req.getParameter(Paginator.QUERY_PARAM_NAME));
        } catch (NumberFormatException ignored) {
        }

        int orderedTourCount = OrderedTourDAO.getFilteredCount();
        Paginator paginator = new Paginator(TOURS_PER_PAGE, orderedTourCount, currentPage, req);
        List<OrderedTour> orderedTours = OrderedTourDAO.getOrderedTours(paginator.getLimit(), paginator.getActivePage().getOffset());
        req.setAttribute("orderedTours", orderedTours);
        req.setAttribute("paginator", paginator);
        getServletContext().getRequestDispatcher("/JSP/ordered_tours/ordered_tour_list.jsp").forward(req, resp);
    }


}
