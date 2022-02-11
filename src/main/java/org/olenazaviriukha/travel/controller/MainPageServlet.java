package org.olenazaviriukha.travel.controller;

import org.olenazaviriukha.travel.dao.UserDAO;
import org.olenazaviriukha.travel.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/main")
public class MainPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = UserDAO.getAllUsers();
        req.setAttribute("users", users);
        getServletContext().getRequestDispatcher("/user-list.jsp").forward(req, resp);

    }
}
