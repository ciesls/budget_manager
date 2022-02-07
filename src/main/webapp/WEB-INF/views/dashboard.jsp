<%--
  Created by IntelliJ IDEA.
  User: szymonciesla
  Date: 04/02/2022
  Time: 21:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form"
           uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/scripts.js" />"></script>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>BudgetManager</title>
    <link href="css/styles.css" rel="stylesheet"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"
            crossorigin="anonymous"></script>
</head>
<body class="sb-nav-fixed">
<jsp:include page="staticElements/topNavBar.jsp"/>
<div id="layoutSidenav">
    <jsp:include page="staticElements/leftNavBar.jsp"/>
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <h2 class="mt-4">Dashboard</h2>
                <div class="container-fluid px-4">
                    <div class="card mb-4">
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-table me-1"></i>
                                Last 5 expenses
                            </div>
                            <div class="card-body">
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
                                        <th>Details</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="expense" items="${last5Expenses}">
                                        <tr>
                                            <td>${expense.name}</td>
                                            <td>${expense.amount}</td>
                                            <td>${localDateTimeFormat.format(expense.createdOn)}</td>
                                            <td>${expense.description}</td>
                                            <td>${expense.category.name}</td>
                                            <td><a href="/expenses/edit/${expense.id}">Edit</a></td>
                                            <td><a href="/expenses/delete/${expense.id}">Delete</a></td>
                                            <td><a href="/expenses/details/${expense.id}">Details</a></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
<br>
                    <table>
                        <thead>
                        <tr>
                            <th>Budget</th>
                            <th>Spent this month</th>
                            <th>Planned spending</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                    <c:forEach var="entry" items="${budgetAmount}">
                            <td>${entry.key.name}</td>
                            <td>${entry.value}</td>
                            <td>${entry.key.amount}</td>
                        </tr>
                    </c:forEach>
                        </tbody>
                    </table>

                    <br>
                    Top 5 categories this month
                    <table>
                        <thead>
                        <tr>
                            <th>Category</th>
                            <th>Spent this month</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <c:forEach var="entry" items="${categoriesSum}" begin="0" end="4">
                            <td>${entry.key.name}</td>
                            <td>${entry.value}</td>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>

        </main>
        <jsp:include page="staticElements/footer.jsp"/>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            crossorigin="anonymous"></script>
    <script src="js/scripts.js"></script>

</div>
</body>
</html>
