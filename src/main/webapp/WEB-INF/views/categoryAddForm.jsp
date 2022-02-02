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
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--@elvariable id="category" type="pl.cieslas.budgetmanager.entity.Category"--%>
<form:form method="post"
           modelAttribute="category">
    <label>Name:</label>
    <form:input path="name" type="text"/><br>
    <label>Budget:</label>
    <form:select path="budget.id" multiple="false" itemValue="name">
        <c:forEach var="budget" items="${budgets}">
            <form:option value="${budget.id}">
                ${budget.name}
            </form:option>
        </c:forEach>
    </form:select>
    <input type="submit" value="Save">
</form:form>
</body>
</html>
