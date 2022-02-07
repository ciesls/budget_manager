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
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>BudgetManager</title>
    <link href="css/styles.css" rel="stylesheet" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js" crossorigin="anonymous"></script>
</head>
<body class="sb-nav-fixed">
<jsp:include page="topNavBar.jsp"/>
<div id="layoutSidenav">
    <jsp:include page="leftNavBar.jsp"/>
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <h1 class="mt-4">**TO BE CHANGES BASED ON VIEW NEEDS</h1>
<%--                <ol class="breadcrumb mb-4">--%>
<%--                    <li class="breadcrumb-item"><a href="index.html">Dashboard</a></li>--%>
<%--                    <li class="breadcrumb-item active">Static Navigation</li>--%>
<%--                </ol>--%>
                <div class="card mb-4">
                    <div class="card-body">
                        <p class="mb-0">
                            This page is an example of using static navigation. By removing the
                            <code>.sb-nav-fixed</code>
                            class from the
                            <code>body</code>
                            , the top navigation and side navigation will become static on scroll. Scroll down this page to see an example.
                        </p>
                    </div>
                </div>
            </div>
        </main>
        <jsp:include page="footer.jsp"/>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="js/scripts.js"></script>
</body>
</html>
