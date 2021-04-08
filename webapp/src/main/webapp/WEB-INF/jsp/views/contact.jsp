<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="productName"/> | <spring:message code="contact.title"/></title>
    <%@ include file="../components/includes/headers.jsp" %>
    <link href='<c:url value="/resources/css/contact.css"/>' rel="stylesheet">
</head>
<body>
<div class="container-fluid px-0 outerContainer">
    <%@ include file="../components/navbar.jsp" %>
    <div class="container-fluid py-4 px-0">
        <div class="container-lg p-5 w-50 bigContentContainer">
            <div class="row w-100 m-0 align-items-center justify-content-center">
                <div class="col">
                    <h1 class="text-center title"><spring:message code="contact.contactTitle"/>${provider.name}</h1>
                    <p class="subtitle text-center mb-4"><span class="font-weight-bold"><spring:message
                            code="contact.description.boldText"/></span>
                        <spring:message code="contact.description.normalText"/></p>
                    <div class="container-lg">
                        <div class="row">
                            <div class="col-12 d-flex align-items-center justify-content-center">
                                <%@ include file="../components/forms/contactForm.jsp" %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<%@ include file="../components/footer.jsp" %>

<script src='<c:url value="/resources/js/contact.js"/>'></script>
<%@ include file="../components/includes/bottomScripts.jsp" %>

</body>
</html>
