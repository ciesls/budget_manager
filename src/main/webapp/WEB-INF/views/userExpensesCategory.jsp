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
        <th>Created on</th>
        <th>Description</th>
        <th>Category</th>
    </tr>
    </thead>
<c:forEach var="expense" items="${expensesCategories}">

    <tr>
        <th>${expense.name}</th>
        <th>${expense.amount}</th>
        <th>${localDateTimeFormat.format(expense.createdOn)}</th>
        <th>${expense.description}</th>
        <th>${expense.category.name}</th>
    </tr>
    </c:forEach>
</table>
<br>
<br>
<br>

Total expenses
${categorySum}
<br>
Spent in current month
${monthSum}

</body>
</html>
