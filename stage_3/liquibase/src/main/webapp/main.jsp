<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>Liquibase</title>
    </head>
    <body>
        <h1>Listed tables</h1>

        <ul>
            <c:forEach items="${requestScope.dataz}" var="item">
                <li><c:out value="${item}"/></li>
            </c:forEach>
        </ul>
    </body>
</html>
