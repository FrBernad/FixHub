<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="modal fade" id="reviewsModal" data-show="true" tabindex="-1" aria-labelledby="newReviewLabel"
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
                    <div class="col-12 align-items-center justify-content-center mt-4
                                        ${results.totalPages<=1 ? 'd-none':''} ">
                        <nav class="d-flex align-items-center justify-content-center">
                            <ul class="pagination mb-0" id="pagination" data-paginationmodal="${paginationModal}"
                                data-page="${results.page}">
                                <li class="page-item ${results.isFirst() ? 'disabled' : ''}">
                                    <a class="page-link" id="prev" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>
                                <c:forEach begin="1" end="${results.totalPages}" varStatus="status">
                                    <li class="page-item ${results.page == status.index-1 ? 'active' : ''}"><a
                                            class="page-link index"
                                            data-index="${status.index}">${status.index}</a>
                                    </li>
                                </c:forEach>
                                <li class="page-item ${results.isLast() ? 'disabled' : ''}">
                                    <a class="page-link" id="next" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


