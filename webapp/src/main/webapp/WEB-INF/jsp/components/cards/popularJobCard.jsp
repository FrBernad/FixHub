<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="card jobCard" style="position: relative">
    <div style="padding: 1.25rem">
        <div class="row">
            <div class="col-12">
                <h5 class="card-title jobCardName">
                    <c:out value="${job.jobProvided}"/>
                </h5>
            </div>
            <div class="col-7">
                <p class="card-text m-0 names"><c:out value="${job.provider.name} ${job.provider.surname}"/></p>
            </div>
            <div class="col-5 d-flex justify-content-end align-items-center">
                <div style="height: 23px">
                    <i class="fas iconsColor fa-star fa-1x mr-2"></i>
                    <span class="jobCardAvgRating"><c:out value="${job.averageRating}"/></span>
                    <span class="jobCardAvgRatingCount"> (<c:out value="${job.totalRatings})"/></span>
                </div>
            </div>
        </div>
    </div>
    <div class="jobCardImgContainer">
        <c:choose>
            <c:when test="${job.imagesId.size() > 0}">
                <img src="<c:url value='/jobs/images/${job.getJobThumbnailId()}'/>"
                     alt="${job.category}" class="card-img jobCardImg">
            </c:when>
            <c:otherwise>
                <img class="card-img jobCardImg"
                     src="<c:url value='/resources/images/${job.category}.jpg'/>"
                     alt="${job.category}">
            </c:otherwise>
        </c:choose>
        <span class="badge badge-pill badge-secondary category" style="position: absolute; top: 10px; left: 10px">
             <spring:message code="home.categories.${job.category}"/>
        </span>
    </div>
    <a href="<c:url value='/jobs/${job.id}'/>" class="stretched-link"></a>
    <div class="jobPrice">
        <p class="text-left mb-0"><spring:message code="popularJobCard.priceText"/><c:out value="${job.price}"/></p>
    </div>
</div>
