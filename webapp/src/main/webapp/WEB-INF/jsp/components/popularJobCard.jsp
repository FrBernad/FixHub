<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="col-12 mt-3 col-md-4 mb-4 mb-md-0 d-flex align-items-center justify-content-center">
    <div class="card jobCard" style="position: relative">
        <div style="padding: 1.25rem">
            <div class="row">
                <div class="col-7">
                    <h5 class="card-title jobCardName">
                        <c:out value="${job.jobProvided}"/>
                    </h5>
                </div>
                <div class="col-5">
                    <i class="fas iconsColor fa-star fa-1x mr-2"></i>
                    <span class="jobCardAvgRating"><c:out value="${job.averageRating}"/></span>
                    <span class="jobCardAvgRatingCount"> (<c:out value="${job.totalRatings})"/></span>
                </div>
            </div>
            <span class="badge badge-pill badge-secondary category"><spring:message code="home.categories.${job.category}"/></span>
        </div>
        <div class="jobCardImgContainer">
            <img src="<c:url value='/resources/images/${job.category}.jpg'/>"
                 class="jobCardImg" alt="${job.category}">
        </div>
        <a href="<c:url value='/jobs/${job.id}'/>" class="stretched-link"></a>
        <div class="jobPrice">
            <p class="text-left mb-0"><spring:message code="popularJobCard.priceText"/><c:out value="${job.price}"/></p>
        </div>
    </div>
</div>
