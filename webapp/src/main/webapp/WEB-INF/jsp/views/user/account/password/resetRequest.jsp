<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="productName"/> | <spring:message code="account.password.resetRequest.title"/></title>

    <%@ include file="../../../../components/includes/headers.jsp" %>
    <link href='<c:url value="/resources/css/resetRequest.css"/>' rel="stylesheet">
</head>

<body>

<div class="container-fluid px-0 outerContainer">
    <%@ include file="../../../../components/navbar.jsp" %>
    <div class="container-fluid py-4 px-0 d-flex align-items-center">
        <div class="container-lg p-5 mt-5 smallContentContainer">
            <div class="row w-100 h-100 m-0 align-items-center justify-content-center">
                <div class="col-12">
                    <h1 class="text-center title">
                        <spring:message code="account.password.resetRequest.instruction"/>
                    </h1>
                </div>
                <div class="col-12">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-12 mt-4">
                                <c:url var="postUrl" value='/user/resetPasswordRequest'/>
                                <form:form id="passResetRequest" modelAttribute="resetPasswordEmailForm"
                                           action="${postUrl}"
                                           method="POST">
                                    <form:label path="email" for="email">
                                        <spring:message code="account.password.resetRequest.emailLabel"/>
                                    </form:label>
                                    <form:input path="email" type="text" class="form-control input" id="email"
                                                name="email"/>
                                    <form:errors path="email" cssClass="formError" element="p"/>
                                </form:form>
                            </div>
                            <div class="col-12 mt-2 d-flex align-items-center justify-content-center">
                                <button form="passResetRequest" type="submit" class="w-100 submitBtn my-2">
                                    <spring:message code="account.password.resetRequest.submitBtn"/>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<%@ include file="../../../../components/includes/globalScripts.jsp" %>

</body>

</html>