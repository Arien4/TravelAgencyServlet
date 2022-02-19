<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<% request.setCharacterEncoding("UTF-8"); %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html>
<jsp:include page="../../head_section.jsp"/>
<body>
<jsp:include page="../../header.jsp"/>
<div class="container">
    <h2><c:if test="${tour.hot}">
        <span class="badge bg-danger text-white">
                            <i class="bi bi-thermometer-sun"></i>HOT!</span></c:if> ${tour.name}</h2>
    <div><fmt:message key="tour.view.${tour.tourType}"/></div>
    <c:if test="${tour.hotel != null}">
        <div><i class="bi bi-building"></i> <a
                href="<%= request.getContextPath() %>/hotel?hotel_id=${tour.hotelId}">${tour.hotel.name}</a>
            <c:if test="${tour.hotel.hotelType != null}">
                ${tour.hotel.hotelType}*
            </c:if>
        </div>
    </c:if>
    <div><c:if test="${tour.guestsNumber > 1}">
        <i class="bi bi-people-fill"></i>
    </c:if>
        <c:if test="${tour.guestsNumber == 1}">
            <i class="bi bi-person-fill"></i>
        </c:if>${tour.guestsNumber}</div>
    <div><i class="bi bi-calendar3"></i> ${tour.startDay} - ${tour.endDay}</div>
    <div><i class="bi bi-cash"></i> ${tour.price}</div>
    ${sessionScope.user.role.name}
    <c:if test="${(sessionScope.user.isManager()) || (sessionScope.user.isAdmin())}">
        <div style="color: gold">
            <span class="input-group-text"><fmt:message key="tour.view.maxDiscount"/> ${tour.maxDiscount}</span>
            <span class="input-group-text"><fmt:message key="tour.view.discountStep"/> ${tour.discountStep}</span>
        </div>
    </c:if>

    <%--    <div><i class="bi bi-${ tour.hot ? 'check-' : '' }square"></i></div>--%>
    <div>${tour.description}</div>
    <%--    ${sessionScope.user.role.name}--%>
    <c:if test="${sessionScope.user.isManager() || sessionScope.user.isAdmin()}">
        <div><i class="bi bi-pencil-square"></i><a
                href="<%= request.getContextPath() %>/tour_edit?tour_id=${tour.id}">Edit</a></div>
    </c:if>

    <c:if test="${sessionScope.user != null}">
    <div class="mb-3">
        <form method="post">
            <input type="hidden" name="tour_id" value="${tour.id}"/>
            <button class="btn btn-primary" type="submit">Order</button>
        </form>

    </div>
    </c:if>


</div>
</body>
</html>