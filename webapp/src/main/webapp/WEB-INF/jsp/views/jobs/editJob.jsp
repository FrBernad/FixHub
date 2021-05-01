<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><spring:message code="productName"/> | <spring:message code="job.editTitle"/> <c:out value="${job.jobProvided}"/></title>

    <%@ include file="../../components/includes/headers.jsp" %>

    <link href='<c:url value="/resources/css/job.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/css/pagination.css"/>' rel="stylesheet">
    <c:url value="/jobs/${job.id}/edit" var="postPath"/>

</head>

<body>
<div class="container-fluid px-0 outerContainer">
    <%@ include file="../../components/navbar.jsp" %>
    <div class="container-fluid py-4 px-0">
        <div class="container-lg p-5 bigContentContainer">
            <form:form modelAttribute="jobForm" action="${postPath}" enctype="multipart/form-data" id="jobForm" class="jobForm" method="POST">
            <div class="row mt-3">
                <div class="col-6 col-md-6 d-flex align-content-center">
                    <div class="container-fluid">
                        <div class="row " style="height: 300px; width:300px;">
                            <div class="col d-flex align-content-center justify-content-center" style="height: 300px; width:300px;">

                                <c:choose>
                                    <c:when test="${job.imagesId.size() > 0}">

                                        <div id="carouselExampleControls" class="carousel slide justify-content-center" data-ride="carousel">
                                            <div id="carousel" class="carousel-inner justify-content-center">
                                                <c:forEach var="imageId" items="${job.imagesId}">
                                                    <div class="carousel-item">
                                                        <img
                                                                src="<c:url value='/jobs/images/${imageId}'/>"
                                                                alt="${job.category}"  class="rounded jobImages"
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
                                                style="object-fit: cover; height: 100%; width: 100%"/>
                                    </c:otherwise>

                                </c:choose>
                            </div>
                        </div>
                        <div class="row my-2">
                            <div class="col d-flex align-content-center justify-content-center">
                                <form:input path="images" type="file" name="images"  id="images" multiple="multiple" accept=".png,.jpg,.jpeg"/>
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
                                            <form:label class="label" path="jobProvided"><spring:message code="jobForm.jobNameTitle"/> </form:label>
                                            <form:input type="text" path="jobProvided" value="${job.jobProvided}" cssErrorClass="form-control is-invalid" id="state" class="form-control"/>
                                            <form:errors path="jobProvided" cssClass="formError" element="p"/>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-12 pl-0 form-group ">
                                            <form:label class="label" path="jobCategory"><spring:message code="jobForm.jobTypeTitle"/></form:label>
                                            <form:select id="jobCategory" path="jobCategory" class="form-control">
                                                <c:forEach var="category" items="${categories}">
                                                    <option selected value="${category}"><spring:message code="home.categories.${category}"/></option>
                                                </c:forEach>
                                            </form:select>
                                            <form:errors path="jobCategory" cssClass="formError" element="p"/>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-12 pl-0 form-group">
                                            <form:label class="label" path="price"><spring:message code="jobForm.jobPriceTitle"/></form:label>
                                            <form:input type="number" path="price" value="${job.price}" cssErrorClass="form-control is-invalid" id="price" class="form-control"/>
                                            <form:errors path="price" cssClass="formError" element="p"/>
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
                    <form:label class="label" path="description"><spring:message code="jobForm.jobDescriptionTitle"/></form:label>
                    <form:input type="text" path="description" value="${job.description}" cssErrorClass="form-control is-invalid" id="state" class="form-control" cssStyle="height: 200px; text-align: start"/>
                    <form:errors path="description" cssClass="formError" element="p"/>
                </div>
            </div>
            <div class="row mt-3 align-content-center justify-content-center">
                <button type="submit" id="editFormButton" form="jobForm" class="w-25 continueBtn my-2 ">
                    <spring:message code="job.submit"/>
                </button>
            </div>
        </div>
        </form:form>
    </div>
</div>

<%@ include file="../../components/footer.jsp" %>

<script src='<c:url value="/resources/js/job.js"/>'></script>

<%@ include file="../../components/includes/globalScripts.jsp" %>

</body>
</html>
