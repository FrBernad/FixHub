<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="productName"/> | <spring:message code="login.title"/></title>

    <%@ include file="../components/includes/headers.jsp" %>
    <link href='<c:url value="/resources/css/login.css"/>' rel="stylesheet">
</head>

<body>

<div class="container-fluid px-0 outerContainer">
    <%@ include file="../components/navbar.jsp" %>
    <div class="container-fluid py-4 px-0">
        <div class="container-lg p-5 smallContentContainer" style="min-height: 75%">
            <div class="row w-100 m-0 align-items-center justify-content-center">
                <div class="col-12">
                    <h1 class="text-center title">
                        <spring:message code="login.header"/>
                    </h1>
                    <p class="subtitle text-center mb-4">
                        <span class="font-weight-bold"><spring:message
                                code="login.description.boldText"/>
                        </span>
                        <spring:message code="login.description.normalText"/></p>
                </div>
                <div class="col-12">
                    <div class="container-fluid px-0">
                        <c:url value="/login" var="postPath"/>
                        <form id="loginForm" action="${postPath}"
                              method="POST" enctype="application/x-www-form-urlencoded">
                            <div class="row">
                                <div class="col-12">
                                    <label>
                                        <spring:message code="login.EmailText"/>
                                    </label>
                                    <input type="text" class="form-control mb-2"
                                           id="email" name="email"
                                           aria-describedby="email input"/>
                                </div>
                                <div class="col-12 mt-2">
                                    <div class="container-fluid">
                                        <div class="row">
                                            <div class="col-6 px-0">
                                                <label>
                                                    <spring:message code="login.password"/>
                                                </label>
                                            </div>
                                            <div class="col-6 px-0 d-flex justify-content-end">
                                                <a href="<c:url value='/user/resetPasswordRequest'/>" tabindex="-1">
                                                    <span class="text-right"
                                                          style="font-size: 0.9rem; margin-bottom: .5rem">
                                                        <spring:message code="login.forgotPassword"/>
                                                    </span>
                                                </a>
                                            </div>
                                            <div class="col-12 px-0 d-flex justify-content-start align-items-center">
                                                <div class="input-group d-flex justify-content-start align-items-center">
                                                    <input type="password" class="form-control"
                                                           id="password" name="password"
                                                           aria-describedby="password input"/>
                                                    <div class="input-group-append">
                                                        <button id="passwordEye" type="button" tabindex="-1"
                                                                class="btn btn-lg form-control inputBtn input-group-text">
                                                            <i id="eye" class="far fa-eye-slash"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                            <c:if test="${error==true}">
                                                <div class="col-12 mt-2 px-0 d-flex justify-content-start align-items-center">
                                                    <p class="mb-0 formError">
                                                        <spring:message code="login.error"/>
                                                    </p>
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12 mt-3 d-flex justify-content-start align-items-center">
                                    <input type="checkbox" name="rememberMe"/>
                                    <label class="mb-0 ml-2">
                                        <spring:message code="login.rememberme"/>
                                    </label>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="col-12 d-flex align-items-center justify-content-center">
                    <button form="loginForm" type="submit" class="w-100 continueBtn my-2">
                        <spring:message code="login.buttonText"/>
                    </button>
                </div>
                <div class="col-12 mt-4">
                    <p class="mb-0 text-center">
                        <spring:message code="login.noAccount1"/>
                        <a href="<c:url value='/register'/>">
                            <spring:message code="login.noAccount2"/>
                        </a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../components/footer.jsp" %>
<script src='<c:url value="/resources/js/login.js"/>'></script>

<%@ include file="../components/includes/globalScripts.jsp" %>

</body>

</html>