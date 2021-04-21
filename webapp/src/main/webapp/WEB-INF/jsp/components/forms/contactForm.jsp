<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url value="/jobs/${job.id}/contact" var="postPath"/>
<form:form modelAttribute="contactForm" id="contactForm" action="${postPath}" class="contactForm" method="POST">

    <c:if test="${contactInfoCollection.size() > 0}">
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
                        data-info-id="${contact.contactInfoId}"
                        data-street="${contact.street}" data-address-number="${contact.addressNumber}"
                        data-floor="${contact.floor}" data-department-number="${contact.departmentNumber}">
                        <c:out value="${contact.state} ${contact.city} ${contact.street} ${contact.addressNumber} ${contact.floor} ${contact.departmentNumber}"/></a>
                </c:forEach>
        </div>
    </div>
    </c:if>


    <input type="hidden" value="${job.provider.email}" name="providerEmail">
    <div class="row">
        <div class="col">
            <div class="form-group">
                <form:label path="state"><spring:message code="contactForm.state"/></form:label>
                <form:input type="text" path="state" id="state" class="form-control"
                            cssErrorClass="form-control is-invalid"/>
                <form:errors path="state" cssClass="formError" element="p"/>

            </div>
        </div>
        <div class="col">
            <div class="form-group">
                <form:label path="city"><spring:message code="contactForm.city"/></form:label>
                <form:input type="text" path="city" id="city" class="form-control"
                            cssErrorClass="form-control is-invalid"/>
                <form:errors path="city" cssClass="formError" element="p"/>
            </div>
        </div>
    </div>
    <div class="form-group">
        <form:label path="street"><spring:message code="contactForm.street"/></form:label>
        <form:input type="text" path="street" id="street" class="form-control" cssErrorClass="form-control is-invalid"/>
        <form:errors path="street" cssClass="formError" element="p"/>
    </div>
    <div class="row">
        <div class="col-4">
            <div class="form-group">
                <form:label path="addressNumber"><spring:message code="contactForm.addressNumber"/></form:label>
                <form:input type="number" path="addressNumber" id="addressNumber" class="form-control"
                            cssErrorClass="form-control is-invalid"/>
                <form:errors path="addressNumber" cssClass="formError" element="p"/>

            </div>
        </div>
        <div class="col-4">
            <div class="form-group">
                <form:label path="floor"><spring:message code="contactForm.floor"/></form:label>
                <form:input type="number" path="floor" id="floor" class="form-control"
                            cssErrorClass="form-control is-invalid"/>
                <form:errors path="floor" cssClass="formError" element="p"/>
            </div>
        </div>

        <div class="col-4">
            <div class="form-group">
                <form:label path="departmentNumber"><spring:message code="contactForm.departmentNumber"/></form:label>
                <form:input type="text" path="departmentNumber" id="departmentNumber" class="form-control"
                            cssErrorClass="form-control is-invalid"/>
                <form:errors path="departmentNumber" cssClass="formError" element="p"/>
            </div>
        </div>
    </div>
    <div class="form-group">
        <form:label path="message"><spring:message code="contactForm.description"/></form:label>
        <form:textarea class="form-control" path="message" id="message" style="resize: none; height: 150px;"
                       cssErrorClass="form-control is-invalid"/>
        <form:errors path="message" cssClass="formError" element="p"/>
    </div>
    <form:input type="hidden" path="contactInfoId" value="-1"/>
    <div class="row align-items-center justify-content-center">
        <div class="col-12 px-0 d-flex align-items-center justify-content-center">
            <button type="button" id="contactFormButton" class="contactBtn w-50"><spring:message code="contactForm.submit"/></button>
        </div>
    </div>


</form:form>
