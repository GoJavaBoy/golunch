<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<a href="restaurants">Restaurants</a> | <a href="users">Users</a>
<br>
<hr>
<section>
    <form method="post" action="users">
        <a>Login as: </a> <select name="userId">
        <option value="100006">Admin</option>
        <option value="100005" selected>User</option>
        <option value="100007" selected>User1</option>
        <option value="100008" selected>User2</option>
        <option value="100009" selected>User3</option>
        <option value="100010" selected>User4</option>
        <option value="100011" selected>User5</option>
    </select>
        <button type="submit">Select</button>
    </form>
</section>
</body>
</html>