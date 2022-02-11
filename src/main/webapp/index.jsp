<%@ page import="org.olenazaviriukha.travel.utils.SecurityUtils" %>
<%@ page import="org.olenazaviriukha.travel.entity.User" %>
<%@ page import="org.olenazaviriukha.travel.dao.UserDAO" %>
<%@ page import="org.olenazaviriukha.travel.entity.Hotel" %>
<%@ page import="org.olenazaviriukha.travel.dao.HotelDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<jsp:include page="head_section.jsp" />
<body>
<jsp:include page="header.jsp" />
<h2>Hello World! Travel Agency Servlet Project</h2>
<p>
<img src="<%= request.getContextPath() %>/images/hotels/zanzibar.jpg" width="334" height="250"/>
</p>
</body>
</html>
