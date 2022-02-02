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
        <th>Planned Amount</th>
        <th>Spent this month amount</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    </thead>

    <tr>
        <th>${budgetDetails.name}</th>
        <th>${budgetDetails.amount}</th>
        <th>${budgetSum}</th>
        <th><a href="/budgets/edit/${budgetDetails.id}">Edit</a></th>
        <th><a href="/budgets/delete/${budgetDetails.id}">Delete</a></th>
    </tr>
</table>
</body>
</html>
