<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:url value="/user/join" var="postPath"/>
<form:form modelAttribute="joinForm" action="${postPath}" id="joinForm" method="POST">
    <div class="row">
        <div class="col">
            <p class="subtitle text-left mb-4 font-weight-bold">
                <spring:message code="joinForm.availableHours"/>
            </p>
        </div>
    </div>
    <div class="form-group">
        <div class="row">
            <div class="col-6">
                <form:label class="label" path="startTime"><spring:message code="joinForm.startTime"/></form:label>
            </div>
            <div class="col-md-4 col-6 align-items-center justify-content-end">
                <form:input path="startTime" cssClass="timepicker px-2 w-100" id="startTime"/>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="row">
            <div class="col-6">
                <form:label class="label" path="endTime"><spring:message code="joinForm.endTime"/></form:label>
            </div>
            <div class="col-md-4 col-6 align-items-center justify-content-end">
                <form:input path="endTime" cssClass="timepicker px-2 w-100" id="endTime"/>
            </div>
            <div class="col-12">
                <form:errors path="" cssClass="formError text-center mt-2" element="p"/>
            </div>
        </div>
    </div>
    <div class="form-group">
        <form:label class="label font-weight-bold" path="state"><spring:message code="joinForm.availableZones"/>*</form:label>
        <form:input path="state" type="hidden" id="state"/>
        <div class="row">
            <div class="col d-flex align-items-center justify-content-center">
                <div class="dropdown">
                    <button class="dropdown-custom dropdown-toggle mt-2" type="button" id="stateDropdown"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <spring:message code="joinForm.state"/>
                        <span class="state" id="stateName">
                        </span>
                    </button>
                    <div class="dropdown-menu dropdown-size">
                        <c:forEach var="state" items="${states}">
                            <div class="input-group">
                                <button class="dropdown-item stateButton" type="button" data-id="${state.id}"
                                        data-name="${state.name}">
                                        ${state.name}
                                </button>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        <form:errors path="state" cssClass="formError text-center mt-2" element="p"/>
    </div>
    <div class="col-12 d-flex align-items-center justify-content-center">
        <button class="w-100 continueBtn my-1" form="joinForm">
            <spring:message code="joinForm.continue"/>
        </button>
    </div>
</form:form>