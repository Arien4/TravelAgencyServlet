<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pg" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>
<html>
<jsp:include page="../../head_section.jsp" />
<body>
<jsp:include page="../../header.jsp" />
<div class="container md-4">
    <c:set var="user" value="${sessionScope.get('user')}"/>
    <h2>${user.firstName} ${user.lastName}</h2>

</div>
<div class="container">
    <h1><fmt:message key="account.table.header.orderedTours"/></h1>

    <table class="table table-striped table-hover">
        <caption>Ordered Tours</caption>
        <thead>
        <tr>
            <th scope="col" class="col">Is Hot</th>
            <th scope="col" class="col">Tour Name</th>
            <th scope="col" class="col">Tour Type</th>
            <th scope="col" class="col">Hotel</th>
            <th scope="col" class="col">Guests</th>
            <th scope="col" class="col">Starts</th>
            <th scope="col" class="col">Ends</th>
            <th scope="col" class="col">Price</th>
            <th scope="col" class="col">Status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="orderedTour" items="${orderedTours}">
            <tr>
                <td><i class="bi bi-${ orderedTour.tour.hot ? 'check-' : '' }square"></i></td>
                <td><a href="<%= request.getContextPath() %>/tour?tour_id=${orderedTour.tour.id}">${orderedTour.tour.name}</a></td>
                <td>${orderedTour.tour.tourType}</td>
                <c:choose>
                    <c:when test="${orderedTour.tour.hotel != null}">
                        <td><a href="<%= request.getContextPath() %>/hotel?hotel_id=${orderedTour.tour.hotelId}">${orderedTour.tour.hotel.name}</a></td>
                    </c:when>
                    <c:otherwise><td></td></c:otherwise>
                </c:choose>
                <td>${orderedTour.tour.guestsNumber}</td>
                <td>${orderedTour.tour.startDay}</td>
                <td>${orderedTour.tour.endDay}</td>
                <td>${orderedTour.getFinalPrice()}</td>
                <td>${orderedTour.status}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
