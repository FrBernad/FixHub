<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title><spring:message code="productName"/> | <c:out value="${loggedUser.name} ${loggedUser.surname}"/></title>
    <%@ include file="../../../components/includes/headers.jsp" %>
    <link href='<c:url value="/resources/css/profile.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/css/pagination.css"/>' rel="stylesheet">
</head>
<body>
<%@ include file="../../../components/accountNotVerified.jsp" %>
<div class="outerContainer pb-4">
    <%@ include file="../../../components/navbar.jsp" %>
    <div class="container-lg userContainerProfile pb-4">
        <div class="row">

            <%--USER PICTURES--%>
            <div class="col-12 px-0">
                <div class="container-fluid px-0 position-relative backgroundImageContainer">
                    <c:choose>
                        <c:when test="${loggedUser.coverImageId == 0}">
                            <img src="<c:url value='/resources/images/defaultCoverImage.jpg'/>"
                                 class="backgroundImage">
                        </c:when>
                        <c:otherwise>
                            <img src="<c:url value='/user/images/profile/${loggedUser.coverImageId}'/>"
                                 class="backgroundImage">
                        </c:otherwise>
                    </c:choose>
                    <div class="profileBackgroundPic">
                        <c:url value="/user/account/updateCoverImage" var="postCoverImage"/>
                        <form:form id="changeCoverForm" type="hidden" action="${postCoverImage}" method="POST"
                                   enctype="multipart/form-data">
                            <input type="file" id="coverInputFile" name="image" hidden accept=".png,.jpg,.jpeg"/>
                        </form:form>
                        <button type="button" id="changeCoverImageButton">
                            <i class="fas fa-camera mr-2"></i>
                            <span><spring:message code="profilePage.picture.changeBg"/></span>
                        </button>
                    </div>
                </div>
            </div>
            <div class="col-12 mt-4">
                <div class="container-fluid px-0">
                    <div class="row align-items-center  position-relative justify-content-center">
                        <div class="profilePictureContainer">
                            <div class="picContainer">
                                <c:choose>
                                    <c:when test="${loggedUser.profileImageId == 0}">
                                        <img src="<c:url value='/resources/images/userProfile.png'/>"
                                             class="profilePicture">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="<c:url value='/user/images/profile/${loggedUser.profileImageId}'/>"
                                             class="profilePicture">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="profilePicEditBtn">
                                <c:url value="/user/account/updateProfileImage" var="postProfileImage"/>
                                <form:form id="changeProfileForm" type="hidden" action="${postProfileImage}"
                                           method="POST" enctype="multipart/form-data">
                                    <input type="file" id="profileInputFile" name="image" hidden
                                           accept=".png,.jpg,.jpeg"/>

                                </form:form>

                                <button type="button" id="changeProfileImageButton">
                                    <i class="fas fa-camera"></i>
                                </button>
                            </div>
                        </div>
                        <div class="col-8 mt-5">
                            <h1 class="userSectionTitles text-center">
                                <c:out value="${loggedUser.name} ${loggedUser.surname}"/>
                            </h1>
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
                <div class="container-lg detailsSection">
                    <div class="row py-2">
                        <div class="col-6 my-2">
                            <h3 class="sectionTitle text-left">
                                <spring:message code="profilePage.subtitle.contact"/>
                            </h3>
                        </div>
                        <div class="col-6 d-flex align-items-center justify-content-end">
                            <a href="<c:url value="/user/account/updateInfo"/>">
                                <button class="editBtn">
                                    <i class="fas fa-pen"></i>
                                </button>
                            </a>
                        </div>
                        <div class="col-12">
                            <h3 class="info">
                                <span class="detailField"><spring:message code="profilePage.info.location"/></span>
                                <c:out value="${loggedUser.state}, ${loggedUser.city}."/>
                            </h3>
                        </div>
                        <div class="col-12 mt-2">
                            <h3 class="info">
                                <span class="detailField"><spring:message code="profilePage.info.email"/></span>
                                <c:out value="${loggedUser.email}."/>
                            </h3>
                        </div>
                        <div class="col-12 mt-2">
                            <h3 class="info">
                                <span class="detailField"><spring:message code="profilePage.info.phone"/></span>
                                <c:out value="${loggedUser.phoneNumber}."/>
                            </h3>
                        </div>
                    </div>
                </div>
            </div>

            <%--RECENT CONTACTS--%>
            <div class="col-12 col-md-6 pr-0 pr-md-5">
                <div class="container-lg d-flex flex-column recentContacts">
                    <div class="row py-2">
                        <div class="col-12 mt-2">
                            <h1 class="text-left mb-0 sectionTitle">
                                <spring:message code="profilePage.contacts.recents"/>
                            </h1>
                        </div>
                    </div>
                    <div class="row pb-2 align-items-start flex-grow-1 align-content-between">
                        <c:url value="/user/account/search" var="postPath"/>
                        <form:form cssClass="mb-0" action="${postPath}" modelAttribute="searchForm"
                                   method="GET"
                                   id="searchForm">
                            <form:input path="page" type="hidden" id="pageInput"/>
                        </form:form>
                        <div class="col-12">
                            <div class="container-fluid px-0">
                                <div class="row">
                                    <c:choose>
                                        <c:when test="${results.totalPages > 0}">
                                            <c:forEach var="contact" items="${results.results}" varStatus="status">
                                                <div class="col-12 mt-2">
                                                    <%@ include file="../../../components/cards/accordionContact.jsp" %>
                                                </div>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="col-12 d-flex align-items-center justify-content-center">
                                                <div class="container mt-2 d-flex align-items-center justify-content-center">
                                                    <p class="m-0 text-center p-4" style="font-size: 16px">
                                                        <spring:message code="profilePage.contacts.noContacts"/>
                                                    </p>
                                                </div>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                        <div class="col-12 mb-2 d-flex align-self-end align-items-center justify-content-center ${results.totalPages<=1 ? 'd-none':''}">
                            <div class="row">
                                <%@ include file="../../../components/pagination.jsp" %>
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
<script src='<c:url value="/resources/js/profile.js"/>'></script>

</body>
</html>
