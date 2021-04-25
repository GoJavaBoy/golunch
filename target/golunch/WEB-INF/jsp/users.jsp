<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<section>
    <a href="${pageContext.request.contextPath}">Home</a>    <br>
    <br>
    <br>
<section>
    <h3>Users: </h3>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>User name</th>
            <th>Email</th>
            <th>Roles</th>
<%--            <th>Active status</th>--%>
            <th>Registered</th>
        </tr>
        </thead>
        <c:forEach items="${users}" var="user">
            <jsp:useBean id="user" scope="page" type="co.uk.golunch.model.User"/>
            <tr>
                <td><c:out value="${user.name}"/></td>
                <td><a href="mailto:${user.email}">${user.email}</a></td>
                <td>${user.roles}</td>
<%--                <td><%=user.isEnabled()%></td>--%>
                <td><fmt:formatDate value="${user.registered}" pattern="dd-MM-yyyy"/></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
