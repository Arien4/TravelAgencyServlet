<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pg" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html>
<jsp:include page="../../head_section.jsp"/>
<body>
<jsp:include page="../../header.jsp"/>
<div class="container">

    <h1><fmt:message key="ordered_tours.header"></fmt:message></h1>

    <form method="post">
        <input name="ordered_tour_id" type="hidden" value="${orderedTour.id}">
        <div class="mb-3">
            <label for="userInput"><fmt:message key="label.user"/></label>
            <input class="form-control" id="userInput" type="text" value="${orderedTour.user.getFullName()}" name="_"
                   disabled="disabled"/>
        </div>
        <div class="mb-3">
            <label for="hotelInput"><fmt:message key="label.hotel"/></label>
            <input class="form-control" id="hotelInput" type="text" value="${orderedTour.tour.hotel.getHotelName()}"
                   name="_" disabled="disabled"/>
        </div>
        <div class="mb-3 input-group">
            <span class="input-group-text"><fmt:message key="label.startDay"/></span>
            <input class="form-control" id="startDayInput" type="date" value="${orderedTour.tour.startDay}" name="_"
                   disabled="disabled"/>
            <span class="input-group-text"><fmt:message key="label.endDay"/></span>
            <input class="form-control" id="endDayInput" type="date" value="${orderedTour.tour.endDay}" name="_"
                   disabled="disabled"/>
        </div>

        <div class="mb-3">
            <label for="guestsNumberInput"><fmt:message key="label.guestsNumber"/></label>
            <input class="form-control" id="guestsNumberInput" type="text" value="${orderedTour.tour.guestsNumber}"
                   name="_" disabled="disabled"/>
        </div>

        <div class="mb-3">
            <label for="statusSelect" class="form-label">Status</label>
            <c:if test="${orderedTour.status eq 'COMPLETED' || orderedTour.status eq 'CANCELLED' }">
                <c:set var="orderStatus">
                    <fmt:message key="orderedTour.form.status.${orderedTour.status}"/>
                </c:set>
                <input disabled="disabled" class="form-control" value="${orderStatus}" id="statusSelect"/>
                <input type="hidden" value="${orderedTour.status}" name="status"/>
            </c:if>
            <c:if test="${orderedTour.status ne 'COMPLETED' && orderedTour.status ne 'CANCELLED' }">
                <select class="form-select form-select-lg" name="status" id="statusSelect">
                    <c:forEach var="item" items="${orderedTourStatusList}">
                        <c:if test="${ orderedTour.isNew() || item ne 'REGISTERED' }">
                            <option value="${item}" ${orderedTour.status == item ? 'selected="true"' : ''}>
                                <fmt:message key="orderedTour.form.status.${item}"/>
                            </option>
                        </c:if>
                    </c:forEach>
                </select>
            </c:if>
        </div>
        <div class="mb-3">
            <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"
                                                      value="${orderedTour.getFinalPrice()}" var="finalPrice"/>
            <label for="finalPriceId"><fmt:message key="label.finalPrice"/></label>
            <input class="form-control" id="finalPriceId" type="text" value="${finalPrice}" name="_"
                   disabled="disabled"/>
            <input id="fixedPriceId" type="hidden" value="${orderedTour.fixedPrice}" name="_"/>
        </div>
        <div class="mb-3">
            <label for="discountInput" class="form-label">Discount:
                <span id="discountLabel">${orderedTour.discount}</span></label>
            <div class="input-group">
                <c:if test="${!orderedTour.isNew()}">
                    <input type="hidden" name="discount" value="${orderedTour.discount}"/>
                </c:if>
                <input type="range" class="form-range" id="discountInput" name="discount${!orderedTour.isNew() ? '_disabled' : ''}"
                       ${!orderedTour.isNew() ? 'disabled="disabled"' : ''}
                       value="${orderedTour.discount}" min="0" max="${orderedTour.tour.maxDiscount}"
                       step="${orderedTour.tour.discountStep}" onchange="updateLabel(this.value);"/>
            </div>
        </div>
        <button type="submit" class="btn btn-primary"><fmt:message key="btn.Save"/></button>
    </form>

</div>
</body>
<script type="application/javascript">
    function updateLabel(val) {
        document.getElementById('discountLabel').innerText = val;
        let finalPrice = parseInt(document.getElementById('fixedPriceId').value) * (100 - parseInt(val)) / 100;
        document.getElementById('finalPriceId').value = finalPrice.toFixed(2);
    }
</script>
</html>