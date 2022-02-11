package org.olenazaviriukha.travel.controller;

import org.olenazaviriukha.travel.controller.exceptions.UserValidationException;
import org.olenazaviriukha.travel.dao.DuplicateKeyException;
import org.olenazaviriukha.travel.dao.UserDAO;
import org.olenazaviriukha.travel.entity.User;
import org.olenazaviriukha.travel.utils.SecurityUtils;
import org.olenazaviriukha.travel.utils.ValidationUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final String EMAIL = "email";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_REPEAT = "password_repeat";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/user_register.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = null;
        try {
            user = getUserFromRequest(req);
        } catch (UserValidationException e) {
            req.setAttribute("errors", e.getErrors());
            req.setAttribute("user", e.getUser());
            getServletContext().getRequestDispatcher("/user_register.jsp").forward(req, resp);
            return;
        } catch (Exception e) {
            // Error reading user from request
            e.printStackTrace();
        }
        try {
            UserDAO.registerUser(user);
        } catch (DuplicateKeyException e) {
            Map<String, String> errors = new HashMap<>();
            errors.put(e.getParam(), e.getMessage());
            req.setAttribute("errors", errors);
            req.setAttribute("user", user);
            getServletContext().getRequestDispatcher("/user_register.jsp").forward(req, resp);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.sendRedirect("login.jsp");
    }

    /**
     *
     * @param req request from doPost()
     * @return user
     * @throws Exception if input is incorrect
     */
    private User getUserFromRequest(HttpServletRequest req) throws Exception {
        User user = new User();
        Map<String, String> errors = new HashMap<>();

        String email = req.getParameter(EMAIL);
        user.setEmail(email.toLowerCase());
        String emailError = ValidationUtils.emailValidationError(email);
        if (emailError != null) errors.put(EMAIL, emailError);

        String firstName = req.getParameter(FIRST_NAME);
        user.setFirstName(firstName);
        String nameError = ValidationUtils.nameValidationError(firstName, true);
        if (nameError != null) errors.put(FIRST_NAME, nameError);

        String lastName = req.getParameter(LAST_NAME);
        user.setLastName(lastName);
        nameError = ValidationUtils.nameValidationError(lastName, false);
        if (nameError != null) errors.put(LAST_NAME, nameError);

        String password = req.getParameter(PASSWORD);
        String passwordRepeat = req.getParameter(PASSWORD_REPEAT);
        String passError = ValidationUtils.blankPasswordError(password);
        if (passError != null) errors.put(PASSWORD, passError);
        passError = ValidationUtils.matchPasswordsError(password, passwordRepeat);
        if (passError != null) {
            errors.put(PASSWORD, passError);
        } else {
            user.setPassword(SecurityUtils.generateStrongPasswordHash(password));
        }

        if (errors.isEmpty()) return user;
        throw new UserValidationException(user, errors);
    }

}
