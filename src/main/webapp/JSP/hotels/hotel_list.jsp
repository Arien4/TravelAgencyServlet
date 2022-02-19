<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: arien
  Date: 2/8/2022
  Time: 10:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<jsp:include page="../../head_section.jsp" />
<body>
<jsp:include page="../../header.jsp" />
<div class="container">
<table class="table table-striped table-hover">
    <caption>Hotel list</caption>
    <thead>
        <tr>
            <th scope="col" class="col-3">Name</th>
            <th scope="col" class="col">Type</th>
            <th scope="col" class="col-8">Description</th>
            <th scope="col">Edit</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="hotel" items="${hotels}">
            <tr>
                <td><a href="<%= request.getContextPath() %>/hotel?hotel_id=${hotel.id}">${hotel.name}</a></td>
                <td>${hotel.hotelType}*</td>
                <td>${hotel.description}</td>
                <td><i class="bi bi-pencil-square"></i><a href="<%= request.getContextPath() %>/hotel_edit?hotel_id=${hotel.id}">Edit</a></td>
            </tr>

        </c:forEach>
    </tbody>
</table>
</div>
</body>
</html>
