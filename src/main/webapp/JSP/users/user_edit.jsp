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
        <c:when test="${_action == 'register'}">
            <h1>User Register Form</h1>
        </c:when>
        <c:when test="${_action == 'edit'}">
            <h1>Edit user</h1>
        </c:when>
    </c:choose>

    <form class="needs-validation" action="<%= request.getContextPath() %>${_path}" method="post"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="user_id" value="${user.id}">
        <input type="hidden" name="_action" value="${_action}">
        <div class="mb-3">
            <label for="emailInput" class="form-label">Email</label>
            <div class="input-group has-validation">
                <input type="email" class="form-control ${errors.containsKey('email') ? 'is-invalid' : ''}"
                       id="emailInput" name="email" value="${user.email}"/>
                <c:if test="${errors.containsKey('email')}">
                    <div class="invalid-feedback">
                        <c:out value="${errors['email']}"/>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="mb-3">
            <label for="firstNameInput" class="form-label">First Name</label>
            <div class="input-group has-validation">
                <input type="text" class="form-control ${errors.containsKey('first_name') ? 'is-invalid' : ''}"
                       id="firstNameInput" name="first_name" value="${user.firstName}"/>
                <c:if test="${errors.containsKey('first_name')}">
                    <div class="invalid-feedback">
                        <c:out value="${errors['first_name']}"/>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="mb-3">
            <label for="lastNameInput" class="form-label">Last Name</label>
            <div class="input-group has-validation">
                <input type="text" class="form-control ${errors.containsKey('last_name') ? 'is-invalid' : ''}"
                       id="lastNameInput" name="last_name" value="${user.lastName}"/>
                <c:if test="${errors.containsKey('last_name')}">
                    <div class="invalid-feedback">
                        <c:out value="${errors['last_name']}"/>
                    </div>
                </c:if>
            </div>
        </div>
        <c:if test="${_action == 'register'}">
            <div class="mb-3">
                <label for="passwordInput" class="form-label">Password</label>
                <div class="input-group has-validation">
                    <input type="password" class="form-control ${errors.containsKey('password') ? 'is-invalid' : ''}"
                           id="passwordInput" name="password"/>
                    <c:if test="${errors.containsKey('password')}">
                        <div class="invalid-feedback">
                            <c:out value="${errors['password']}"/>
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
        </c:if>
        <c:if test="${_action == 'edit'}">
            <div class="mb-3">
                <label for="roleSelect" class="form-label">Role</label>
                <select class="form-select" id="roleSelect" name="role">
                    <c:forEach var="role" items="${roles}">
                        <option value="${role.id}" ${ role.id == user.roleId ? 'selected="selected"' : ''}>
                            ${role.name}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <input class="form-check-input" name="is_blocked" ${user.blocked ? 'checked="checked"' : ''}
                       type="checkbox" id="isBlockedInput"/>
                <label for="isBlockedInput" class="form-label">Is Blocked</label>
            </div>
        </c:if>

        <div class="mb-3">
            <button class="btn btn-primary" type="submit">Submit</button>
        </div>
    </form>
</div>
</body>
</html>