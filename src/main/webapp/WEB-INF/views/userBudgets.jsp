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
<jsp:include page="topNavBar.jsp"/>
<div id="layoutSidenav">
    <jsp:include page="leftNavBar.jsp"/>
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <div class="card mb-4">
                </div>
                <div class="card mb-4">
                    <div class="card-body">
                        <table id="datatablesSimple">
                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Amount</th>
                                <th>Edit</th>
                                <th>Delete</th>
                                <th>Details</th>
                                <th>Show categories in budget</th>
                                <th>Show expenses in budget</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="budget" items="${budgets}">
                                <tr>
                                    <td>${budget.name}</td>
                                    <td>${budget.amount}</td>
                                    <td><a href="/budgets/edit/${budget.id}">Edit</a></td>
                                    <td><a href="/budgets/delete/${budget.id}">Delete</a></td>
                                    <td><a href="/budgets/details/${budget.id}">Details</a></td>
                                    <td><a href="/budgets/budgetCategories/${budget.id}">Show categories in budget</a></td>
                                    <td><a href="/budgets/budgetExpenses/${budget.id}">Show expenses in budget</a></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </main>
        <jsp:include page="footer.jsp"/>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
<script src="js/scripts.js"></script>
<script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
<script src="js/datatables-simple-demo.js"></script>
</body>
</html>