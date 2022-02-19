<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>
<html>
<jsp:include page="../../head_section.jsp"/>
<body>
<jsp:include page="../../header.jsp"/>
<div class="container">
    <h3 class="text-center"><fmt:message key="users.list.ListOfUsers"/></h3>

    <table class="table table-bordered">
        <thead>
        <tr>
            <th><fmt:message key="users.list.ID"/></th>
            <th><fmt:message key="users.list.Email"/></th>
            <th><fmt:message key="users.list.FirstName"/></th>
            <th><fmt:message key="users.list.LastName"/></th>
            <th><fmt:message key="users.list.Role"/></th>
            <th><fmt:message key="users.list.Actions"/></th>
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
                <td><a href="<%= request.getContextPath() %>/user/edit?user_id=<c:out value='${user.id}' />"><fmt:message key="users.list.Action.Edit"/></a>
            </tr>
        </c:forEach>

        </tbody>

    </table>
</div>

</body>
</html>