<%@tag description="Paginator" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="paginator" required="true" type="org.olenazaviriukha.travel.common.paginator.Paginator" %>

<div class="container mt-4">
    <nav aria-label="paginator">
        <ul class="pagination justify-content-center">
            <c:forEach var="page" items="${paginator.iterator()}">
                <li class="page-item ${page.active ? 'active' : ''}">
                    <a class="page-link" href="${page.getURI()}">${page.getCount()}</a></li>
            </c:forEach>
        </ul>
    </nav>
</div>
