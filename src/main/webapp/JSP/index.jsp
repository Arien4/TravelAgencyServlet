<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pg" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>
<html>
<jsp:include page="../head_section.jsp"/>
<body>
<jsp:include page="../header.jsp"/>
<div class="container">
    <header>
        <h1><fmt:message key="main.header.title"/></h1>
    </header>

    <div class="md-3">
        <form class="row row-cols-12 row-md-12" method="get">
            <div class="col">
                <div class="input-group">
                    <span class="input-group-text"><fmt:message key="main.filter.tourType"/></span>
                    <select class="form-select" name="tour_type">
                        <option></option>
                        <c:forEach var="tourType" items="${tour_types}">
                            <option value="${tourType}" ${tour_type == tourType ? 'selected="selected"' : ''}>
                                <fmt:message key="main.filter.value.tour.${tourType.getFriendlyName()}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="col-4">
                <div class="input-group">
                    <span class="input-group-text"><fmt:message key="main.filter.priceMin"/> </span>
                    <input type="number" class="form-control" name="min_price" min="0" value="${min_price}"/>
                    <span class="input-group-text"><fmt:message key="main.filter.priceMax"/></span>
                    <input type="number" class="form-control" name="max_price" min="0" value="${max_price}"/>
                </div>
            </div>
            <div class="col">
                <div class="input-group">
                    <span class="input-group-text"><fmt:message key="main.filter.guestsNumber"/> </span>
                    <input type="number" class="form-control" name="guests_number" min="1" max="10"
                           value="${guests_number}"/>
                </div>
            </div>
            <div class="col">
                <div class="input-group">
                    <span class="input-group-text"><fmt:message key="main.filter.hotelType"/></span>
                    <select class="form-select" name="hotel_type">
                        <option></option>
                        <option value="none" ${hotel_type == 'none' ? 'selected="selected"' : ''}>
                            <fmt:message key="main.filter.value.noHotel"/></option>
                        <option value="0" ${hotel_type == '0' ? 'selected="selected"' : ''}>
                            <fmt:message key="main.filter.value.notRated"/>
                        </option>
                        <c:set var="hotel_type_loop" value="_${hotel_type}_"/>
                        <c:forEach begin="1" end="5" varStatus="loop">
                            <c:set var="count" value="_${loop.count}_"/>
                            <option value="${loop.count}" ${hotel_type_loop eq count ? 'selected="selected"' : ''}>
                                    ${loop.count}*
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="col col-md-auto">
                <button class="btn btn-secondary" type="submit"><fmt:message key="main.filter.btnSubmit"/></button>
            </div>
        </form>
    </div>

    <div class="row row-cols-1 row-cols-md-3 g-4">
        <c:forEach var="tour" items="${tours}">
            <div class="col">
                <div class="card h-100">
                    <img src="<%= request.getContextPath() %>/images/tours/defaults/${tour.tourType.getFriendlyName()}.jpg"
                         class="card-img-top" alt="${tour.name} ${fn:substring(tour.description, 0, 100)}"/>
                    <c:if test="${tour.hot}">
                        <div class="card-img-overlay">
                            <h6 class="text-end"><span class="badge bg-danger text-white">
                            <i class="bi bi-thermometer-sun"></i>HOT!</span></h6>
                        </div>
                    </c:if>
                    <div class="card-body">
                        <h5 class="card-title">${tour.name}</h5>
                        <p class="card-text">${tour.price} <fmt:message key="main.card.uah"/></p>
                        <p class="card-text">
                            <c:if test="${tour.guestsNumber > 1}">
                                <i class="bi bi-people-fill"></i>
                            </c:if>
                            <c:if test="${tour.guestsNumber == 1}">
                                <i class="bi bi-person-fill"></i>
                            </c:if>${tour.guestsNumber}
                        </p>
                        <c:if test="${tour.hotel != null}">
                            <p class="card-text"><i class="bi bi-building"></i> ${tour.hotel.name}
                                <c:if test="${ tour.hotel.hotelType > 0 }">${tour.hotel.hotelType}*</c:if></p>
                        </c:if>
                        <c:url value="${request.getContextPath()}/tour" var="editTourLink">
                            <c:param name="tour_id" value="${tour.id}"/>
                        </c:url>
                        <a href="${editTourLink}" class="stretched-link">
                            <fmt:message key="main.card.viewTour"/>
                        </a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<pg:paginator paginator="${paginator}"/>
</body>
</html>
