<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="serverError.title"/></title>
    <%@ include file="../components/headers.jsp" %>
</head>
<body>
<div class="container-fluid" style="position: relative;">
    <img src="<c:url value="/resources/images/pageNotFound.png"/>"  alt="" style="object-fit: cover;">
    <div class="top-left mt-1" style="position: absolute; top: 1em; left: 3em;">
        <h1 style="color: #003B6D;"><spring:message code="serverError.description"/></h1>
    </div>
</div>

<%@ include file="../components/bottomScripts.jsp" %>

</body>
</html>
