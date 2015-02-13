<%--
  User: Anton.Nekrasov
  Date: 2/13/2015
  Time: 10:39
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String error = request.getAttribute("error").toString(); %>
<html>
<head>
    <title>Oops</title>
</head>
<body>

<h1> Error !</h1>
<p style="color: red;">
    <%= error %>
</p>
</body>
</html>
