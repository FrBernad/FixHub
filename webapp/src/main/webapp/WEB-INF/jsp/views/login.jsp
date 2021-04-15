<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="productName"/> | <spring:message code="join.jobTitle"/></title>

    <%@ include file="../components/includes/headers.jsp" %>
    <link href='<c:url value="/resources/css/login.css"/>' rel="stylesheet">
</head>

<body>

<div class="container-fluid px-0 outerContainer">
    <%@ include file="../components/navbar.jsp" %>
    <div class="container-fluid py-4 px-0">
        <div class="container-lg h-75 p-5 smallContentContainer">
            <div class="row w-100 m-0 align-items-center justify-content-center">
                <div class="col-12">
                    <h1 class="text-center title"><spring:message code="join.jobTitle"/></h1>
                    <p class="subtitle text-center mb-4"><span class="font-weight-bold"><spring:message
                            code="join.description.boldText"/></span>
                        <spring:message code="join.description.normalText"/></p>
                </div>
                <div class="col-12">
                    <div class="container-lg">
                        <div class="row">
                            <div class="col-12">
                                <div class="row">
                                    <div class="col-12">
                                        <c:url value="/login" var="postPath"/>
                                        <form:form modelAttribute="loginForm" id="loginForm" action="${postPath}"
                                                   method="POST" enctype="application/x-www-form-urlencoded">
                                            <div class="form-group">
                                                <form:label path="email">
                                                    <spring:message code="join.EmailText"/>
                                                </form:label>
                                                <form:input type="text" class="form-control input mb-2" path="email"
                                                            id="email" name="email"
                                                            aria-describedby="email input"/>
                                                <form:errors path="email" cssClass="formError" element="p"/>

                                                <form:label path="password">
                                                    password
                                                </form:label>
                                                <form:input type="text" class="form-control input" path="password"
                                                            id="password" name="password"
                                                            aria-describedby="password input"/>
                                                <form:errors path="password" cssClass="formError" element="p"/>
                                                <div class="container-fluid px-0 mt-2">
                                                    <div class="row">
                                                        <div class="col-12 d-flex justify-content-start align-items-center">
                                                            <form:checkbox name="rememberMe" path="rembemberMe"/>
                                                            <form:label cssClass="mb-0 ml-2" path="rembemberMe">
                                                                remember me
                                                            </form:label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </form:form>
                                    </div>
                                    <div class="col-12 d-flex align-items-center justify-content-center">
                                        <button form="loginForm" type="submit" class="w-100 continueBtn my-2">
                                            <spring:message code="join.buttonText"/>
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12">
                                <p class="my-4 text-center" style="font-size: 14px"><spring:message
                                        code="join.noAccountText"/>
                                    <a href="<c:url value='/register'/>"><spring:message
                                            code="join.hyperlinkText"/> </a>
                                </p>
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

</body>

</html>