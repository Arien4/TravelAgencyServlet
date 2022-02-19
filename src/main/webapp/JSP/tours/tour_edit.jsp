<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html>
<jsp:include page="../../head_section.jsp"/>
<body>
<jsp:include page="../../header.jsp"/>
<div class="container">
    <c:choose>
        <c:when test="${tour == null || tour.id ==null}">
            <h1>Add New Tour Form</h1>
        </c:when>
        <c:when test="${tour.id != null}">
            <h1>Edit Tour Form</h1>
        </c:when>
    </c:choose>

    <form class="needs-validation" method="post" enctype="application/x-www-form-urlencoded">
        <input name="tour_id" type="hidden" value="${tour.id}">
        <div class="mb-3">
            <label for="nameInput" class="form-label">Name</label>
            <div class="input-group">
                <input type="text" class="form-control" id="nameInput" name="name" value="${tour.name}"/>
            </div>
        </div>
        <div class="mb-3">
            <label for="tourTypeSelect" class="form-label">Tour type</label>

            <select class="form-select form-select-lg" name="tour_type" id="tourTypeSelect">
                <c:forEach var="item" items="${tour_types}">
                    <option value="${item}" ${tour != null && item == tour.tourType ? 'selected="true"' : ''}>${item}</option>
                </c:forEach>

            </select>
        </div>
        <div class="mb-3">
            <label for="hotelSelect" class="form-label">Hotel</label>

            <select class="form-select form-select-lg" name="hotel_id" id="hotelSelect">
                <option></option>
                <c:forEach var="item" items="${hotels}">
                    <option value="${item.id}" ${hotel != null && hotel.id == item.id ? 'selected="true"' : ''}>${item.name}</option>
                </c:forEach>

            </select>
        </div>
        <div class="mb-3">
            <label for="guestNumberInput" class="form-label">Number of guests</label>

            <input name="guests_number" id="guestNumberInput"
                   class="form-control ${errors.guests_number != null ? 'is-invalid' : ''}" type="number" min="1"
                   max="10" value="${tour.guestsNumber}"/>
            <c:if test="${errors.guests_number != null}">
                <div class="invalid-feedback">
                    <c:out value="${errors.guests_number}"/>
                </div>
            </c:if>

        </div>
        <div class="mb-3 input-group">
            <span class="input-group-text">Start Date</span>
            <input class="form-control ${errors.days != null || errors.start_day != null ? 'is-invalid' : ''}"
                   name="start_day"
                   id="startDateInput" type="date" value="${tour.startDay}"/>
            <span class="input-group-text">End Date</span>
            <input class="form-control ${errors.days != null || errors.end_day != null ? 'is-invalid' : ''}"
                   name="end_day" id="endDateInput"
                   type="date" value="${tour.endDay}"/>
            <c:if test="${errors.start_day != null || errors.start_day != null && errors.end_day != null}">
                <div class="invalid-feedback">
                    <c:out value="${errors.start_day}"/>
                </div>
            </c:if>
            <c:if test="${errors.end_day != null && errors.start_day == null}">
                <div class="invalid-feedback">
                    <c:out value="${errors.end_day}"/>
                </div>
            </c:if>
            <c:if test="${errors.containsKey('days')}">
                <div class="invalid-feedback">
                    <c:out value="${errors['days']}"/>
                </div>
            </c:if>
        </div>

        <div class="mb-3">
            <label for="priceInput" class="form-label">Price</label>

            <input class="form-control ${errors.price != null ? 'is-invalid' : ''}" name="price" id="priceInput"
                   type="number" min="0" value="${tour.price}"/>
            <c:if test="${errors.price != null}">
                <div class="invalid-feedback">
                    <c:out value="${errors.price}"/>
                </div>
            </c:if>
        </div>

        <div class="mb-3">
            <label for="maxDiscountInput" class="form-label">Max Discount</label>

            <input class="form-control ${errors.max_discount != null ? 'is-invalid' : ''}" name="max_discount"
                   id="maxDiscountInput" type="number" min="0" value="${tour != null ? tour.maxDiscount : 20}"/>
            <c:if test="${errors.max_discount != null}">
                <div class="invalid-feedback">
                    <c:out value="${errors.max_discount}"/>
                </div>
            </c:if>
        </div>

        <div class="mb-3">
            <label for="discountStepInput" class="form-label">Discount Step</label>

            <input class="form-control ${errors.discount_step != null ? 'is-invalid' : ''}" name="discount_step"
                   id="discountStepInput" type="number" min="0" value="${tour != null ? tour.discountStep : 1}"/>
            <c:if test="${errors.discount_step != null}">
                <div class="invalid-feedback">
                    <c:out value="${errors.discount_step}"/>
                </div>
            </c:if>
        </div>

        <div class="mb-3">
            <label for="hotInput" class="form-label">Hot</label>
            <input class="form-check-input" name="hot" id="hotInput"
                   type="checkbox" ${tour != null && tour.hot ? 'checked="checked"' : ''}/>
        </div>


        <div class="mb-3">
            <label for="descriptionInput" class="form-label">Description</label>
            <textarea class="form-control" id="descriptionInput" name="description">${tour.description}</textarea>
        </div>

        <div class="mb-3">
            <button class="btn btn-primary" type="submit">Submit</button>
        </div>
    </form>
</div>
</body>
</html>