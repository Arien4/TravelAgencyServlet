<%--
  Created by IntelliJ IDEA.
  User: arien
  Date: 2/12/2022
  Time: 11:40 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html>
<jsp:include page="../../head_section.jsp" />
<body>
<jsp:include page="../../header.jsp" />
<div class="container">
    <div class="md-3">
        <h1>404. User not found!</h1>
        <p class="lead">The <mark>user_id</mark> query parameter is incorrect or missed.</p>
    </div>
</div>
</body>
</html>
