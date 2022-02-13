package org.olenazaviriukha.travel.users.controllers;

import org.olenazaviriukha.travel.controller.exceptions.UserValidationException;
import org.olenazaviriukha.travel.dao.DuplicateKeyException;
import org.olenazaviriukha.travel.users.dao.RoleDAO;
import org.olenazaviriukha.travel.users.dao.UserDAO;
import org.olenazaviriukha.travel.users.entity.Role;
import org.olenazaviriukha.travel.users.entity.User;
import org.olenazaviriukha.travel.common.utils.SecurityUtils;
import org.olenazaviriukha.travel.common.utils.ValidationUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet({"/register", "/user/edit"})
public class UserEditServlet extends HttpServlet {
    private static final String SERVLET_PATH_EDIT = "/user/edit";
    private static final String ACTION_EDIT = "edit";
    private static final String ACTION_REGISTER = "register";
    private static final String REQUEST_KEY_ACTION = "_action";
    private static final String REQUEST_KEY_PATH = "_path";
    private static final String EMAIL = "email";
    private static final String USER_ID = "user_id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_REPEAT = "password_repeat";
    private static final String ROLE = "role";
    private static final String IS_BLOCKED = "is_blocked";

    private void setDefaultRequestAttributes(HttpServletRequest req) {
        String path = req.getServletPath();
        if (path.equals(SERVLET_PATH_EDIT)) {
            req.setAttribute(REQUEST_KEY_ACTION, ACTION_EDIT);
        } else {
            req.setAttribute(REQUEST_KEY_ACTION, ACTION_REGISTER);
        }
        req.setAttribute(REQUEST_KEY_PATH, path);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if (path.equals(SERVLET_PATH_EDIT)) {
            User user = null;
            try {
                user = UserDAO.getUserById(Integer.valueOf(req.getParameter(USER_ID)));
            } catch (NumberFormatException | NullPointerException e) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                req.getRequestDispatcher("/JSP/users/user_not_found.jsp").forward(req, resp);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<Role> roles = RoleDAO.getAllRoles();
            req.setAttribute("user", user);
            req.setAttribute("roles", roles);
        }
        setDefaultRequestAttributes(req);
        req.getRequestDispatcher("/JSP/users/user_edit.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        User user = null;
        try {
            user = getUserFromRequest(req);
        } catch (UserValidationException e) {
            req.setAttribute("errors", e.getErrors());
            req.setAttribute("user", e.getUser());
            req.setAttribute("roles", RoleDAO.getAllRoles());
            setDefaultRequestAttributes(req);
            getServletContext().getRequestDispatcher("/JSP/users/user_edit.jsp").forward(req, resp);
            return;
        } catch (Exception e) {
            // Error reading user from request
            e.printStackTrace();
        }
        try {
            if (user.getId() != null) UserDAO.updateUser(user);
            else UserDAO.registerUser(user);
        } catch (DuplicateKeyException e) {
            Map<String, String> errors = new HashMap<>();
            errors.put(e.getParam(), e.getMessage());
            req.setAttribute("errors", errors);
            req.setAttribute("user", user);
            req.setAttribute("roles", RoleDAO.getAllRoles());
            setDefaultRequestAttributes(req);
            getServletContext().getRequestDispatcher("/JSP/users/user_edit.jsp").forward(req, resp);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (path.equals(SERVLET_PATH_EDIT)) {
            resp.sendRedirect(req.getContextPath() + "/users");
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/login");
    }

    /**
     * @param req request from doPost()
     * @return user
     * @throws Exception if input is incorrect
     */
    private User getUserFromRequest(HttpServletRequest req) throws Exception {
        User user = new User();
        Map<String, String> errors = new HashMap<>();
        String action = req.getParameter(REQUEST_KEY_ACTION);

        try {
            user.setId(Integer.valueOf(req.getParameter(USER_ID)));
        } catch (NumberFormatException e) {
        }

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

        if (action.equals(ACTION_REGISTER)) {
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
        }

        if (action.equals(ACTION_EDIT)) {
            user.setRoleId(Integer.valueOf(req.getParameter(ROLE)));
            String isBlocked = req.getParameter(IS_BLOCKED);
            if (isBlocked != null && isBlocked.equals("on")) user.setBlocked(true);
            else user.setBlocked(false);
        }

        if (errors.isEmpty()) return user;
        throw new UserValidationException(user, errors);
    }
}
