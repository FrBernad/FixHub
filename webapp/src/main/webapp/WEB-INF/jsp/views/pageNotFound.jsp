<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <%@ include file="../components/includes/headers.jsp" %>
    <title><spring:message code="pageNotFound.title"/></title>
</head>
<body>
<div class="container-fluid" style="position: relative;">
    <div class="row h-25">
        <div class="col d-flex justify-content-center align-items-center">
            <h1 style="color: #003B6D;"><spring:message code="pageNotFound.description"/></h1>
        </div>
    </div>
    <div class="row h-50">
        <div class="col d-flex justify-content-center align-items-center">
            <img src="<c:url value="/resources/images/pageNotFound.png"/>" alt=""
                 style="object-fit: cover; width: 1000px; height: 500px;">
        </div>
    </div>
    <div class="row h-25">
        <div class="col d-flex justify-content-center align-items-center">
            <a href="<c:url value='/'/>" style="font-size: 30px;">
                <spring:message code="navBar.home"/>
            </a>
        </div>
    </div>
</div>
<%@ include file="../components/includes/bottomScripts.jsp" %>
</body>
</html>
