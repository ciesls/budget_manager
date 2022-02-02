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
<table id="datatablesSimple">
    <thead>
    <tr>
        <th>Name</th>
        <th>Edit</th>
        <th>Delete</th>
        <th>Show expenses in category</th>
    </tr>
    </thead>
<%--<c:forEach var="cat" items="${categoriesBudget}">--%>

    <c:forEach var="category" items="${categoriesBudget}">
    <tr>
        <th>${category.name}</th>
        <th><a href="/budgets/edit/${cat.id}">Edit</a></th>
        <th><a href="/budgets/delete/${cat.id}">Delete</a></th>
        <th><a href="/categories/catExpenses/${category.id}">Expenses</a></th>
    </tr>
    </c:forEach>
</table>
</body>
</html>
