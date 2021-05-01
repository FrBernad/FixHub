<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="container-lg d-flex flex-column recentContacts py-1">
    <div class="row">
        <div class="col-2 p-0 d-flex justify-content-center align-items-center">
            <img src="<c:url value='/resources/images/userProfile.png'/>"
                 class="avatarPicture" width="75" height="75" style="border-radius: 50%;">
        </div>
        <div class="col-3 p-0 d-flex justify-content-center align-items-center">
            <i class="fas fa-user mr-1" aria-hidden="true"></i>
            <span>Roman Riquelme</span>
        </div>
        <div class="col-4 p-0 d-flex justify-content-center align-items-center">
            <i class="far fa-envelope mr-1" aria-hidden="true"></i>
            <span>romanriquelme@gmail.com</span>
        </div>
        <div class="col-3 p-0 d-flex justify-content-center align-items-center">
            <%--TODO: Si lo seguis poner unfollow y sino poner follow--%>
            <button class="contactBtn"><spring:message code="profilePage.unfollow"/></button>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <hr>
        </div>
    </div>
</div>
</body>
</html>
