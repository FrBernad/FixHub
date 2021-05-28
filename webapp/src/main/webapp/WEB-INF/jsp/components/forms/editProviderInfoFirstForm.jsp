<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:url value="/user/account/updateProviderStateAndTime" var="postPath"/>
<form:form modelAttribute="providerInfoFirstForm" action="${postPath}" id="providerInfoFirstForm" method="POST">
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
            <div class="col-md-3 col-6 align-items-center justify-content-end">
                <form:input path="startTime" cssClass="startTimepicker px-2 w-100" id="startTime"
                            value="${loggedUser.providerDetails.schedule.startTime}"/>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="row">
            <div class="col-6">
                <form:label class="label" path="endTime"><spring:message code="joinForm.endTime"/></form:label>
            </div>
            <div class="col-md-3 col-6 align-items-center justify-content-end">
                <form:input path="endTime" cssClass="endTimepicker px-2 w-100" id="endTime"
                            value="${loggedUser.providerDetails.schedule.endTime}"/>
            </div>
            <div class="col-12">
                <form:errors path="" cssClass="formError text-center mt-2" element="p"/>
            </div>

        </div>
    </div>
    <div class="form-group">
        <spring:hasBindErrors name="joinForm">
            <c:if test="${errors.globalErrorCount > 0}">
                <div class="alert alert-danger"><form:errors/></div>
            </c:if>
        </spring:hasBindErrors>
    </div>
    <div class="form-group">
        <form:label class="label font-weight-bold" path="state"><spring:message
                code="joinForm.availableZones"/>*</form:label>
        <form:input path="state" type="hidden" id="state" value="${loggedUser.providerDetails.location.state.id}"/>
        <div class="row">
            <div class="col d-flex align-items-center justify-content-center">
                <div class="dropdown">
                    <button class="dropdown-custom dropdown-toggle mt-2" type="button" id="stateDropdown"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <spring:message code="joinForm.state"/>
                        <span class="state" id="stateName">
                            <c:out value="${loggedUser.providerDetails.location.state.name}"/>
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
    <div class="row">
        <div class="col-6 d-flex align-items-center justify-content-center px-3">
            <a href="<c:url value="/user/dashboard"/>" style="width: 100%;">
                <button class="w-100 continueBtn my-1" type="button">
                    <spring:message code="providerUpdate.cancel"/>
                </button>
            </a>
        </div>
        <div class="col-6 d-flex align-items-center justify-content-center px-3">
            <button class="w-100 continueBtn my-1" form="providerInfoFirstForm" type="submit">
                <spring:message code="joinForm.continue"/>
            </button>
        </div>
    </div>
</form:form>