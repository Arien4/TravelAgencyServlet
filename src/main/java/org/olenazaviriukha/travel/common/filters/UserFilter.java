package org.olenazaviriukha.travel.common.filters;


import org.olenazaviriukha.travel.users.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class UserFilter implements Filter {

    public static final List<String> ADMIN_PATH = Arrays.asList("/tour_add", "/tour_edit", "/tours", "/hotels_add," +
            "/hotels_edit", "/hotels", "/users", "/user?edit", "/account");

    public static final List<String> MANAGER_PATH = Arrays.asList("/tour_edit", "/tours", "/account");

    public static final List<String> USER_PATH = Arrays.asList("/account");
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) servletRequest;

            // redirecting to 404 if the page is not allowed
            User user = null;
            if (ADMIN_PATH.contains(req.getServletPath())) {
                HttpSession session = req.getSession(false);
                if (session != null) {
                    user = (User) session.getAttribute("user");
                }
                if (user == null || !(user.isAdmin() || user.isManager())) {
                    req.getRequestDispatcher("/404.html").forward(servletRequest, servletResponse);
                    return;
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
