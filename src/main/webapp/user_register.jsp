<%--
  Created by IntelliJ IDEA.
  User: arien
  Date: 2/3/2022
  Time: 4:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html>
<jsp:include page="head_section.jsp" />
<body>
<jsp:include page="header.jsp" />
<div class="container">
    <h1>User Register Form</h1>
    <%--    <form action="<%= request.getContextPath() %>/register" method="post">--%>
    <%--    <form action="<%= request.getContextPath() %>/userdetails.jsp" method="post">--%>
    <form class="needs-validation" action="<%= request.getContextPath() %>/register" method="post" enctype="application/x-www-form-urlencoded">
        <div class="mb-3">
            <label for="emailInput" class="form-label">Email</label>
            <div class="input-group has-validation">
                <input type="email" class="form-control ${errors.containsKey('email') ? 'is-invalid' : ''}" id="emailInput" name="email" value="${user.email}"/>
<%--                <input type="text" class="form-control ${errors.containsKey('email') ? 'is-invalid' : ''}" id="emailInput" name="email" value="${user.email}"/>--%>
                <c:if test="${errors.containsKey('email')}">
                    <div class="invalid-feedback">
                        <c:out value="${errors['email']}" />
                    </div>
                </c:if>
            </div>
        </div>
        <div class="mb-3">
            <label for="firstNameInput" class="form-label">First Name</label>
            <div class="input-group has-validation">
                <input type="text" class="form-control ${errors.containsKey('first_name') ? 'is-invalid' : ''}" id="firstNameInput" name="first_name" value="${user.firstName}"/>
                <c:if test="${errors.containsKey('first_name')}">
                    <div class="invalid-feedback">
                        <c:out value="${errors['first_name']}" />
                    </div>
                </c:if>
            </div>
        </div>
        <div class="mb-3">
            <label for="lastNameInput" class="form-label">Last Name</label>
            <div class="input-group has-validation">
                <input type="text" class="form-control ${errors.containsKey('last_name') ? 'is-invalid' : ''}" id="lastNameInput" name="last_name" value="${user.lastName}"/>
                <c:if test="${errors.containsKey('last_name')}">
                    <div class="invalid-feedback">
                        <c:out value="${errors['last_name']}" />
                    </div>
                </c:if>
            </div>
        </div>
        <div class="mb-3">
            <label for="passwordInput" class="form-label">Password</label>
            <div class="input-group has-validation">
                <input type="password" class="form-control ${errors.containsKey('password') ? 'is-invalid' : ''}" id="passwordInput" name="password"/>
                <c:if test="${errors.containsKey('password')}">
                    <div class="invalid-feedback">
                        <c:out value="${errors['password']}" />
                    </div>
                </c:if>
            </div>
        </div>
        <div class="mb-3">
            <label for="passwordRepeatInput" class="form-label">Repeat Password</label>
            <div class="input-group has-validation">
                <input type="password" class="form-control" id="passwordRepeatInput" name="password_repeat"/>

            </div>
        </div>
        <div class="mb-3">
            <button class="btn btn-primary" type="submit">Submit</button>
        </div>
    </form>
</div>
</body>
</html>