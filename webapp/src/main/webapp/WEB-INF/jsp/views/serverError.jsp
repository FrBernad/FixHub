<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="productName"/> | <spring:message code="serverError.title"/></title>
    <%@ include file="../components/includes/headers.jsp" %>
</head>
<body>
<div class="container-fluid h-100" id="mainContainer">
    <div class="row h-100">
        <div class="col-12 h-25 d-flex justify-content-center align-items-center">
            <h1><c:out value="${code}"/>: <c:out value="${errors}"/></h1>
        </div>
        <div class="col-12 h-50 d-flex justify-content-center align-items-center">
            <img src="<c:url value="/resources/images/pageNotFound.png"/>" alt="not found image"
                 style="object-fit: contain; width: 700px; height: 700px;">
        </div>
        <div class="col-12 h-25 d-flex justify-content-center align-items-center">
            <a href="<c:url value='/'/>" style="font-size: 30px;">
                <spring:message code="navBar.home"/>
            </a>
        </div>
    </div>
    <%@ include file="../components/includes/bottomScripts.jsp" %>
</body>
</html>

