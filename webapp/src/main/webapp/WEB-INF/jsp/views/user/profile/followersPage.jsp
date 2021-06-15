<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title><spring:message code="productName"/> | <spring:message code="profilePage.${flag ? 'following' : 'followers'}"/></title>
    <%@ include file="../../../components/includes/headers.jsp" %>
    <link href='<c:url value="/resources/css/followers.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/css/pagination.css"/>' rel="stylesheet">
</head>
<body>
<div class="outerContainer">
    <%@ include file="../../../components/navbar.jsp" %>
    <div class="container-lg userContainerProfile py-4">
        <div class="row">

            <div class="col-12 col-md-4 d-flex justify-content-center pt-1">
                <div class="container-lg">

                    <div class="col-12 d-flex justify-content-center align-items-center">
                        <div class="profilePictureContainer">
                            <div class="picContainer">
                                <c:choose>
                                    <c:when test="${user.profileImage == null}">
                                        <img alt="profile picture" src="<c:url value='/resources/images/userProfile.png'/>"
                                             class="profilePicture">
                                    </c:when>
                                    <c:otherwise>
                                        <img alt="profile picture" src="<c:url value='/user/images/profile/${user.profileImage.id}'/>"
                                             class="profilePicture">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>

                    <div class="col-12 mt-2 d-flex justify-content-center align-items-center">
                        <a href="<c:url value='/user/${user.id}'/>">
                            <span class="userSectionTitles names">
                                <c:out value="${user.name} ${user.surname}"/>
                            </span>
                        </a>
                    </div>

                    <div class="col-12 my-2">
                        <hr>
                    </div>

                    <div class="col-12">
                        <div class="container-fluid">
                            <div class="row">

                                <div class="col-12">
                                    <div class="container-fluid px-0">
                                        <div class="row">
                                            <div class="col-1 d-flex align-items-center justify-content-center">
                                                <i class="fas fa-map-marker-alt"></i>
                                            </div>
                                            <div class="col-10">
                                                    <span>
                                                      <c:out value="${user.state}, ${user.city}."/>
                                                    </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-12 mt-2">
                                    <div class="container-fluid px-0">
                                        <div class="row">
                                            <div class="col-1 d-flex align-items-center justify-content-center">
                                                <i class="far fa-envelope"></i>
                                            </div>
                                            <div class="col-10">
                                                <span><c:out value="${user.email}."/></span>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-12 mt-2">
                                    <div class="container-fluid px-0">
                                        <div class="row">
                                            <div class="col-1 d-flex align-items-center justify-content-center">
                                                <i class="fas fa-phone-alt"></i>
                                            </div>
                                            <div class="col-10">
                                                <span><c:out value="${user.phoneNumber}."/></span>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-12 mt-2 mb-2">
                                    <div class="container-fluid px-0">
                                        <div class="row">
                                            <div class="col-1 d-flex align-items-center justify-content-center">
                                                <i class="fas fa-user-friends"></i>
                                            </div>
                                            <div class="col-10">
                                                <spring:message var="followers" code="profilePage.info.followers"/>
                                                <spring:message var="following" code="profilePage.info.following"/>
                                                <a class="extraInfo"
                                                   href="<c:url value="/user/${user.id}/followers"/>">
                                                            <span class="detailField">
                                                                <c:out value="${user.followers.size()}"/>
                                                            </span>
                                                    ${followers}
                                                </a>
                                                ·
                                                <a class="extraInfo"
                                                   href="<c:url value="/user/${user.id}/following"/>">
                                                        <span class="detailField">
                                                            <c:out value="${user.following.size()}"/>
                                                        </span>
                                                    ${following}
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <c:url value="/user/${user.id}/${flag ? 'following' : 'followers'}/search" var="postPath"/>
            <form:form cssClass="mb-0" action="${postPath}" modelAttribute="searchForm" method="GET"
                       id="searchForm">
                <form:input path="page" type="hidden" id="pageInput"/>
            </form:form>

            <c:choose>
                <c:when test="${fn:length(results.results) gt 0}">
                    <div class="col-12 col-md-8 pt-5">
                        <c:forEach var="follower" items="${results.results}">
                            <div class="col-12 mt-3">
                                <%@ include file="../../../components/followers.jsp" %>
                            </div>
                            <div class="col-10">
                                <hr>
                            </div>
                        </c:forEach>
                        <%@ include file="../../../components/pagination.jsp" %>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="col-12 col-md-8 d-flex align-items-center justify-content-center">
                        <div class="container h-100 mt-2 d-flex align-items-center justify-content-center noJobsFound">
                            <p class="m-0 text-center p-4" style="font-size: 16px">
                                <spring:message code="profilePage.noFollowers"/>
                            </p>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
</div>
<%@ include file="../../../components/footer.jsp" %>
<script src='<c:url value="/resources/js/followers.js"/>'></script>
<%@ include file="../../../components/includes/globalScripts.jsp" %>
</body>
</html>
