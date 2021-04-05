<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <%@ include file="../components/headers.jsp" %>
    <title><spring:message code="register.title"/></title>
    <link href='<c:url value="/resources/css/join.css"/>' rel="stylesheet">
</head>

<body>
<%@ include file="../components/navbar.jsp" %>

<div class="container-fluid d-flex align-items-center justify-content-center"
     style="background-color: rgb(245,245,242)">
    <div class="container-lg w-50 p-5 my-5" style="background-color: white; max-width: 32em">
        <div class="row w-100 m-0 align-items-center justify-content-center">
            <div class="col-12">
                <h1 class="text-center title"><spring:message code="register.title"/></h1>
                <p class="subtitle text-center mb-4"><span class="font-weight-bold"><spring:message code="register.description.boldText"/></span>
                <spring:message code="register.description.normalText"/></p>
            </div>
            <div class="col-12">
                <div class="container-lg">
                    <div class="row">
                        <div class="col-12 d-flex align-items-center justify-content-center">
                            <%@ include file="../components/registerForm.jsp" %>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../components/footer.jsp" %>
<%@ include file="../components/bottomScripts.jsp" %>

</body>

</html>