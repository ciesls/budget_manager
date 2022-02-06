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
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet"/>
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
                <%--                <h1 class="mt-4">Add New Budget</h1>--%>
                <div id="layoutAuthentication">
                    <div id="layoutAuthentication_content">
                        <main>
                            <div class="container">
                                <div class="row justify-content-center">
                                    <div class="col-lg-7">
                                        <div class="card shadow-lg border-0 rounded-lg mt-5">
                                            <div class="card-header"><h3
                                                    class="text-center font-weight-light my-4">Add expense</h3>
                                            </div>
                                            <div class="card-body">
                                                <form:form method="post"
                                                           modelAttribute="expense">
                                                <div class="row mb-3">
                                                    <div class="col-md-6">
                                                        <div class="form-floating mb-3">
                                                            <form:input class="form-control" id="expenseName"
                                                                        type="text" placeholder="Enter name"
                                                                        path="name"/>
                                                            <label for="expenseName">Expense name</label>
                                                        </div>
                                                    </div>
                                                    <div class="row mb-3">
                                                        <div class="col-md-6">
                                                            <div class="form-floating mb-3">
                                                                <form:input class="form-control" id="expenseDescription"
                                                                            type="text" placeholder="Enter description"
                                                                            path="description"/>
                                                                <label for="expenseDescription">Description</label>
                                                            </div>
                                                        </div>
                                                        <div class="form-floating mb-3">
                                                            <form:input path="amount" class="form-control"
                                                                        id="budgetAmount"
                                                                        type="number" step="0.01"
                                                                        placeholder="Amount" ></form:input>
                                                            <form:errors path="amount"
                                                                         cssClass="error" />
                                                            <label for="budgetAmount">Expense Amount</label>
                                                        </div>
                                                        <div class="form-floating mb-3">
                                                            <form:select path="category.id" multiple="false"
                                                                         itemValue="name" class="form-control"
                                                                         id="category">
                                                                <c:forEach var="category" items="${categories}">
                                                                    <form:option value="${category.id}">
                                                                        ${category.name}
                                                                    </form:option>
                                                                </c:forEach>
                                                                <label for="category">Category</label>
                                                            </form:select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="mt-4 mb-0">
                                                    <div class="d-grid">
                                                        <button class="btn btn-primary btn-block"
                                                                type="submit">Save
                                                        </button>
                                                    </div>
                                                    </form:form>
                                                </div>
                                            </div>
                                            <div class="card-footer text-center py-3">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </main>
                    </div>
                </div>
            </div>
        </main>
    </div>


    <jsp:include page="footer.jsp"/>
</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
<script src="js/scripts.js"></script>
</body>
</html>