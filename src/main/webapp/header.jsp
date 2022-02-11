<%--
  Created by IntelliJ IDEA.
  User: arien
  Date: 2/7/2022
  Time: 9:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
        <a href="/" class="d-flex align-items-center col-md-3 mb-2 mb-md-0 text-dark text-decoration-none">
            <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap"><use xlink:href="#bootstrap"/></svg>
        </a>

        <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">
            <li><a href="#" class="nav-link px-2 link-secondary">Home</a></li>
            <li><a href="#" class="nav-link px-2 link-dark">Features</a></li>
            <li><a href="<%= request.getContextPath() %>/tour_add" class="nav-link px-2 link-dark">New Tour</a></li>
            <li><a href="<%= request.getContextPath() %>/hotels" class="nav-link px-2 link-dark">Hotels</a></li>
            <li><a href="<%= request.getContextPath() %>/hotel_add" class="nav-link px-2 link-dark">New Hotel</a></li>
        </ul>
        <c:if test="${sessionScope.user == null}">
        <div class="col-md-3 text-end">
            <a href="<%= request.getContextPath() %>/login" role="button" class="btn btn-outline-primary me-2">Login</a>
            <a href="<%= request.getContextPath() %>/register" role="button" class="btn btn-primary">Sign-up</a>
        </div>
        </c:if>
        <c:if test="${sessionScope.user != null}">
            <div class="col-md-3 text-end">
                <c:out value="${sessionScope.user.firstName}"/>
                <c:out value="${sessionScope.user.lastName}"/>
                <a href="<%= request.getContextPath() %>/logout" role="button" class="btn btn-outline-primary me-2">Logout</a>
            </div>
        </c:if>
    </header>
</div>