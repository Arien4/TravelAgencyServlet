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
    <h1>LIMITED</h1>
    <form class="needs-validation" method="post" enctype="application/x-www-form-urlencoded">
        <input name="tour_id" type="hidden" value="${tour.id}">
        <div class="mb-3">
            <label for="nameInput" class="form-label">Name</label>
            <div class="input-group">
                <input type="text" readonly="readonly" class="form-control" id="nameInput" name="name"
                       value="${tour.name}"/>
            </div>
        </div>
        <div class="mb-3">
            <label for="tourTypeSelect" class="form-label">Tour type</label>

            <input class="form-control" name="tour_type" id="tourTypeSelect" readonly="readonly" value="${tour.tourType}"/>

        </div>
        <div class="mb-3">
            <label for="hotelInput" class="form-label">Hotel</label>

            <input class="form-control" name="hotel_id" id="hotelInput" readonly="readonly" value="${tour.hotel.name}"/>


        </div>
        <div class="mb-3">
            <label for="guestNumberInput" class="form-label">Number of guests</label>

            <input name="guests_number" id="guestNumberInput" readonly="readonly"
                   class="form-control" type="number" min="1"
                   max="10" value="${tour.guestsNumber}"/>
        </div>
        <div class="mb-3 input-group">
            <span class="input-group-text">Start Date</span>
            <input class="form-control" readonly="readonly"
                   name="start_day"
                   id="startDateInput" type="date" value="${tour.startDay}"/>
            <span class="input-group-text">End Date</span>
            <input class="form-control" readonly="readonly"
                   name="end_day" id="endDateInput"
                   type="date" value="${tour.endDay}"/>

        </div>

        <div class="mb-3">
            <label for="priceInput" class="form-label">Price</label>

            <input class="form-control" readonly="readonly" name="price" id="priceInput"
                   type="number" min="0" value="${tour.price}"/>
        </div>

        <div class="mb-3">
            <label for="maxDiscountInput" class="form-label">Max Discount</label>

            <input class="form-control" name="max_discount"
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
            <textarea class="form-control" id="descriptionInput" name="description" readonly="readonly">${tour.description}</textarea>
        </div>

        <div class="mb-3">
            <button class="btn btn-primary" type="submit">Submit</button>
        </div>
    </form>
</div>
</body>
</html>