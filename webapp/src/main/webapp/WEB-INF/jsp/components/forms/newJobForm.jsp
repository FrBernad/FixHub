<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:url value="/jobs/new" var="postPath"/>
<form:form modelAttribute="jobForm" action="${postPath}" enctype="multipart/form-data" id="jobForm" class="jobForm"
           method="POST">
    <div class="form-group">
        <form:label path="jobProvided"><spring:message code="jobForm.jobNameTitle"/> <span
                class="required-field">*</span></form:label>
        <form:input type="text" path="jobProvided" id="jobProvided" class="form-control"
                    cssErrorClass="form-control is-invalid"/>
        <form:errors path="jobProvided" cssClass="formError" element="p"/>
    </div>
    <div class="row">
        <div class="col-12">
            <div class="form-group">
                <form:label path="jobCategory"><spring:message code="jobForm.jobTypeTitle"/> <span
                        class="required-field">*</span></form:label>
                <form:input type="hidden" id="jobCategory" path="jobCategory"/>
                <div class="dropdown">
                    <button class="dropdown-custom dropdown-toggle w-100 d-flex align-items-center justify-content-between"
                            type="button" id="categoryDropdown"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <span>
                            <spring:message code="newJob.category"/>
                            </span>
                        <span class="category" id="categoryName">-</span>
                    </button>
                    <div class="dropdown-menu w-100">
                        <c:forEach var="category" items="${categories}">
                            <spring:message var="categoryName" code="home.categories.${category}"/>
                            <div class="input-group">
                                <button class="dropdown-item categoryButton " type="button" data-name="${category}"
                                        data-i18name="${categoryName}">
                                    <c:out value="${categoryName}"/>
                                </button>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <form:errors path="jobCategory" cssClass="formError" element="p"/>
            </div>
        </div>
        <div class="col-12">
            <div class="form-group">
                <form:label path="price"><spring:message code="jobForm.jobPriceTitle"/> <span
                        class="required-field">*</span></form:label>
                <form:input type="number" path="price" id="price" class="form-control"
                            cssErrorClass="form-control is-invalid"/>
                <form:errors path="price" cssClass="formError" element="p"/>
            </div>
        </div>
    </div>
    <div class="form-group">
        <form:label path="description"><spring:message code="jobForm.jobDescriptionTitle"/> <span
                class="required-field">*</span></form:label>
        <form:textarea class="form-control" path="description" id="description"
                       cssErrorClass="form-control is-invalid"/>
        <form:errors path="description" cssClass="formError" element="p"/>
    </div>

    <div class="form-group d-flex justify-content-between align-items-center">
        <div class="container-fluid px-0">
            <div class="row">
                <div class="col-6 d-flex align-items-center justify-content-start">
                    <form:label path="images" class="mb-0">
                        <spring:message code="jobForm.jobImageTitle"/>
                    </form:label>
                </div>
                <div class="col-2 d-flex px-0 align-items-center justify-content-start">
                    <div>
                        <span id="imagesQuantity" data-max="${maxImagesPerJob}">0</span>
                        <span>/ <c:out value="${maxImagesPerJob}"/></span>
                    </div>
                </div>
                <div class="col-4 d-flex align-items-center justify-content-center">
                    <button class="buttonCustom buttonEnabled d-flex align-items-center justify-content-center" type="button" id="addFileButton">
                        <i class="fas fa-upload mr-1"></i>
                        <spring:message code="jobForm.ImagesButton"/>
                    </button>
                </div>

                <div class="col-12">
                    <input type="file" id="inputFiles" name="images" accept=".png,.jpg,.jpeg" hidden/>
                </div>
            </div>
        </div>


    </div>
    <form:errors path="images" cssClass="formError" element="p"/>

    <input type="hidden" value="${user.id}" name="userId">

    <div class="container-fluid p-0" id="imagesHolder">
        <div class="row">
            <div class="col-12"></div>
        </div>
    </div>

    <div class="col-12 px-0 d-flex align-items-center justify-content-center">
        <button type="button" id="jobFormButton" class="w-100 continueBtn my-2">
            <span id="loadingSpinner" class="spinner-border spinner-border-sm mr-1" hidden role="status"
                  aria-hidden="true"></span>
            <spring:message code="jobForm.buttonText"/>
        </button>
    </div>

</form:form>
