<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  User: Anton.Nekrasov
  Date: 2/13/2015
  Time: 10:23
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% List<String> data = (ArrayList<String>)request.getAttribute("data"); %>
<html>
<head>
    <title>Liquibase</title>
</head>
<body>
<h1>Listed tables</h1>
<ul>
    <%
        for (String table : data) {
    %>

        <li><%=table%></li>

    <%
        }
    %>
</ul>

</body>
</html>
