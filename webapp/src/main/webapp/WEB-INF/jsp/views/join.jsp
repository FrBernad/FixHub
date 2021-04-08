<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="productName"/> | <spring:message code="join.jobTitle"/></title>

    <%@ include file="../components/includes/headers.jsp" %>
    <link href='<c:url value="/resources/css/join.css"/>' rel="stylesheet">
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
                                        <c:url value="/join" var="postPath"/>
                                        <form:form modelAttribute="emailForm" id="mailForm" action="${postPath}"
                                                   method="POST">
                                            <div class="form-group">
                                                <form:label path="email"><spring:message
                                                        code="join.EmailText"/></form:label>
                                                <form:input type="text" class="form-control input" path="email"
                                                            id="email"
                                                            aria-describedby="email input"/>
                                                <form:errors path="email" cssClass="formError" element="p"/>
                                            </div>
                                        </form:form>
                                    </div>
                                    <div class="col-12 d-flex align-items-center justify-content-center">
                                        <button form="mailForm" type="submit" class="w-100 continueBtn my-2">
                                            <spring:message code="join.buttonText"/>
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12">
                                <p class="my-4 text-center" style="font-size: 14px"><spring:message
                                        code="join.noAccountText"/>
                                    <a href="<c:url value='/join/register'/>"><spring:message
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