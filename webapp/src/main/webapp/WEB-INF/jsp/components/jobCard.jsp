<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="col-12 mt-3 col-md-4 mb-4 mb-md-0 d-flex align-items-center justify-content-center">
    <div class="card jobCard">
        <div class="jobCardImgContainer" style="position:relative;">
            <img
                    class="card-img jobCardImg"
                    src="<c:url value='/resources/images/${job.category}.jpg'/>"
                    alt="${job.category}">
            <div class="jobPrice">
                <p class="text-left mb-0"><spring:message code="jobCard.priceText"/><c:out value="${job.price}"/></p>
            </div>
            <span class="badge badge-pill badge-secondary category jobCategory">
                  <spring:message code="home.categories.${job.category}"/>
            </span>
        </div>
        <div style="padding: 1.25rem">
            <div class="row">
                <div class="col-12 col-md-7 d-flex align-items-center">
                    <h5 class="card-title m-0 jobCardName"><c:out value="${job.jobProvided}"/></h5>
                </div>
                <div class="col-12 col-md-5 d-flex align-items-center">
                    <div>
                        <i class="fas iconsColor fa-star fa-1x mr-2"></i>
                        <span class="jobCardAvgRating"><c:out value="${job.averageRating}"/></span>
                        <span class="jobCardAvgRatingCount"> (<c:out value="${job.totalRatings})"/></span>
                    </div>
                </div>
                <div class="col-12 my-1">
                    <p class="card-text m-0"><c:out value="${job.provider.name}"/></p>
                </div>
            </div>
        </div>
        <a href="<c:url value='/jobs/${job.id}'/>" class="stretched-link"></a>
    </div>
</div>