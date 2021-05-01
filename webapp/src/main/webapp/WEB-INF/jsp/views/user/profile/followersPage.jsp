<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Title</title>
    <%@ include file="../../../components/includes/headers.jsp" %>
    <link href='<c:url value="/resources/css/followers.css"/>' rel="stylesheet">
</head>
<body>
<div class="outerContainer pb-4">
    <%@ include file="../../../components/navbar.jsp" %>
    <div class="container-lg userContainerProfile pb-4">.
        <div class="row">
            <div class="col-3 d-flex justify-content-center pt-1">
                <div class="container-lg">
                    <div class="row">
                        <div class="col">
                            <img src="<c:url value='/resources/images/userProfile.png'/>"
                                 class="avatarPicture" width="200" height="200" style="border-radius: 50%;">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col d-flex justify-content-start">
                            <span style="font-weight: 600;">El frano</span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col d-flex justify-content-start">
                            <span>frano@frano.com</span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <hr>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-2 p-0 d-flex justify-content-end align-items-center">
                            <i class="fas fa-users" aria-hidden="true"></i>
                        </div>
                        <div class="col-5 p-0 d-flex justify-content-center align-items-center">
                            <span>8 followers |</span>
                        </div>
                        <div class="col-5 p-0 d-flex justify-content-start align-items-center">
                            <span>7 following</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-9 pt-5">
                <c:forEach var="i" begin="0" end="4">
                    <%@ include file="../../../components/followers.jsp" %>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
<%@ include file="../../../components/footer.jsp" %>
<%@ include file="../../../components/includes/globalScripts.jsp" %>
</body>
</html>
