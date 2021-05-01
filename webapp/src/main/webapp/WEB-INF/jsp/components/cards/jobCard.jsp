<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="card jobCard">
    <div class="jobCardImgContainer" style="position:relative;">
        <c:choose>
            <c:when test="${job.coverImageId != 0}">
                <img src="<c:url value='/jobs/images/${job.getJobThumbnailId()}'/>"
                     alt="${job.category}" class="card-img jobCardImg">
            </c:when>
            <c:otherwise>
                <img class="card-img jobCardImg"
                     src="<c:url value='/resources/images/${job.category}.jpg'/>"
                     alt="${job.category}">
            </c:otherwise>
        </c:choose>
        <div class="jobPrice">
            <p class="text-left mb-0"><spring:message code="jobCard.priceText"/><c:out value="${job.price}"/></p>
        </div>
        <span class="badge badge-pill badge-secondary category jobCategory">
                  <spring:message code="home.categories.${job.category}"/>
            </span>
    </div>
    <div style="padding: 1.25rem">
        <div class="row">
            <div class="col-12 d-flex align-items-center">
                <h5 class="card-title m-0 jobCardName"><c:out value="${job.jobProvided}"/></h5>
            </div>
            <div class="col-6 col-md-7 my-2">
                <p class="card-text m-0"><c:out value="${job.provider.name} ${job.provider.surname}"/></p>
            </div>
            <div class="col-6 my-2 pl-0 col-md-5 d-flex align-items-center justify-content-end">
                <div>
                    <i class="fas iconsColor fa-star fa-1x mr-2" style="color: rgb(0,59,109)"></i>
                    <span class="jobCardAvgRating"><c:out value="${job.averageRating}"/></span>
                    <span class="jobCardAvgRatingCount"> (<c:out value="${job.totalRatings})"/></span>
                </div>
            </div>
        </div>
    </div>
    <a href="<c:url value='/jobs/${job.id}'/>" class="stretched-link"></a>
</div>
