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
</head>
<title>Expense List</title>
</head>
<body>

    <table>
        <thead>
        <tr>
            <th>${category.name}</th>
        </tr>
        </thead>
        <tbody>
<c:forEach var="entry" items="${groupedByCategory}">
        <tr>
            <td>${entry.key}</td>
            <td>${entry.value}</td>
        </tr>
</c:forEach>
        </tbody>
    </table>


</body>
</html>
