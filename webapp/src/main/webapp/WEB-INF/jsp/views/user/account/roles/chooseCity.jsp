<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="productName"/> | <spring:message code="join.title"/></title>

    <%@ include file="../../../../components/includes/headers.jsp" %>
    <link href='<c:url value="/resources/css/join.css"/>' rel="stylesheet">
</head>

<body>
<div class="container-fluid px-0 outerContainer">
    <%@ include file="../../../../components/navbar.jsp" %>
    <div class="container-fluid py-4 px-0">
        <div class="container-lg p-5 smallContentContainer" style="min-height: 75%">
            <div class="row w-100 m-0 align-items-center justify-content-center">
                <div class="col-12">
                    <h1 class="text-center title">
                        <spring:message code="join.header"/>
                    </h1>
                    <p class="subtitle text-center mb-4">
                        <span class="font-weight-bold"><spring:message
                                code="join.description.boldText"/>
                        </span>
                        <spring:message code="join.description.normalText"/></p>
                </div>
                <div class="col-12">
                    <div class="container-fluid px-0">
                        <div class="row">
                            <div class="col-12 d-flex align-items-center justify-content-center">
                                <%@ include file="../../../../components/forms/chooseCityForm.jsp" %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../../../../components/footer.jsp" %>
<%@ include file="../../../../components/includes/bottomScripts.jsp" %>
<script src='<c:url value="/resources/js/chooseCity.js"/>'></script>
</body>

</html>