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
        <th>Amount</th>
        <th>Edit</th>
        <th>Delete</th>
        <th>Details</th>
        <th>Show categories in bugdet</th>
    </tr>
    </thead>
<c:forEach var="budget" items="${budgets}">

    <tr>
        <th>${budget.name}</th>
        <th>${budget.amount}</th>
        <th><a href="/budgets/edit/${budget.id}">Edit</a></th>
        <th><a href="/budgets/delete/${budget.id}">Delete</a></th>
        <th><a href="/budgets/details/${budget.id}">Details</a></th>
        <th><a href="/budgets/budgetCategories/${budget.id}">Show categories in budget</a></th>
    </tr>
    </c:forEach>
</table>
</body>
</html>
