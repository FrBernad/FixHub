<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title><spring:message code="productName"/> | <c:out value="${user.name} ${user.surname}"/></title>
    <%@ include file="../../../components/includes/headers.jsp" %>
    <link href='<c:url value="/resources/css/profile.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/css/pagination.css"/>' rel="stylesheet">
</head>
<body>
<div class="outerContainer pb-4">
    <%@ include file="../../../components/navbar.jsp" %>
    <div class="container-lg userContainerProfile pb-4">
        <div class="row justify-content-center">

            <%--USER PICTURES--%>
            <div class="col-12 px-0">
                <div class="container-fluid px-0 position-relative backgroundImageContainer">
                    <c:choose>
                        <c:when test="${user.coverImageId == 0}">
                            <img src="<c:url value='/resources/images/defaultCoverImage.jpg'/>"
                                 class="backgroundImage">
                        </c:when>
                        <c:otherwise>
                            <img src="<c:url value='/user/images/profile/${user.coverImageId}'/>"
                                 class="backgroundImage">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="col-12 mt-4">
                <div class="container-fluid px-0">
                    <div class="row align-items-center  position-relative justify-content-center">
                        <div class="profilePictureContainer">
                            <div class="picContainer">
                                <c:choose>
                                    <c:when test="${user.profileImageId == 0}">
                                        <img src="<c:url value='/resources/images/userProfile.png'/>"
                                             class="profilePicture">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="<c:url value='/user/images/profile/${user.profileImageId}'/>"
                                             class="profilePicture">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="col-8 mt-5">
                            <h1 class="userSectionTitles text-center names">
                                <c:out value="${user.name} ${user.surname}"/>
                            </h1>
                        </div>
                        <div class="followBtnContainer">
                            <c:if test="${loggedUser!=null}">
                                <c:choose>
                                    <c:when test="${followed}">
                                        <form id="unfollowForm" action="<c:url value='/user/unfollow'/>" method="POST">
                                            <input type="hidden" name="userId" value="${user.id}">
                                        </form>
                                        <button class="followBtn" id="unfollowBtn">
                                            <spring:message code="profilePage.unfollow"/>
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <form id="followForm" action="<c:url value='/user/follow'/>" method="POST">
                                            <input type="hidden" name="userId" value="${user.id}">
                                        </form>
                                        <button class="followBtn" id="followBtn">
                                            <spring:message code="profilePage.follow"/>
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-12 px-0 my-2">
                <div class="container-lg px-5">
                    <hr>
                </div>
            </div>

            <%--USER INFO--%>
            <div class="col-12 col-md-6 pl-0 pl-md-5">
                <div class="container-lg h-100 detailsSection">
                    <div class="row py-2">
                        <div class="col-6 my-2">
                            <h3 class="sectionTitle text-left">
                                <spring:message code="profilePage.subtitle.contact"/>
                            </h3>
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
                                                    <spring:message var="followers"
                                                                    code="profilePage.info.followers"/>
                                                    <spring:message var="following"
                                                                    code="profilePage.info.following"/>
                                                    <a class="extraInfo"
                                                       href="<c:url value="/user/${user.id}/followers"/>">
                                                            <span class="detailField">
                                                                <c:out value="${user.followers}"/>
                                                            </span>
                                                        ${followers}
                                                    </a>
                                                    ·
                                                    <a class="extraInfo"
                                                       href="<c:url value="/user/${user.id}/following"/>">
                                                        <span class="detailField">
                                                            <c:out value="${user.following}"/>
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
            </div>

        </div>
    </div>
</div>

<%@ include file="../../../components/footer.jsp" %>
<%@ include file="../../../components/includes/globalScripts.jsp" %>
<script src='<c:url value="/resources/js/otherProfile.js"/>'></script>

</body>
</html>
