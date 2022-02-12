<%--
  Created by IntelliJ IDEA.
  User: arien
  Date: 2/7/2022
  Time: 9:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>

<div class="container">
<%--    <c:out value="${sessionScope.locale}"/>--%>
    <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
        <div class="col-md-3 text-end">
            <a href="<%= request.getContextPath() %>/?lang_code=ua" role="button" class="btn btn-outline-primary">Укр</a>
            <a href="<%= request.getContextPath() %>/?lang_code=en" role="button" class="btn btn-outline-primary">En</a>
        </div>


        <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">
            <li><a href="<%= request.getContextPath() %>/" class="nav-link px-2 link-secondary"><fmt:message key="header.nav.Home"/></a></li>
            <li><a href="<%= request.getContextPath() %>/users" class="nav-link px-2 link-dark"><fmt:message key="header.nav.Users"/></a></li>
            <li><a href="<%= request.getContextPath() %>/tour_add" class="nav-link px-2 link-dark"><fmt:message key="header.nav.NewTour"/></a></li>
            <li><a href="<%= request.getContextPath() %>/hotels" class="nav-link px-2 link-dark"><fmt:message key="header.nav.Hotels"/></a></li>
            <li><a href="<%= request.getContextPath() %>/hotel_add" class="nav-link px-2 link-dark"><fmt:message key="header.nav.NewHotels"/></a></li>
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