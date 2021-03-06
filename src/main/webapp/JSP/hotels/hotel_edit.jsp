<%--
  Created by IntelliJ IDEA.
  User: arien
  Date: 2/3/2022
  Time: 4:34 PM
  To change this template use File | Settings | File Templates.
--%>
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
        <c:when test="${hotel == null || hotel.id == null}">
            <h1>Add New Hotel Form</h1>
        </c:when>
        <c:when test="${hotel.id != null}">
            <h1>Edit Hotel Form</h1>
        </c:when>
    </c:choose>

    <form class="needs-validation" action="<%= request.getContextPath() %>/hotel_add" method="post"
          enctype="multipart/form-data">
        <input name="hotel_id" type="hidden" value="${hotel.id}">
        <div class="mb-3">
            <label for="nameInput" class="form-label">Name</label>
            <div class="input-group has-validation">
                <input type="text" class="form-control ${errors.containsKey('name') ? 'is-invalid' : ''}" id="nameInput"
                       name="name" value="${hotel.name}"/>
                <c:if test="${errors.containsKey('name')}">
                    <div class="invalid-feedback">
                        <c:out value="${errors['name']}"/>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="mb-3">
            <label for="hotelTypeInput" class="form-label">Hotel type</label>
            <div class="input-group has-validation">
                <input type="number" class="form-control ${errors.containsKey('hotel_type') ? 'is-invalid' : ''}"
                       id="hotelTypeInput" name="hotel_type" value="${hotel.hotelType}" min="0" max="5"/>
                <c:if test="${errors.containsKey('hotel_type')}">
                    <div class="invalid-feedback">
                        <c:out value="${errors['hotel_type']}"/>
                    </div>
                </c:if>
            </div>
        </div>

        <div class="mb-3">
            <label for="descriptionInput" class="form-label">Description</label>
            <div class="input-group has-validation">
                <textarea class="form-control" id="descriptionInput" name="description">${hotel.description}</textarea>
            </div>
        </div>

<%--        <div class="mb-3">--%>
<%--            <label for="hotelImageInput" class="form-label">Photo</label>--%>
<%--            <input id="hotelImageInput" type="file" class="form-control" name="image">--%>
<%--        </div>--%>
        <div class="mb-3">
            <img src="<%= request.getContextPath() %>/${ hotel.image != null ? hotel.image : 'images/hotels/default.jpg'}"
                 height="250" width="334" alt="${hotel.name}" class="rounded d-block"/>

        </div>

        <div class="mb-3">
            <button class="btn btn-primary" type="submit">Submit</button>
        </div>
    </form>
</div>
</body>
</html>