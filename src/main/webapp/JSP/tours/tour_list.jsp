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
<div class="container">
<table class="table table-striped table-hover">
    <caption>Tour list</caption>
    <thead>
        <tr>
            <th scope="col" class="col">Name</th>
            <th scope="col" class="col">Type</th>
            <th scope="col" class="col">Hotel</th>
            <th scope="col" class="col">Guests</th>
            <th scope="col" class="col">Starts</th>
            <th scope="col" class="col">Ends</th>
            <th scope="col" class="col">Price</th>
            <th scope="col" class="col">Max Discount</th>
            <th scope="col" class="col">Discount Step</th>
            <th scope="col" class="col">Hot</th>
            <th scope="col" class="col-2">Description</th>
            <th scope="col">Edit</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="tour" items="${tours}">
            <tr>
                <td><a href="<%= request.getContextPath() %>/tour?tour_id=${tour.id}">${tour.name}</a></td>
                <td>${tour.tourType}</td>
                <td><a href="<%= request.getContextPath() %>/hotel?hotel_id=${tour.hotelId}">${tour.hotel.name}</a></td>
                <td>${tour.guestsNumber}</td>
                <td>${tour.startDay}</td>
                <td>${tour.endDay}</td>
                <td>${tour.price}</td>
                <td>${tour.maxDiscount}</td>
                <td>${tour.discountStep}</td>
                <td><i class="bi bi-${ tour.hot ? 'check-' : '' }square"></i></td>
                <td>${fn:substring(tour.description, 0, 100)}</td>
                <td><i class="bi bi-pencil-square"></i><a href="<%= request.getContextPath() %>/tour_edit?tour_id=${tour.id}">Edit</a></td>
            </tr>

        </c:forEach>
    </tbody>
</table>
</div>
<pg:paginator paginator="${paginator}"/>
</body>
</html>
