<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="contact.contactTitle"/></title>
    <%@ include file="../components/headers.jsp" %>
    <link href='<c:url value="/resources/css/contact.css"/>' rel="stylesheet">
</head>
<body>
<%@ include file="../components/navbarOld.jsp" %>

<div class="container-fluid d-flex align-items-center justify-content-center"
     style="background-color: rgb(245,245,242)">
    <div class="container-lg w-50 p-5 my-5" style="background-color: white; max-width: 75em">
        <div class="row w-100 m-0 align-items-center justify-content-center">
            <div class="col">
                <h1 class="text-center title"><spring:message code="contact.contactTitle"/> ${provider.name}</h1>
                <p class="subtitle text-center mb-4"><span class="font-weight-bold"><spring:message code="contact.description.boldText"/></span>
                    <spring:message code="contact.description.normalText"/></p>
                <div class="container-lg">
                    <div class="row">
                        <div class="col-12 d-flex align-items-center justify-content-center">
                            <%@ include file="../components/contactForm.jsp" %>
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
