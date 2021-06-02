<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:url value="/jobs/${job.id}/contact" var="postPath"/>
<form:form modelAttribute="contactForm" id="contactForm" action="${postPath}" class="contactForm" method="POST">

    <c:if test="${fn:length(contactInfoCollection) gt 0}">
        <div class="dropdown mb-4">
            <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton"
                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <spring:message code="contactForm.Dropdown.Information"/>
            </button>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                <a class="dropdown-item" id="newUserContact"><spring:message code="contactForm.newContactInfo"/> </a>
                <c:forEach var="contact" items="${contactInfoCollection}">
                    <a class="dropdown-item userContact"
                       data-state="${contact.state}" data-city="${contact.city}"
                       data-info-id="${contact.id}"
                       data-street="${contact.street}" data-address-number="${contact.addressNumber}"
                       data-floor="${contact.floor}" data-department-number="${contact.departmentNumber}">
                        <c:out value="${contact.state} ${contact.city} ${contact.street} ${contact.addressNumber} ${contact.floor} ${contact.departmentNumber}"/></a>
                </c:forEach>
            </div>
        </div>
    </c:if>


    <input type="hidden" value="${job.provider.email}" name="providerEmail">
    <div class="row">
        <div class="col-6">
            <div class="form-group">
                <form:label path="state"><spring:message code="contactForm.state"/>
                    <span class="required-field">*</span>
                </form:label>
                <form:input type="text" path="state" id="state" class="form-control"
                            value="${job.provider.providerDetails.location.state.name}"
                            cssErrorClass="form-control is-invalid" readonly="true"/>
                <form:errors path="state" cssClass="formError" element="p"/>
            </div>
        </div>
        <div class="col-6">
            <div class="form-group mb-0">
                <form:label path="city">
                    <spring:message code="contactForm.city"/> <span class="required-field">*</span>
                </form:label>
                <div class="dropdown">
                    <div class="input-group mb-1">
                        <form:input type="text" path="city" id="city" class="form-control"
                                    cssErrorClass="form-control is-invalid"/>
                        <button class="btn btn-secondary dropdown-toggle dropdown-toggle-split"
                                type="button" id="cityDropdownBtn"
                                data-toggle="dropdown"
                                data-reference="parent"
                                style="border-radius: 0 .25rem .25rem 0; height: 38px">
                        </button>
                        <div class="dropdown-menu dropdown-menu-left w-100" data-reference="parent">
                            <c:forEach var="city" items="${job.provider.providerDetails.location.cities}">
                                <button class="dropdown-item cityBtn" type="button"
                                        data-name="${city.name}">
                                        ${city.name}
                                </button>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
            <p id="cityError" style="font-size: 14px" class="invisible mb-0 text-danger">
                <spring:message code="contactForm.cityWarning"/>
            </p>
            <form:errors path="city" cssClass="formError" element="p"/>
        </div>
    </div>
    <div class="form-group">
        <form:label path="street"><spring:message code="contactForm.street"/>
            <span class="required-field">*</span>
        </form:label>
        <form:input type="text" path="street" id="street" class="form-control" cssErrorClass="form-control is-invalid"/>
        <form:errors path="street" cssClass="formError" element="p"/>
    </div>
    <div class="row">
        <div class="col-4">
            <div class="form-group">
                <form:label path="addressNumber"><spring:message code="contactForm.addressNumber"/>
                    <span class="required-field">*</span>
                </form:label>
                <form:input type="number" path="addressNumber" id="addressNumber" class="form-control"
                            cssErrorClass="form-control is-invalid"/>
                <form:errors path="addressNumber" cssClass="formError" element="p"/>

            </div>
        </div>
        <div class="col-4">
            <div class="form-group">
                <form:label path="floor">
                    <spring:message code="contactForm.floor"/>
                </form:label>
                <form:input type="number" path="floor" id="floor" class="form-control"
                            cssErrorClass="form-control is-invalid"/>
                <form:errors path="floor" cssClass="formError" element="p"/>
            </div>
        </div>

        <div class="col-4">
            <div class="form-group">
                <form:label path="departmentNumber">
                    <spring:message code="contactForm.departmentNumber"/>
                </form:label>
                <form:input type="text" path="departmentNumber" id="departmentNumber" class="form-control"
                            cssErrorClass="form-control is-invalid"/>
                <form:errors path="departmentNumber" cssClass="formError" element="p"/>
            </div>
        </div>
    </div>
    <div class="form-group">
        <form:label path="message"><spring:message code="contactForm.description"/>
            <span class="required-field">*</span>
        </form:label>
        <form:textarea class="form-control" path="message" id="message" style="resize: none; height: 150px;"
                       cssErrorClass="form-control is-invalid"/>
        <form:errors path="message" cssClass="formError" element="p"/>
    </div>
    <form:input type="hidden" path="contactInfoId" value="-1"/>
    <div class="row align-items-center justify-content-center">
        <div class="col-12 px-0 d-flex align-items-center justify-content-center">
            <button type="button" id="contactFormButton" class="contactBtn w-50">
                <spring:message code="contactForm.submit"/>
            </button>
        </div>
    </div>


</form:form>
