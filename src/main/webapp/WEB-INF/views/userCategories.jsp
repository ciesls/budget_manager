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
<c:forEach var="category" items="${categories}">

    <tr>
        <th>${category.name}</th>
        <th><a href="/categories/edit/${category.id}">Edit</a></th>
        <th><a href="/categories/delete/${category.id}">Delete</a></th>
        <th><a href="/categories/catExpenses/${category.id}">Expenses</a></th>
    </tr>
    </c:forEach>
</table>

<br>
<th><a href="/expenses/all">Expenses</a></th>
<br>
<th><a href="/categories/all">Categories</a></th>
<br>
<th><a href="/budgets/all">Budgets</a></th>
<br>
<br>
<br>

<th><a href="/expenses/add">Add Expense</a></th>
<br>
<th><a href="/categories/add">Add Category</a></th>
<br>
<th><a href="/budgets/add">Add Budget</a></th>
<br>
</body>
</html>