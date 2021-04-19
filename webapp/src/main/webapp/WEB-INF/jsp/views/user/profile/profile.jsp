<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title><spring:message code="productName"/> | ${loggedUser.name} ${loggedUser.surname}</title>
    <%@ include file="../../../components/includes/headers.jsp" %>
    <link href='<c:url value="/resources/css/profile.css"/>' rel="stylesheet">
</head>
<body>
<div class="outerContainer pb-4">
    <%@ include file="../../../components/navbar.jsp" %>
    <div class="container-lg userContainerProfile py-4">
        <div class="container-lg px-0 m-2">
            <div class="container-fluid">
                <div class="row justify-content-center align-content-center">
                    <div class="col-4 align-content-center justify-content-center">
                        <img src="<c:url value='/resources/images/userProfile.png'/>" class="rounded card-img-bottom img-thumbnail my-3" alt="Imagen no disponible">
                    </div>
                </div>
                <div class="row justify-content-center align-content-center">
                    <div class="col-md-auto justify-content-center align-content-center">
                        <div class="userNameProfile">
                            <p>${loggedUser.name} ${loggedUser.surname}</p>
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
                        <a href="<c:url value='/account/update'/>">
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
                                ${loggedUser.email}
                            </div>
                        </div>
                        <div class="row my-2">
                            <div class="col-4 infoField">
                                <p style="margin: 0;"><spring:message code="profilePage.info.phone"/></p>
                            </div>
                            <div class="col-8">
                                ${loggedUser.phoneNumber}
                            </div>
                        </div>
                        <div class="row my-2">
                            <div class="col-4 infoField">
                                <p style="margin: 0"><spring:message code="profilePage.info.state"/></p>
                            </div>
                            <div class="col-8">
                                ${loggedUser.state}
                            </div>
                        </div>
                        <div class="row my-2">
                            <div class="col-4 infoField">
                                <p style="margin: 0"><spring:message code="profilePage.info.city"/> </p>
                            </div>
                            <div class="col-8">
                                ${loggedUser.city}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../../../components/footer.jsp" %>
<%@ include file="../../../components/includes/bottomScripts.jsp"%>

</body>
</html>
