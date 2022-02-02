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
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--@elvariable id="expense" type="pl.cieslas.budgetmanager.entity.Expense"--%>
<form:form method="post"
           modelAttribute="expense">
    <form:hidden path="id"/>
    <label>Name:</label>
    <form:input path="name" type="text"/><br>
    <label>Description:</label>
    <form:input path="description" type="text"/>
    <label>Amount:</label>
    <form:input path="amount" type="number" step="0.01"/>
    <label>Category:</label>
    <form:select path="category.name" multiple="false" itemValue="name">
        <c:forEach var="category" items="${categories}">
            <form:option value="${category.name}"/>
        </c:forEach>
    </form:select>
    <input type="submit" value="Save">
</form:form>
</body>
</html>
