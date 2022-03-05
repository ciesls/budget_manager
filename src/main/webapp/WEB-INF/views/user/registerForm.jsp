<%--
  Created by IntelliJ IDEA.
  User: szymonciesla
  Date: 20/01/2022
  Time: 21:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form"
           uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form:form method="post" modelAttribute="user" action="/user/register">
    <form:input type="text" path="username" class="form-control" placeholder="Username"
                autofocus="true"/><br>

    <form:input type="password" path="password" class="form-control" placeholder="Password"/><br>

    <form:input type="email" path="email" class="form-control" placeholder="Email"/><br>

    <input type="submit" value="Create account">

</form:form>
</body>
</html>
