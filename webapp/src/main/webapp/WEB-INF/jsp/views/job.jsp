<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><c:out value="${job.description}"/></title>
    <%@ include file="../components/headers.jsp" %>
</head>
<body>
<h1><c:out value="${job.description}"/></h1>
</body>
</html>
