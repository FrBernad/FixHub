<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="productName"/> | <spring:message code="editProfilePage.title"/></title>
    <%@ include file="../../../components/includes/headers.jsp" %>
    <link href='<c:url value="/resources/css/login.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/css/user.css"/>' rel="stylesheet">
</head>
<body>
<div class="outerContainer pb-4">
    <%@ include file="../../../components/navbar.jsp" %>
    <div class="container-lg userContainer py-4">
        <div class="container-lg px-0 m-2">
            <div class="container-fluid">
                <div class="row justify-content-center align-content-center">
                    <div class="col-4 align-content-center justify-content-center">
                        <img src="<c:url value='/resources/images/userProfile.png'/>" class="rounded card-img-bottom img-thumbnail my-3" alt="Imagen no disponible">
                    </div>
                </div>
            </div>
        </div>
        <div class="container-lg px-4 m-2 ">
            <%@ include file="../../../components/forms/userInfoForm.jsp" %>
        </div>
    </div>
</div>
<%@ include file="../../../components/footer.jsp" %>
<%@ include file="../../../components/includes/bottomScripts.jsp"%>

</body>
</html>
