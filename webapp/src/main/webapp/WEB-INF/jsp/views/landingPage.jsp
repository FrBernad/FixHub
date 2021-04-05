<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title><spring:message code="productName"/></title>
    <%@ include file="../components/headers.jsp" %>
    <link href='<c:url value="/resources/css/landingPage.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/css/jobCard.css"/>' rel="stylesheet">
</head>

<body>
<div class="container-fluid p-0 h-100 bgImg d-flex flex-column">
    <%@ include file="../components/navbar.jsp" %>
    <div class="container-lg flex-grow-1">
        <div class="row w-100 h-100 align-items-center justify-content-center">
            <div class="col-12 px-0">
                <div class="container-lg">
                    <div class="row w-100">
                        <div class="col-10 col-md-8 w-50 d-flex justify-content-start align-items-center">
                            <h2 class="text-left photoText"><spring:message code="landingPage.title"/></h2>
                        </div>
                        <div class="col-8 col-md-9 w-50 d-flex justify-content-center align-items-center">
                            <form id="searchForm" action="<c:url value="/discover/search"/>" method="GET" class="mb-0"
                                  style="width: 100%">
                                <div class="input-group">
                                    <input id="searchFormInput" placeholder="<spring:message code="landingPage.searchbarPlaceholder"/>"
                                           class="inputRadius form-control p-4" name="searchPhrase">
                                    <div class="input-group-prepend mr-0">
                                        <button id="searchFormButton" class="btn btn-lg inputBtn" type="button"><spring:message
                                                code="landingPage.searchButtonText"/></button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="col-8 col-lg-9 mt-3 d-flex justify-content-start align-items-center">
                            <c:forEach var="category" items="${categories}" begin="0" end="4">
                                <form action="<c:url value="/discover/search"/>" method="GET" class="mb-0">
                                    <input type="hidden" name="filter" value="${category}">
                                    <div class="input-group-prepend">
                                        <button  class="btn-sm suggestionBtn mr-4">
                                            <spring:message code="home.categories.${category}"/>
                                        </button>
                                    </div>
                                </form>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="container-fluid py-5" style="background-color: rgb(255,255,255)">
    <div class="container-lg d-flex align-items-center py-5">
        <div class="row align-items-start">
            <div class="col-12">
                <h1 class="py-3 stepSectionTitle"><spring:message code="landingPage.howItWorks.title"/></h1>
            </div>
            <div class="col-4 d-flex justify-content-center align-items-start">
                <div class="row">
                    <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                        <i class="fas iconsColor fa-search fa-2x"></i>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                        <p class="text-start stepHeader"><spring:message
                                code="landingPage.howItWorks.firstSection.title"/></p>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-center align-items-center">
                        <p class="text-start stepBody"><spring:message
                                code="landingPage.howItWorks.firstSection.text"/></p>
                    </div>
                </div>
            </div>
            <div class="col-4 d-flex justify-content-center align-items-start">
                <div class="row">
                    <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                        <i class="fas iconsColor fa-star fa-2x"></i>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                        <p class="text-start stepHeader"><spring:message
                                code="landingPage.howItWorks.secondSection.title"/></p>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-center stepBody align-items-center">
                        <p class="text-start"><spring:message code="landingPage.howItWorks.secondSection.text"/></p>
                    </div>
                </div>
            </div>
            <div class="col-4 d-flex justify-content-center align-items-start">
                <div class="row">
                    <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                        <i class="fas iconsColor fa-handshake fa-2x"></i>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                        <p class="text-start stepHeader"><spring:message
                                code="landingPage.howItWorks.thirdSection.title"/></p>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-center stepBody align-items-center">
                        <p class="text-start"><spring:message code="landingPage.howItWorks.thirdSection.text"/></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="container-fluid py-5" style="background-color: var(--tertiary-color);">
    <div class="container-lg py-5 d-flex align-items-center w-100">
        <div class="row align-items-center justify-content-center w-100">
            <div class="col-12">
                <h1 class="py-3 stepSectionTitle mb-0"><spring:message
                        code="landingPage.mostPopularJobs.title"/></h1>
            </div>
            <div class="col-12 p-0">
                <div class="container-fluid p-0">
                    <div class="row ${jobs.size()%3 == 0 ? 'justify-content-between': 'justify-content-start'} align-items-center m-0">
                        <c:choose>
                            <c:when test="${jobs.size()>0}">
                                <c:forEach begin="0" end="2" var="job" items="${jobs}">
                                    <%@ include file="../components/popularJobCard.jsp" %>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div class="col-12 d-flex align-items-center justify-content-center">
                                    <div class="container mt-5 d-flex align-items-center justify-content-center noJobsFound">
                                        <p class="m-0 text-center p-4" style="font-size: 16px;"><spring:message code="landingPage.mostPopularJobs.noPopularJobsText"/></p>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="container-fluid py-5" style="background-color: var(--primary-color);">
    <div class="container-lg px-0 d-flex align-items-center py-5">
        <div class="row align-items-center justify-content-between w-100">
            <div class="col-12 col-md-6 d-flex justify-content-start align-items-center">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-12 mb-4 d-flex justify-content-start align-items-center">
                            <h2 class="p-0 text-left joinUsTitle"><spring:message code="landingPage.joinUs.descriptionTitle"/> </h2>
                        </div>
                        <div class="col-12 d-flex justify-content-start align-items-center">
                            <a href="<c:url value='/join'/>">
                                <button class="learnMoreBtn"><spring:message
                                        code="landingPage.joinUs.buttonText"/></button>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-12 col-md-6 d-flex justify-content-end align-items-center">
                <img class="jobProvideImg" src='<c:url value="/resources/images/manWorking.jpg"/>'
                     alt="Hombre trabajando">
            </div>
        </div>
    </div>
</div>

<script src='<c:url value="/resources/js/landingPage.js"/>'></script>

<%@ include file="../components/footer.jsp" %>

<%@ include file="../components/bottomScripts.jsp" %>

</body>
</html>
