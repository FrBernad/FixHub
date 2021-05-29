<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="productName"/> | <spring:message code="join.city"/></title>

    <%@ include file="../../components/includes/headers.jsp" %>
    <link href='<c:url value="/resources/css/join.css"/>' rel="stylesheet">
</head>

<body>
<div class="container-fluid px-0 outerContainer">
    <%@ include file="../../components/navbar.jsp" %>
    <div class="container-fluid py-4 px-0">
        <div class="container-lg p-5 smallContentContainer">
            <div class="row w-100 m-0 align-items-center justify-content-center">
                <div class="col-12">
                    <h1 class="text-center title">
                        <spring:message code="editProvider.header"/>
                    </h1>
                </div>
                <div class="col-12">
                    <div class="container-fluid px-0">
                        <div class="row">
                            <div class="col-12 d-flex align-items-center justify-content-center">
                                <%@ include file="../../components/forms/editProviderInfoSecondForm.jsp" %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../../components/footer.jsp" %>
<%@ include file="../../components/includes/globalScripts.jsp" %>
<script src='<c:url value="/resources/js/editProviderInfoSecondForm.js"/>'></script>
</body>

</html>