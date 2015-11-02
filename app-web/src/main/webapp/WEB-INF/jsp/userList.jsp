<%--
  Created by IntelliJ IDEA.
  User: juga
  Date: 30.10.15
  Time: 20.08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <title></title>
</head>
<body>

<h1><spring:message code="user.list"/></h1>
<table border="1">
  <tr>
    <td>id</td>
    <td>login</td>
    <td>password</td>
  </tr>
  <c:forEach items="${dto.users}" var="user">
    <tr>
      <td>${user.userId}</td>
      <td>${user.login}</td>
      <td>${user.password}</td>
    </tr>
  </c:forEach>
</table>
<h3><spring:message code="user.total"/> ${dto.total}</h3>
<a href="/inputUser"><spring:message code="user.create"/></a>
</body>
</html>
