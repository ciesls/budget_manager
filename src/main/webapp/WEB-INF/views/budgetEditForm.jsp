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
<%--@elvariable id="budget" type="pl.cieslas.budgetmanager.entity.Budget"--%>
<form:form method="post"
           modelAttribute="budget">

    <form:hidden path="id"/>

    <label>Name:</label>
    <form:input path="name" type="text"/><br>
    <label>Amount:</label>
    <form:input path="amount" type="number" step="0.01"/>
    <input type="submit" value="Save">
</form:form>
</body>
</html>
