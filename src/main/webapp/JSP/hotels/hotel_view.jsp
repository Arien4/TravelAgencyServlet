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
<jsp:include page="../../head_section.jsp"/>
<body>
<jsp:include page="../../header.jsp"/>
<div class="container">

<h2>${hotel.name}</h2>
    <div>${hotel.hotelType}*</div>
    <div>${hotel.description}</div>
    <c:if test="${sessionScope.user.isAdmin()}">
        <div><i class="bi bi-pencil-square"></i><a
                href="<%= request.getContextPath() %>/hotel_edit?hotel_id=${hotel.id}">Edit</a></div>
    </c:if>

</div>
</body>
</html>
