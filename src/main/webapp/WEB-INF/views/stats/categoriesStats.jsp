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
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }

        th, td {
            padding: 5px;
            text-align: left;
        }
    </style>
</head>
<title>Expense List</title>
</head>
<body>

    <table>
        <thead>
        <tr>
            <th></th>
            <th>${category.name}</th>
        </tr>
        </thead>
        <tbody>
<c:forEach var="entry" items="${groupedByCategory}">
        <tr>
            <td>${entry.key}</td>
            <td>${entry.value}</td>
        </tr>
        </tbody>
    </table>
</c:forEach>


</body>
</html>
