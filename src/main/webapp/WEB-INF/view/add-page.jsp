<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="spring"%>
<html>
<head>
    <title>adding</title>
</head>
<body>
<spring:form method="post" action="${pageContext.request.contextPath}/results" modelAttribute="newCertificate">
    <spring:label path="name">name: </spring:label>
    <spring:input path="name"/><br>

    <spring:label path="description">description: </spring:label>
    <spring:input path="description"/><br>

    <spring:label path="price">price: </spring:label>
    <spring:input path="price"/><br>

    <input type="submit" value="submit">
</spring:form>
</body>
</html>
