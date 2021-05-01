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
    <div class="row h-100 align-items-center ">
        <div class="col-12">
            <div class="container-fluid px-0">
                <div class="row">
                    <div class="col-12 d-flex justify-content-center align-items-center mb-4">
                        <img src="<c:url value="/resources/images/errors.png"/>" alt="not found image"
                             style="object-fit: cover; width: 400px; height: 200px;">
                    </div>

                    <div class="col-12 d-flex justify-content-center align-items-center">
                        <h1><c:out value="${code}"/></h1>
                    </div>

                    <div class="col-12 d-flex justify-content-center align-items-center">
                        <p><c:out value="${errors}"/></p>
                    </div>

                    <div class="col-12 d-flex justify-content-center align-items-center">
                        <a href="<c:url value='/'/>" style="font-size: 18px;">
                            <spring:message code="navBar.home"/>
                        </a>
                    </div>
                </div>
            </div>

        </div>

    </div>

    <%@ include file="../../components/includes/globalScripts.jsp" %>
</body>
</html>

