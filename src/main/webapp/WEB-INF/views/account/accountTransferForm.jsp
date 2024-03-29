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
<body>
<jsp:include page="../staticElements/topNavBar.jsp"/>
<div id="layoutSidenav">
    <jsp:include page="../staticElements/leftNavBar.jsp"/>
    <div id="layoutSidenav">
        <form:form method="post" action="/account/transfer">

        <label>From account:</label>
        <select name="account1ID" multiple="false" id="account1ID">
            <c:forEach var="account" items="${accounts}">
                <option value="${account.id}">
                        ${account.name}
                </option>
            </c:forEach>
        </select>
            <label>To account:</label>
            <select name="account2ID" multiple="false" id="account2ID">
                <c:forEach var="account" items="${accounts}">
                    <option value="${account.id}">
                            ${account.name}
                    </option>
                </c:forEach>
            </select>
            <label>Amount to be transferred</label>
            <input type="number" step="0.01" name="amount">
            <br>
            <input type="submit" value="Transfer">
        </form:form>
        <jsp:include page="../staticElements/footer.jsp"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
                crossorigin="anonymous"></script>
        <script src="js/scripts.js"></script>
</body>
</html>