<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<section>
    <a href="index.html">Home</a>
    <br>
    <br>
    <br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Restaurant Name</th>
            <th>Menu</th>
            <th>All votes</th>
            <th>Vote here</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${restaurants}" var="restaurant">
            <tr>
                <jsp:useBean id="restaurant" type="co.uk.golunch.model.Restaurant"/>
                <td>${restaurant.name}</td>
                <td>${restaurant.menu}</td>
                <td>Votes: ${restaurant.votes.size()}</td>
                    <%--                <td><a href="restaurants?action=vote&id=${restaurant.id}&userId=">Vote</a></td>--%>
                <td><a href="restaurants?action=vote&id=${restaurant.id}">Vote</a></td>
                <td><a href="restaurants?action=update&id=${restaurant.id}">Update</a></td>
                <td><a href="restaurants?action=delete&id=${restaurant.id}">Delete</a></td>
            </tr>
        </c:forEach>

        <a href="restaurants?action=create">Add restaurant</a>
        <br><br>
    </table>
</section>
</body>
</html>
