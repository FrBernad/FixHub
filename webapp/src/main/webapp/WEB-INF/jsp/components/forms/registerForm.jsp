<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:url value="/join/register" var="postPath"/>
<form:form modelAttribute="registerForm" action="${postPath}" id="registerForm" class="jobForm" method="POST">
    <div class="form-group">
        <form:label class="label" path="name"><spring:message code="registerForm.userNameTitle"/></form:label>
        <form:input type="text" path="name" id="name" cssErrorClass="form-control is-invalid" class="form-control"/>
        <form:errors path="name" cssClass="formError" element="p"/>
    </div>
    <div class="form-group">
        <form:label class="label" path="surname"><spring:message code="registerForm.userSurnameTitle"/></form:label>
        <form:input type="text" path="surname" cssErrorClass="form-control is-invalid" id="surname" class="form-control"/>
        <form:errors path="surname" cssClass="formError" element="p"/>

    </div>
    <div class="form-group">
        <form:label class="label" path="email"><spring:message code="registerForm.userEmailTitle"/></form:label>
        <form:input type="text" path="email" id="email" cssErrorClass="form-control is-invalid" class="form-control"/>
        <form:errors path="email" cssClass="formError" element="p"/>

    </div>
    <div class="form-group">
        <form:label class="label" path="phoneNumber"><spring:message code="registerForm.userPhoneTitle"/></form:label>
        <form:input type="number" path="phoneNumber" cssErrorClass="form-control is-invalid" id="phoneNumber" class="form-control"/>
        <form:errors path="phoneNumber" cssClass="formError" element="p"/>
    </div>
    <div class="row">
        <div class="col">
            <div class="form-group">
                <form:label class="label" path="state"><spring:message code="registerForm.userStateTitle"/></form:label>
                <form:input type="text" path="state" cssErrorClass="form-control is-invalid" id="state" class="form-control"/>
                <form:errors path="state" cssClass="formError" element="p"/>

            </div>
        </div>
        <div class="col">
            <div class="form-group">
                <form:label class="label" path="city"><spring:message code="registerForm.userCityTitle"/></form:label>
                <form:input type="text" path="city" cssErrorClass="form-control is-invalid" id="city" class="form-control"/>
                <form:errors path="city" cssClass="formError" element="p"/>

            </div>
        </div>
    </div>
    <div class="col-12 d-flex align-items-center justify-content-center">
        <button type="button" id="registerFormButton" form="registerForm" class="w-100 continueBtn my-2 "><spring:message code="registerForm.buttonText"/></button>
    </div>
</form:form>