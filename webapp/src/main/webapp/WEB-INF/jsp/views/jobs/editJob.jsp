<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
            <form:form modelAttribute="editJobForm" action="${postPath}" enctype="multipart/form-data" id="editJobForm"
                       class="jobForm" method="POST">
            <div class="row mt-3">
                <div class="col-6 col-md-6 d-flex align-content-center">
                    <div class="container-fluid">
                        <div class="row " style="height: 300px; width:300px;">
                            <div class="col d-flex align-content-center justify-content-center"
                                 style="height: 300px; width:300px;">

                                <c:choose>
                                    <c:when test="${job.imagesId.size() > 0}">
                                        <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
                                            <div id="carousel" class="carousel-inner">
                                                <c:forEach var="imageId" items="${job.imagesId}">
                                                    <div class="carousel-item" id="${imageId}">
                                                        <img src="<c:url value='/jobs/images/${imageId}'/>"
                                                             alt="${job.category}" class="rounded jobImages"
                                                             data-image-id="${imageId}"
                                                             style="object-fit: cover; height: 100%; width: 100%">
                                                    </div>
                                                </c:forEach>
                                            </div>

                                            <c:if test="${job.imagesId.size() > 1}">

                                                <a class="carousel-control-prev" href="#carouselExampleControls"
                                                   role="button"
                                                   data-slide="prev">
                                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                                    <span class="sr-only">Previous</span>
                                                </a>
                                                <a class="carousel-control-next" href="#carouselExampleControls"
                                                   role="button"
                                                   data-slide="next">
                                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
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
                        <div class="form-group d-flex justify-content-between align-items-center">
                            <form:label path="images" class="mt-2">
                                <spring:message code="jobForm.jobImageTitle"/></form:label>
                            <button class="buttonCustom" type="button" id="addFileButton">
                                <i class="fas fa-upload mr-1"></i>
                                <spring:message code="jobForm.ImagesButton"/>
                            </button>
                            <input type="file" id="inputFiles" name="images" accept=".png,.jpg,.jpeg" hidden/>
                        </div>

                        <div class="container-fluid p-0" id="imagesHolder">
                            <div class="row">
                                <div class="col-3"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-6 col-md-6 d-flex justify-content-center align-items-center">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-7 d-flex justify-content-center align-content-center">
                                <div class="container-fluid p-0">
                                    <div class="row">
                                        <div class="col-12 pl-0 form-group">
                                            <form:label class="label" path="jobProvided"><spring:message
                                                    code="jobForm.jobNameTitle"/> </form:label>
                                            <form:input type="text" path="jobProvided" value="${job.jobProvided}"
                                                        cssErrorClass="form-control is-invalid" id="state"
                                                        class="form-control"/>
                                            <form:errors path="jobProvided" cssClass="formError" element="p"/>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-12 pl-0 form-group ">
                                            <span class="label"><spring:message code="jobForm.jobTypeTitle"/></span><br>
                                            <input class="form-control "type="text" value="<spring:message code="home.categories.${job.category}"/>" disabled/>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-12 pl-0 form-group">
                                            <form:label class="label" path="price"><spring:message
                                                    code="jobForm.jobPriceTitle"/></form:label>
                                            <form:input type="number" path="price" value="${job.price}"
                                                        cssErrorClass="form-control is-invalid" id="price"
                                                        class="form-control"/>
                                            <form:errors path="price" cssClass="formError" element="p"/>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col">
                                            <div class="form-group">
                                                <form:input path="paused" type="hidden" id="paused"
                                                            value="${job.paused}"/>
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio"
                                                           name="inlineRadioOptions" id="editPaused">
                                                    <label class="form-check-label" for="editPaused"><spring:message
                                                            code="job.editPaused"/></label>
                                                </div>
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio"
                                                           name="inlineRadioOptions" id="editUnpaused">
                                                    <label class="form-check-label" for="editUnpaused"><spring:message
                                                            code="job.editUnpaused"/></label>
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
            <hr class="text-left ml-0 my-3" style="width: 80%;">
            <div class="row mt-3">
                <div class="col-12 form-group">
                    <form:label class="label" path="description"><spring:message
                            code="jobForm.jobDescriptionTitle"/></form:label>
                    <form:input type="text" path="description" value="${job.description}"
                                cssErrorClass="form-control is-invalid" id="state" class="form-control"
                                cssStyle="height: 200px; text-align: start"/>
                    <form:errors path="description" cssClass="formError" element="p"/>
                </div>
            </div>
            <div class="row mt-3 align-content-center justify-content-center">
                <button type="button" id="editFormButton" form="editJobForm" class="w-25 continueBtn my-2 ">
                    <spring:message code="job.submit"/>
                </button>
            </div>
        </div>


        <div id="imageIdDeletedContainer" class="d-none">

        </div>

        </form:form>
    </div>
</div>

<%@ include file="../../components/footer.jsp"%>

<script src='<c:url value="/resources/js/editJob.js"/>'></script>
<%@ include file="../../components/includes/globalScripts.jsp" %>

</body>
</html>
