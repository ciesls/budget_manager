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
    <title>Expense List</title>
</head>
<body>

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
<br>
<a href="<c:url value="/logout" />">Logout</a>
<br>
Current month expenses
<table id="datatablesSimple">
    <thead>
    <tr>
        <th>Name</th>
        <th>Amount</th>
        <th>Created on</th>
        <th>Description</th>
        <th>Category</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    </thead>
    <c:forEach var="expense" items="${currentMonthExpenses}">

        <tr>
            <th>${expense.name}</th>
            <th>${expense.amount}</th>
            <th>${localDateTimeFormat.format(expense.createdOn)}</th>
            <th>${expense.description}</th>
            <th>${expense.category.name}</th>
            <th><a href="/expenses/edit/${expense.id}">Edit</a></th>
            <th><a href="/expenses/delete/${expense.id}">Delete</a></th>
        </tr>
    </c:forEach>
</table>
<br>
<br>
<br>

Last 5 expenses
<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Amount</th>
        <th>Created on</th>
        <th>Description</th>
        <th>Category</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    </thead>
    <c:forEach var="expense" items="${last5Expenses}">

        <tr>
            <th>${expense.name}</th>
            <th>${expense.amount}</th>
            <th>${localDateTimeFormat.format(expense.createdOn)}</th>
            <th>${expense.description}</th>
            <th>${expense.category.name}</th>
            <th><a href="/expenses/edit/${expense.id}">Edit</a></th>
            <th><a href="/expenses/delete/${expense.id}">Delete</a></th>
        </tr>
    </c:forEach>
</table>


<br>
<br>
<br>
Category stats
<form:form method="post" action="/stats/categoryStats">
    <label>Start Date:</label>
    <input type="date" name="startDate"/><br>
    <label>EndDate:</label>
    <input type="date" name="endDate"/><br>
    <label>Category:</label>
    <select name="category" multiple="false">
        <c:forEach var="category" items="${categories}">
            <option value="${category.id}">
                    ${category.name}
            </option>
        </c:forEach>
    </select>
    <input type="submit" value="Save">
</form:form>

<br>
<br>
<br>

Budget stats
<form:form method="post" action="/stats/budgetStats">
    <label>Start Date:</label>
    <input type="date" name="startDate"/><br>
    <label>EndDate:</label>
    <input type="date" name="endDate"/><br>
    <label>Budget:</label>
    <select name="budget" multiple="false">
        <c:forEach var="budget" items="${budgets}">
            <option value="${budget.id}">
                    ${budget.name}
            </option>
        </c:forEach>
    </select>
    <input type="submit" value="Save">
</form:form>

</body>
</html>
