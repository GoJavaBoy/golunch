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
    <hr>
    <h2>${param.id == null ? 'Create restaurant' : 'Edit restaurant'}</h2>
    <jsp:useBean id="restaurant" type="co.uk.golunch.model.Restaurant" scope="request"/>
    <form method="post" action="${pageContext.request.contextPath}/restaurants">
        <input type="hidden" name="id" value="${restaurant.id}">
        <dl>
            <dt>Name:</dt>
            <dd><input type="text" value="${restaurant.name}" name="name" required></dd>
        </dl>
        <a>Menu:</a>
        <br>
        <section>
            <table border="1" cellpadding="8" cellspacing="0">
                <thead>
                <tr>
                    <th>Dish</th>
                    <th>Price</th>
                </tr>
                </thead>
                <c:set var="count" value="0" scope="page" />
                <c:forEach items="${restaurant.menu}" var="restaurant">
                    <tr>
                        <td><input type="text" value="${restaurant.key}" name="dish_${count}"></td>
                        <td><input type="number" value="${restaurant.value}" name="price_${count}"></td>
                    </tr>
                    <c:set var="count" value="${count + 1}" scope="page"/>
                </c:forEach>
                <c:forEach begin="${count}" end="${count+4}">
                    <tr>
                        <td><input type="text" value="" name="dish_${count}"></td>
                        <td><input type="number" value="" name="price_${count}"></td>
                    </tr>
                    <c:set var="count" value="${count + 1}" scope="page"/>
                </c:forEach>
            </table>
            <br><br>
        </section>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>
