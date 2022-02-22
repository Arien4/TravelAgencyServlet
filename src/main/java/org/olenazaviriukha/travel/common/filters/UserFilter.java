package org.olenazaviriukha.travel.common.filters;


import org.olenazaviriukha.travel.users.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserFilter implements Filter {



    public static final List<String> ANONYMOUS_PATH = Arrays.asList("/main", "/login", "/register", "/tour", "/hotel");
    public static final List<String> USER_PATH = Stream.concat(
            Stream.of("/account", "/logout"), ANONYMOUS_PATH.stream()).collect(Collectors.toList());
    public static final List<String> MANAGER_PATH = Stream.concat(
            Stream.of("/tour_edit", "/tours", "/account", "/ordered_tours", "/ordered_tour"), USER_PATH.stream()).collect(Collectors.toList());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) servletRequest;
            HttpServletResponse resp = (HttpServletResponse) servletResponse;
            String serverPath = req.getServletPath();

            // redirecting to 404 if the page is not allowed
            User user = null;
            HttpSession session = req.getSession(false);
            if (session != null) {
                user = (User) session.getAttribute("user");
            }

            if (((user == null || user.isBlocked()) && !ANONYMOUS_PATH.contains(serverPath)) ||
                    (user != null && !user.isAdmin() && !user.isManager() && !USER_PATH.contains(serverPath)) ||
                    (user != null && user.isManager() && !MANAGER_PATH.contains(serverPath))
            ) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }


//            if (ADMIN_PATH.contains(req.getServletPath())) {
//                 session = req.getSession(false);
//                if (session != null) {
//                    user = (User) session.getAttribute("user");
//                }
//                if (user == null || !(user.isAdmin() || user.isManager())) {
//                    req.getRequestDispatcher("/404.html").forward(servletRequest, servletResponse);
//                    return;
//                }
//            }
        } filterChain.doFilter(servletRequest, servletResponse);
    }
}
