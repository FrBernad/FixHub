<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="productName"/> | <spring:message code="account.password.reset.title"/></title>

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
                        <spring:message code="account.password.reset.instruction"/>
                    </h1>
                </div>
                <div class="col-12">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-12 mt-4">
                                <c:url var="postUrl" value='/user/resetPassword'/>
                                <form:form id="passReset" modelAttribute="resetPasswordForm"
                                           action="${postUrl}"
                                           method="POST">
                                    <form:input type="hidden" path="token" name="token" value="${token}"/>

                                    <form:label path="password" for="password">
                                        <spring:message code="account.password.reset.newPasswordLabel"/>
                                    </form:label>
                                    <form:input type="text" path="password" cssClass="form-control input" id="password" name="password"/>
                                    <form:errors path="password" cssClass="formError" element="p"/>

                                     <form:label path="confirmPassword" cssClass="mt-2" for="confirmPassword">
                                        <spring:message code="account.password.reset.newPasswordConfirmLabel"/>
                                    </form:label>
                                    <form:input type="text" path="confirmPassword" cssClass="form-control input" id="confirmPassword" name="confirmPassword"/>
                                    <form:errors path="" cssClass="formError" element="p"/>
                                </form:form>
                            </div>
                            <div class="col-12 mt-2 d-flex align-items-center justify-content-center">
                                <button form="passReset" type="submit" class="w-100 submitBtn my-2">
                                    <spring:message code="account.password.reset.applyBtn"/>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<%@ include file="../../../../components/includes/bottomScripts.jsp" %>

</body>

</html>