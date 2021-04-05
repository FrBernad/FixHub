<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url value="/jobs/${job.id}/contact" var="postPath"/>
<form:form modelAttribute="contactForm" id="contactForm" action="${postPath}" class="contactForm" method="POST">
    <div class="row">
        <div class="col">
            <div class="form-group">
                <form:label path="name"><spring:message code="contactForm.name"/></form:label>
                <form:input type="text" path="name" id="name" class="form-control"
                            cssErrorClass="form-control is-invalid"/>
                <form:errors path="name" cssClass="formError" element="p"/>
            </div>
        </div>
        <div class="col">
            <div class="form-group">
                <form:label path="surname"><spring:message code="contactForm.surname"/></form:label>
                <form:input type="text" path="surname" id="surname" class="form-control"
                            cssErrorClass="form-control is-invalid"/>
                <form:errors path="surname" cssClass="formError" element="p"/>
            </div>
        </div>
    </div>
    <div class="form-group">
        <form:label path="phoneNumber"><spring:message code="contactForm.phoneNumber"/></form:label>
        <form:input type="number" path="phoneNumber" id="phoneNumber" class="form-control"
                    cssErrorClass="form-control is-invalid"/>
        <form:errors path="phoneNumber" cssClass="formError" element="p"/>
    </div>
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
        <div class="col">
            <div class="form-group">
                <form:label path="addressNumber"><spring:message code="contactForm.addressNumber"/></form:label>
                <form:input type="number" path="addressNumber" id="addressNumber" class="form-control" cssErrorClass="form-control is-invalid"/>
                <form:errors path="addressNumber" cssClass="formError" element="p"/>

            </div>
        </div>
        <div class="col">
            <div class="form-group">
                <form:label path="floor"><spring:message code="contactForm.floor"/></form:label>
                <form:input type="number" path="floor" id="floor" class="form-control"
                            cssErrorClass="form-control is-invalid"/>
                <form:errors path="floor" cssClass="formError" element="p"/>
            </div>
        </div>
    </div>
    <div class="form-group">
        <form:label path="message"><spring:message code="contactForm.description"/></form:label>
        <form:textarea class="form-control" path="message" id="message" style="resize: none; height: 150px;"
                       cssErrorClass="form-control is-invalid"/>
        <form:errors path="message" cssClass="formError" element="p"/>
    </div>
    <div class="row">
        <div class="col d-flex align-items-center justify-content-end">
            <div class="form-group">
                <button type="submit"  form="contactForm" class="btn btn-primary"><spring:message code="contactForm.submit"/></button>
            </div>
        </div>
    </div>

</form:form>
