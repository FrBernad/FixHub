<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title><spring:message code="productName"/> | <c:out value="${job.jobProvided}"/></title>

    <%@ include file="../../components/includes/headers.jsp" %>

    <link href='<c:url value="/resources/css/job.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/css/pagination.css"/>' rel="stylesheet">

</head>

<body>
<div class="container-fluid px-0 outerContainer">
    <%@ include file="../../components/navbar.jsp" %>
    <div class="container-fluid py-4 px-0">
        <div class="container-lg p-5 bigContentContainer">
            <div class="row mt-3">
                <div class="col-12 col-md-6 d-flex justify-content-center align-items-start">
                    <div class="container">
                        <div style="position: relative;height: 400px; width:400px;">
                            <c:choose>
                                <c:when test="${job.imagesId.size() > 0}">

                                    <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
                                        <div id="carousel" class="carousel-inner">
                                            <c:forEach var="imageId" items="${job.imagesId}">
                                                <div class="carousel-item">
                                                    <img
                                                            src="<c:url value='/jobs/images/${imageId}'/>"
                                                            alt="${job.category}" class="rounded"
                                                            style="object-fit: cover; height: 100%; width: 100%">
                                                </div>
                                            </c:forEach>
                                        </div>
                                        <a class="carousel-control-prev" href="#carouselExampleControls" role="button"
                                           data-slide="prev">
                                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                            <span class="sr-only">Previous</span>
                                        </a>
                                        <a class="carousel-control-next" href="#carouselExampleControls" role="button"
                                           data-slide="next">
                                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                            <span class="sr-only">Next</span>
                                        </a>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <img

                                            src="<c:url value='/resources/images/${job.category}.jpg'/>"
                                            alt="${job.category}" class="rounded"
                                            style="object-fit: cover; height: 100%; width: 100%">
                                </c:otherwise>

                            </c:choose>

                            <span class="badge badge-pill badge-secondary jobCategory">
                            <spring:message code="home.categories.${job.category}"/></span>
                            <div class="jobPrice">
                                <p class="text-left mb-0"><spring:message code="job.priceText"/><c:out
                                        value="${job.price}"/></p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-md-6 d-flex justify-content-start align-items-start">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-7">
                                <div class="container-fluid p-0">
                                    <row>
                                        <div class="col-12 pl-0">
                                            <h1 class="jobTitle mt-3"><c:out value="${job.jobProvided}"/></h1>
                                        </div>
                                        <div class="col-12 pl-0">
                                            <h1 class="contactInfo"><c:out value="${job.provider.name}"/> <c:out
                                                    value="${job.provider.surname}"/></h1>
                                        </div>
                                        <div class="col-12 pl-0 mt-2">
                                            <c:forEach begin="1" end="${job.averageRating}">
                                                <i class="fas iconsColor fa-star fa-1x mr-2"></i>
                                            </c:forEach>
                                            <c:forEach begin="${job.averageRating}" end="4">
                                                <i class="far iconsColor fa-star fa-1x mr-2"></i>
                                            </c:forEach>
                                            <span style="font-style: normal; font-weight: 400;">(<c:out
                                                    value="${job.totalRatings})"/></span>
                                        </div>
                                    </row>
                                </div>
                            </div>
                            <div class="col-5 d-flex justify-content-start align-items-center">
                                <a href="<c:url value='/jobs/${job.id}/contact'/>">
                                    <button class="contactBtn"><spring:message code="job.contact"/></button>
                                </a>
                            </div>

                            <hr class="text-left ml-0 my-4" style="width: 80%;">
                            <div class="col-12 mt-2">
                                <div class="container-fluid p-0">
                                    <div class="row">
                                        <div class="col-12 d-flex justify-content-start align-items-center">
                                            <h2 class="sectionTitle mb-3">
                                                <spring:message code="job.information.title"/>
                                            </h2>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-12">
                                            <div class="container-fluid p-0">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <p class="text-left contactInfo">
                                                        <span class="font-weight-bold"><spring:message
                                                                code="job.information.name&surname"/></span> <c:out
                                                                value="${job.provider.name}"/> <c:out
                                                                value="${job.provider.surname}"/>
                                                        </p>
                                                    </div>
                                                    <div class="col-12">
                                                        <p class="text-left">
                                                        <span class="font-weight-bold"><spring:message
                                                                code="job.information.email"/></span> <c:out
                                                                value="${job.provider.email}"/>
                                                        </p>
                                                    </div>
                                                    <div class="col-12">
                                                        <p class="text-left">
                                                        <span class="font-weight-bold"><spring:message
                                                                code="job.information.phone"/></span> <c:out
                                                                value="${job.provider.phoneNumber}"/>
                                                        </p>
                                                    </div>
                                                    <div class="col-12">
                                                        <p class="text-left">
                                                        <span class="font-weight-bold"><spring:message
                                                                code="job.information.state"/></span> <c:out
                                                                value="${location.state}"/>
                                                        </p>
                                                    </div>
                                                    <div class="col-12">
                                                        <p class="text-left">
                                                            <span class="font-weight-bold"><spring:message
                                                                    code="job.information.city"/></span>
                                                            <c:forEach var="city" items="${location.cities}">
                                                                <span class="badge badge-pill badge-primary"><c:out
                                                                        value="${city.name}"/></span>

                                                            </c:forEach>

                                                        </p>

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

            <hr class="text-left ml-0 my-5" style="width: 80%;">

            <div class="container-fluid">
                <div class="row">
                    <div class="col-12">
                        <h1 class="sectionTitle"><spring:message code="job.description.title"/></h1>
                    </div>
                    <div class="col-12">
                        <p class="text-left contactInfo">
                            <c:out value="${job.description}"/>
                        </p>
                    </div>
                </div>
            </div>

            <hr class="text-left ml-0 my-5" style="width: 80%;">

            <div class="container-fluid mt-3">
                <div class="row">
                    <div class="col-12 d-flex justify-content-start align-items-center">
                        <h2 class="sectionTitle">
                            <spring:message code="job.review.title"/><c:out value="${job.jobProvided}"/>
                        </h2>
                    </div>
                    <div class="col-12 d-flex justify-content-start align-items-center">
                        <a href="#" type="button" data-toggle="modal" data-target="#newReview">
                            <spring:message code="job.review.hyperlink"/>
                        </a>
                        <%@ include file="../../components/forms/reviewForm.jsp" %>
                    </div>
                </div>
                <div class="row mt-2">
                    <div class="col-12 d-flex align-items-center">
                        <div class="container-fluid">
                            <div class="row">
                                <c:choose>
                                    <c:when test="${results.totalPages > 0}">
                                        <c:forEach var="review" items="${results.results}">
                                            <div class="col-12 mt-3">
                                                <%@ include file="../../components/cards/reviewCard.jsp" %>
                                            </div>
                                        </c:forEach>
                                        <c:if test="${results.totalPages>1}">
                                            <div class="col-12 mt-4 pl-0">
                                                <a href="#" type="button" data-toggle="modal"
                                                   data-target="#reviewsModal">
                                                    Ver más
                                                </a>
                                                <%@ include file="../../components/reviewsPagination.jsp" %>
                                            </div>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="col-12 d-flex align-items-center justify-content-center">
                                            <div class="container mt-2 d-flex align-items-center justify-content-center">
                                                <p class="m-0 text-center p-4" style="font-size: 16px">
                                                    <spring:message code="job.review.noReviews"/>
                                                </p>
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
    </div>

</div>

<%@ include file="../../components/footer.jsp" %>

<script src='<c:url value="/resources/js/job.js"/>'></script>
<%@ include file="../../components/includes/bottomScripts.jsp" %>

</body>
</html>
