<%--
  Created by IntelliJ IDEA.
  User: arien
  Date: 2/4/2022
  Time: 3:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<jsp:include page="../../head_section.jsp"/>
<body>
<jsp:include page="../../header.jsp"/>
<div class="container">
    <h3 class="text-center">List of Users</h3>

    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>Email</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Role</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}">

            <tr>
                <td><c:out value="${user.id}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.firstName}"/></td>
                <td><c:out value="${user.lastName}"/></td>
                <td><c:out value="${user.role.name}"/></td>
                <td><a href="<%= request.getContextPath() %>/user/edit?user_id=<c:out value='${user.id}' />">Edit</a>
            </tr>
        </c:forEach>

        </tbody>

    </table>
</div>

</body>
</html>