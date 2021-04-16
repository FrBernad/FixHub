<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="productName"/> | <spring:message code="account.password.resetResult.title"/></title>

    <%@ include file="../../../../components/includes/headers.jsp" %>
    <link href='<c:url value="/resources/css/resetRequest.css"/>' rel="stylesheet">
</head>

<body>

<div class="container-fluid px-0 outerContainer">
    <%@ include file="../../../../components/navbar.jsp" %>
    <div class="container-fluid py-4 px-0 d-flex align-items-center">
        <div class="container-lg p-5 mt-5 smallContentContainer">
            <div class="row w-100 h-100 m-0 align-items-center justify-content-center">
                <c:choose>
                    <c:when test="${success}">
                        <div class="col-12">
                            <h1 class="text-center title">
                                <spring:message code="account.password.resetResult.success"/>
                            </h1>
                            <p class="subtitle text-center mb-4">
                                <spring:message code="account.password.resetResult.successInstruction"/>
                            </p>
                        </div>
                        <div class="col-12 d-flex align-items-center justify-content-center">
                            <i class="far fa-check-circle fa-7x" id="success"></i>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="col-12">
                            <h1 class="text-center title">
                                <spring:message code="account.password.resetResult.error"/>
                            </h1>
                            <p class="subtitle text-center mb-4">
                                <spring:message code="account.password.resetResult.errorInstruction"/>
                            </p>
                        </div>
                        <div class="col-12 d-flex align-items-center justify-content-center">
                            <i class="fas fa-exclamation-circle fa-7x" id="error"></i>
                        </div>
                    </c:otherwise>
                </c:choose>

            </div>
        </div>
    </div>
</div>


<%@ include file="../../../../components/includes/bottomScripts.jsp" %>

</body>

</html>