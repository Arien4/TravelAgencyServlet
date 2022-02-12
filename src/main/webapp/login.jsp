<%--
  Created by IntelliJ IDEA.
  User: arien
  Date: 2/6/2022
  Time: 5:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fmt"%>--%>
<!DOCTYPE html>
<html>
<jsp:include page="head_section.jsp"/>

<body>
<jsp:include page="header.jsp"/>
<div class="text-center container">
    <main class="form-signin">
        <form method="post">
            <h1 class="h3 mb-3 fw-normal">Please sign in</h1>

            <div class="form-floating">
                <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com" name="email">
                <label for="floatingInput">Email address</label>
            </div>
            <div class="form-floating">
                <input type="password" class="form-control" id="floatingPassword" placeholder="Password"
                       name="password">
                <label for="floatingPassword" ${requestScope.error != null ? 'class="is-invalid"' : ''}>Password</label>
                <c:if test="${requestScope.error != null}">
                    <div class="invalid-feedback">
                        <c:out value="${requestScope.error}"/>
                    </div>
                </c:if>
            </div>

            <div class="checkbox mb-3">
                <input class="form-check-input" type="checkbox" name="remember"
                       id="rememberInput" ${requestScope.remember}> Remember me
            </div>
            <button class="w-100 btn btn-lg btn-primary" type="submit">Sign in</button>

        </form>
    </main>

</div>
</body>
</html>