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
                <div class="container-fluid px-0 position-relative">
                    <img src="https://media-exp1.licdn.com/dms/image/C4E16AQFt_jaEDLpfzw/profile-displaybackgroundimage-shrink_200_800/0/1615241156937?e=1624492800&v=beta&t=8dfcwrjdLN8UgoSoV6k3iNEXT7xoekjs6iZNB6rZl1o"
                         class="backgroundImage"
                    >
                </div>
            </div>
            <div class="col-12 mt-4">
                <div class="container-fluid px-0">
                    <div class="row align-items-center  position-relative justify-content-center">
                        <div class="profilePictureContainer">
                            <div class="picContainer">
                                <c:choose>
                                    <c:when test="${user.imageId == 0}">
                                        <img src="<c:url value='/resources/images/userProfile.png'/>"
                                             class="profilePicture">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="<c:url value='/user/images/profile/${user.imageId}'/>"
                                             class="profilePicture">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="col-8 mt-5">
                            <h1 class="userSectionTitles text-center">
                                <c:out value="${user.name} ${user.surname}"/>
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
                <div class="container-lg h-100 detailsSection">
                    <div class="row py-2">
                        <div class="col-6 my-2">
                            <h3 class="sectionTitle text-left">
                                <spring:message code="profilePage.subtitle.contact"/>
                            </h3>
                        </div>
                        <div class="col-12">
                            <h3 class="info">
                                <span class="detailField"><spring:message code="profilePage.info.location"/></span>
                                <c:out value="${user.state}, ${user.city}."/>
                            </h3>
                        </div>
                        <div class="col-12 mt-2">
                            <h3 class="info">
                                <span class="detailField"><spring:message code="profilePage.info.email"/></span>
                                <c:out value="${user.email}."/>
                            </h3>
                        </div>
                        <div class="col-12 mt-2">
                            <h3 class="info">
                                <span class="detailField"><spring:message code="profilePage.info.phone"/></span>
                                <c:out value="${user.phoneNumber}."/>
                            </h3>
                        </div>
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
