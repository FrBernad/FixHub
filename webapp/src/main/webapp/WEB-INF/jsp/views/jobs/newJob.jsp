<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="productName"/> | <spring:message code="newJob.Title"/></title>

    <%@ include file="../../components/includes/headers.jsp" %>

    <link href='<c:url value="/resources/css/newJob.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/css/login.css"/>' rel="stylesheet">
</head>

<body>

<div class="container-fluid p-0 outerContainer">
    <%@ include file="../../components/navbar.jsp" %>
    <div class="container-fluid py-4 px-0">
        <div class="container-lg w-50 p-5 smallContentContainer">
            <div class="row w-100 m-0 align-items-center justify-content-center">
                <div class="col-12">
                    <h1 class="text-center title"><spring:message code="newJob.Title"/></h1>
                    <p class="subtitle text-center mb-4">
                        <span class="font-weight-bold">
                        <spring:message code="newJob.description.boldText"/>
                        </span>
                        <spring:message code="newJob.description.normalText"/></p>
                </div>
                <div class="col-12">
                    <div class="container-lg">
                        <div class="row">
                            <div class="col-12 d-flex align-items-center justify-content-center">
                                <%@include file="../../components/forms/newJobForm.jsp" %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../../components/footer.jsp" %>

<script src='<c:url value="/resources/js/newJob.js"/>'></script>
<%@ include file="../../components/includes/bottomScripts.jsp" %>

</body>

</html>