<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title><spring:message code="productName"/> | <spring:message code="job.editTitle"/> <c:out
            value="${job.jobProvided}"/></title>

    <%@ include file="../../components/includes/headers.jsp" %>

    <link href='<c:url value="/resources/css/job.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/css/newJob.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/css/pagination.css"/>' rel="stylesheet">
    <c:url value="/jobs/${job.id}/edit" var="postPath"/>

</head>

<body>
<div class="container-fluid px-0 outerContainer">
    <%@ include file="../../components/navbar.jsp" %>
    <div class="container-fluid py-4 px-0">
        <div class="container-lg p-5 bigContentContainer">
            <form:form modelAttribute="editJobForm" action="${postPath}" name="editJobForm"
                       enctype="multipart/form-data" id="editJobForm"
                       class="jobForm" method="POST">
                <div class="row mt-3">
                    <div class="col-12 col-lg-6 d-flex align-content-center">
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-12 px-0 d-flex justify-content-lg-start justify-content-center align-items-start">
                                    <div>
                                        <div style="height: 400px; width:400px;">
                                            <c:choose>
                                                <c:when test="${fn:length(job.imagesId) gt 0}">
                                                    <div id="carouselExampleControls" class="carousel slide"
                                                         data-ride="carousel">
                                                        <div id="carousel" class="carousel-inner">
                                                            <c:forEach var="imageId" items="${job.imagesId}">
                                                                <div class="carousel-item" id="${imageId}">
                                                                    <img src="<c:url value='/jobs/images/${imageId}'/>"
                                                                         alt="${job.category}" class="rounded jobImages"
                                                                         style="object-fit: cover; height: 100%; width: 100%">
                                                                    <span class="imageDelete" style="cursor: pointer;"
                                                                          data-image-id="${imageId}">
                                                                <i class="fas fa-trash fa-2x"
                                                                   style="position:absolute; bottom: 20px; left: 50%; text-align: center; color: red;"></i>
                                                                </span>
                                                                </div>
                                                            </c:forEach>
                                                        </div>

                                                        <c:if test="${fn:length(job.imagesId) gt 1}">

                                                            <a class="carousel-control-prev"
                                                               href="#carouselExampleControls"
                                                               role="button"
                                                               data-slide="prev">
                                                        <span class="carousel-control-prev-icon"
                                                              aria-hidden="true"></span>
                                                                <span class="sr-only">Previous</span>
                                                            </a>
                                                            <a class="carousel-control-next"
                                                               href="#carouselExampleControls"
                                                               role="button"
                                                               data-slide="next">
                                                        <span class="carousel-control-next-icon"
                                                              aria-hidden="true"></span>
                                                                <span class="sr-only">Next</span>
                                                            </a>
                                                        </c:if>

                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <img
                                                            src="<c:url value='/resources/images/${job.category}.jpg'/>"
                                                            alt="${job.category}" class="rounded"
                                                            style="object-fit: cover; height: 100%; width: 100%"/>
                                                </c:otherwise>

                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12 px-0">
                                    <div class="form-group d-flex justify-content-between align-items-center">
                                        <form:label path="images" class="mt-2">
                                            <spring:message code="jobForm.jobImageTitle"/></form:label>
                                        <div>

                                            <span id="imagesQuantity" data-quantity="${job.imagesId.size()}"><c:out value="${job.imagesId.size()}"/></span>
                                            <span>/ <spring:message code="jobForm.imageLimit"/></span>
                                        </div>

                                        <button class="buttonCustom d-flex align-items-center justify-content-center"
                                                type="button" id="addFileButton">
                                            <i class="fas fa-upload mr-1"></i>
                                            <spring:message code="jobForm.ImagesButton"/>
                                        </button>
                                        <input type="file" id="inputFiles" name="images" accept=".png,.jpg,.jpeg"
                                               hidden/>
                                    </div>
                                    <form:errors path="images" cssClass="formError" element="p"/>

                                    <div class="container-fluid p-0" id="imagesHolder">
                                        <div class="row">
                                            <div class="col-12"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-12 col-lg-6 d-flex justify-content-center align-items-start">
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-12 pl-0 form-group">
                                    <form:label class="label" path="jobProvided"><spring:message
                                            code="jobForm.jobNameTitle"/> </form:label>
                                    <form:input type="text" path="jobProvided" value="${job.jobProvided}"
                                                cssErrorClass="form-control is-invalid" id="state"
                                                class="form-control"/>
                                    <form:errors path="jobProvided" cssClass="formError" element="p"/>
                                </div>
                                <div class="col-12 pl-0 form-group ">
                                    <span class="label"><spring:message code="jobForm.jobTypeTitle"/></span><br>
                                    <input class="form-control " type="text"
                                           value="<spring:message code="home.categories.${job.category}"/>"
                                           disabled/>
                                </div>
                                <div class="col-12 pl-0 form-group">
                                    <form:label class="label" path="price"><spring:message
                                            code="jobForm.jobPriceTitle"/></form:label>
                                    <form:input type="number" path="price" value="${job.price}"
                                                cssErrorClass="form-control is-invalid" id="price"
                                                class="form-control"/>
                                    <form:errors path="price" cssClass="formError" element="p"/>
                                </div>
                                <div class="col-12">
                                    <div class="form-group">
                                        <form:input path="paused" type="hidden" id="paused"
                                                    value="${job.paused}"/>
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" value="${job.paused}"
                                                   id="pauseCheck">
                                            <label class="form-check-label" for="pauseCheck">
                                                <spring:message code="job.editPaused"/>
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <hr class="text-left ml-0 my-3" style="width: 100%;">
                <div class="col-12 form-group">
                    <form:label class="label" path="description"><spring:message
                            code="jobForm.jobDescriptionTitle"/></form:label>
                    <form:input type="text" path="description" value="${job.description}"
                                cssErrorClass="form-control is-invalid" id="state" class="form-control"/>
                    <form:errors path="description" cssClass="formError" element="p"/>
                </div>
                <div class="col-12 d-flex justify-content-center align-items-center">
                    <button type="button" id="editFormButton" form="editJobForm" class="w-25 continueBtn my-2 ">
                        <spring:message code="job.submit"/>
                    </button>
                </div>


                <div id="imageIdDeletedContainer" class="d-none">

                </div>

            </form:form>
        </div>
    </div>

</div>

<%@ include file="../../components/footer.jsp" %>

<script src='<c:url value="/resources/js/editJob.js"/>'></script>
<%@ include file="../../components/includes/globalScripts.jsp" %>

</body>
</html>
