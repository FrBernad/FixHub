<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="productName"/> |  <spring:message code="register.title"/></title>

    <%@ include file="../components/includes/headers.jsp" %>
    <link href='<c:url value="/resources/css/join.css"/>' rel="stylesheet">
</head>

<body>

<div class="container-fluid outerContainer">
    <%@ include file="../components/navbar.jsp" %>
    <div class="container-fluid py-4 px-0">
        <div class="container-lg w-50 p-5 smallContentContainer">
            <div class="row w-100 m-0 align-items-center justify-content-center">
                <div class="col-12">
                    <h1 class="text-center title"><spring:message code="register.title"/></h1>
                    <p class="subtitle text-center mb-4"><span class="font-weight-bold"><spring:message
                            code="register.description.boldText"/></span>
                        <spring:message code="register.description.normalText"/></p>
                </div>
                <div class="col-12">
                    <div class="container-lg">
                        <div class="row">
                            <div class="col-12 d-flex align-items-center justify-content-center">
                                <%@ include file="../components/forms/registerForm.jsp" %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../components/footer.jsp" %>

<%@ include file="../components/includes/bottomScripts.jsp" %>

<script src='<c:url value="/resources/js/register.js"/>'></script>

</body>

</html>