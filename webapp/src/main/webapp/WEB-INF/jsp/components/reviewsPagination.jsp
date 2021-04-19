<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="modal fade" id="reviewsModal" data-paginationmodal="${paginationModal}" data-show="true" data- tabindex="-1" aria-labelledby="newReviewLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="container-lg roundedBorder pb-4 pt-5 px-5" style="background-color: #FAFAFA;">
                <div class="row">
                    <c:url value="/jobs/${job.id}" var="postPath"/>
                    <form action="${postPath}" id="reviewsPaginationForm" type="hidden">
                        <input type="hidden" name="page" id="pageInput">
                        <input type="hidden" name="paginationModal" id="paginationModalInput">
                    </form>
                    <div class="col-12 d-flex justify-content-start align-items-center">
                        <h2 class="sectionTitle">
                            <spring:message code="job.review.title"/><c:out value="${job.jobProvided}"/>
                        </h2>
                    </div>
                    <c:forEach var="review" items="${results.results}">
                        <div class="col-12 mt-3">
                            <%@ include file="cards/reviewCard.jsp" %>
                        </div>
                    </c:forEach>
                    <%@ include file="pagination.jsp" %>
                </div>
            </div>
        </div>
    </div>
</div>


