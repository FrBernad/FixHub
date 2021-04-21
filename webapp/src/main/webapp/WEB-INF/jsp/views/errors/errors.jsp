<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="productName"/> | <spring:message code="errors.Title"/></title>
    <%@ include file="../../components/includes/headers.jsp" %>
    <link href='<c:url value="/resources/css/errors.css"/>' rel="stylesheet">

</head>
<body>
<div class="container-fluid h-100" id="mainContainer">
    <%@ include file="../../components/navbar.jsp" %>
    <div class="row h-100">
        <div class="col-12 h-25 d-flex justify-content-center align-items-center">
            <h1><c:out value="${code}"/>: <c:out value="${errors}"/></h1>
        </div>
        <div class="col-12 h-50 d-flex justify-content-center align-items-center">
            <img src="<c:url value="/resources/images/errors.png"/>" alt="not found image"
                 style="object-fit: contain; width: 700px; height: 700px;">
        </div>
        <div class="col-12 h-25 d-flex justify-content-center align-items-center">
            <a href="<c:url value='/'/>" style="font-size: 30px;">
                <spring:message code="navBar.home"/>
            </a>
        </div>
    </div>
    <%@ include file="../../components/footer.jsp" %>
    <%@ include file="../../components/includes/bottomScripts.jsp" %>
</body>
</html>

