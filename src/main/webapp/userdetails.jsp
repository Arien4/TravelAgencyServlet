<%--
  Created by IntelliJ IDEA.
  User: arien
  Date: 2/3/2022
  Time: 4:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<jsp:include page="head_section.jsp" />
<body>
<jsp:include page="header.jsp" />
<h1>User <%= request.getParameter("email") %> successfully registered!</h1>
</body>
</html>
