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
<div class="outerContainer pb-4">
    <%@ include file="../../../components/navbar.jsp" %>
    <div class="container-lg userContainerProfile py-4">
        <div class="container-lg px-0 m-2">
            <div class="container-fluid">
                <c:choose>
                    <c:when test="${loggedUser.imageId == 0}">
                        <div class="col-4 align-content-center justify-content-center">
                            <img src="<c:url value='/resources/images/userProfile.png'/>"
                                 class="rounded card-img-bottom img-thumbnail my-3" alt="Imagen de perfil">
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="col-4 align-content-center justify-content-center">
                            <img src="<c:url value='/user/images/profile/${loggedUser.imageId}'/>"
                                 class="rounded card-img-bottom img-thumbnail my-3" alt="Imagen de perfil">
                        </div>
                    </c:otherwise>
                </c:choose>
                <div class="row justify-content-center align-content-center">
                    <div class="col-md-auto justify-content-center align-content-center">
                        <div class="userNameProfile">
                            <p><c:out value="${loggedUser.name} ${loggedUser.surname}"/></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container-lg px-0 m-2">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-auto m-2">
                        <p class="userSectionTitles"><spring:message code="profilePage.subtitle.contact"/></p>
                    </div>
                    <div class="col-md-auto justify-content-center align-content-center">
                        <a href="<c:url value='/user/account/update'/>">
                            <button class="continueBtn" type="submit">
                                <spring:message code="profilePage.edit"/>
                            </button>
                        </a>
                    </div>
                </div>
            </div>
            <div class="container-fluid">

                <div class="row" style="padding-left: 7px; margin-left: 7px; padding-bottom: 10px; margin-right: 7px;">
                    <div class="col-5 m-2">
                        <div class="row my-2">
                            <div class="col-4 infoField">
                                <p style="margin: 0"><spring:message code="profilePage.info.email"/></p>
                            </div>
                            <div class="col-8">
                                <c:out value="${loggedUser.email}"/>
                            </div>
                        </div>
                        <div class="row my-2">
                            <div class="col-4 infoField">
                                <p style="margin: 0;"><spring:message code="profilePage.info.phone"/></p>
                            </div>
                            <div class="col-8">
                                <c:out value="${loggedUser.phoneNumber}"/>
                            </div>
                        </div>
                        <div class="row my-2">
                            <div class="col-4 infoField">
                                <p style="margin: 0"><spring:message code="profilePage.info.state"/></p>
                            </div>
                            <div class="col-8">
                                <c:out value=" ${loggedUser.state}"/>
                            </div>
                        </div>
                        <div class="row my-2">
                            <div class="col-4 infoField">
                                <p style="margin: 0"><spring:message code="profilePage.info.city"/></p>
                            </div>
                            <div class="col-8">
                                <c:out value="${loggedUser.city}"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="row">
                <div class="col-12">
                    <h1 class="text-left sectionTitle">
                        <spring:message code="profilePage.contacts.recents"/>
                    </h1>
                </div>
                <c:url value="/user/account/search" var="postPath"/>
                <form:form cssClass="mb-0" action="${postPath}" modelAttribute="searchForm"
                           method="GET"
                           id="searchForm">
                    <form:input path="page" type="hidden" id="pageInput"/>
                </form:form>
                <c:choose>
                    <c:when test="${results.totalPages > 0}">
                        <c:forEach var="contact" items="${results.results}" varStatus="status">
                            <div class="col-10 col-md-7 mt-2">
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
                <div class="col-7 d-flex align-items-center justify-content-center ${results.totalPages<=1 ? 'd-none':''}">
                    <div class="row">
                        <%@ include file="../../../components/pagination.jsp" %>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../../../components/footer.jsp" %>
<%@ include file="../../../components/includes/bottomScripts.jsp" %>
<script src='<c:url value="/resources/js/profile.js"/>'></script>

</body>
</html>
