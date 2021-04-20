<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url value="/jobs/new" var="postPath"/>
<form:form modelAttribute="jobForm" action="${postPath}" enctype="multipart/form-data" id="jobForm" class="jobForm" method="POST">
    <div class="form-group">
        <form:label class="label" path="jobProvided"><spring:message code="jobForm.jobNameTitle"/></form:label>
        <form:input type="text" path="jobProvided" id="jobProvided" class="form-control" cssErrorClass="form-control is-invalid"/>
        <form:errors path="jobProvided" cssClass="formError" element="p"/>
    </div>
    <div class="row">
        <div class="col-6">
            <div class="form-group">
                <form:label class="label" path="jobCategory"><spring:message code="jobForm.jobTypeTitle"/></form:label>
                <form:select id="jobCategory" path="jobCategory" class="form-control">
                    <c:forEach var="category" items="${categories}">
                        <option selected value="${category}"><spring:message code="home.categories.${category}"/></option>
                    </c:forEach>
                </form:select>
                <form:errors path="jobCategory" cssClass="formError" element="p"/>
            </div>
        </div>
        <div class="col-6">
            <div class="form-group">
                <form:label class="label" path="price"><spring:message code="jobForm.jobPriceTitle"/></form:label>
                <form:input type="number" path="price" id="price" class="form-control" cssErrorClass="form-control is-invalid"/>
                <form:errors path="price" cssClass="formError" element="p"/>
            </div>
        </div>
    </div>
    <div class="form-group">
        <form:label class="label" path="description"><spring:message code="jobForm.jobDescriptionTitle"/></form:label>
        <form:textarea class="form-control" path="description" id="description" cssErrorClass="form-control is-invalid"/>
        <form:errors path="description" cssClass="formError" element="p"/>

    </div>

    <div class="form-group d-flex justify-content-between align-items-center">
        <form:label path="images" cssClass="mb-0">
            <spring:message code="jobForm.jobImageTitle"/></form:label>
        <button class="buttonCustom" type="button" id="addFileButton">
            <i class="fas fa-upload mr-1"></i>
            <spring:message code="jobForm.ImagesButton"/>
        </button>
        <input type="file" id="inputFiles" name="images" hidden/>
    </div>

    <input type="hidden" value="${user.id}" name="userId">

    <div class="container-fluid p-0" id="imagesHolder">
        <div class="row">
            <div class="col-3"></div>
        </div>
    </div>

    <div class="col-12 px-0 d-flex align-items-center justify-content-center">
        <button type="button" id="jobFormButton" class="w-100 continueBtn my-2">
            <spring:message code="jobForm.buttonText"/>
        </button>
    </div>


</form:form>
