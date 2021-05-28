<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:url value="/register" var="postPath"/>
<form:form modelAttribute="registerForm" action="${postPath}" id="registerForm" class="jobForm" method="POST">
    <div class="form-group">
        <spring:message code="registerForm.namePlaceholder" var="namePlaceholder"/>
        <form:label  path="name"><spring:message code="registerForm.userNameTitle"/> <span class="required-field">*</span></form:label>
        <form:input type="text" path="name" id="name" cssErrorClass="form-control is-invalid" class="form-control"
                    placeholder="${namePlaceholder}"/>
        <form:errors path="name" cssClass="formError" element="p"/>
    </div>
    <div class="form-group">
        <spring:message code="registerForm.surnamePlaceholder" var="surnamePlaceholder"/>
        <form:label  path="surname"><spring:message code="registerForm.userSurnameTitle"/> <span class="required-field">*</span></form:label>
        <form:input type="text" path="surname" cssErrorClass="form-control is-invalid" id="surname"
                    class="form-control" placeholder="${surnamePlaceholder}"/>
        <form:errors path="surname" cssClass="formError" element="p"/>

    </div>
    <div class="form-group">
        <spring:message code="registerForm.emailPlaceholder" var="emailPlaceholder"/>
        <form:label  path="email"><spring:message code="registerForm.userEmailTitle"/> <span class="required-field">*</span></form:label>
        <form:input type="email" path="email" id="email" cssErrorClass="form-control is-invalid" class="form-control"
                    placeholder="${emailPlaceholder}"/>
        <form:errors path="email" cssClass="formError" element="p"/>
    </div>
    <div class="form-group">
        <form:label  path="password">
            <spring:message code="registerForm.password"/> <span class="required-field">*</span>
        </form:label>
        <div class="input-group d-flex justify-content-start align-items-center">
            <spring:message code="registerForm.passwordPlaceHolder" var="passwordPlaceholder"/>
            <form:input type="password" path="password" cssClass="form-control"
                        id="password1" name="password"
                        aria-describedby="password input" placeholder="${passwordPlaceholder}" cssErrorClass="form-control is-invalid"/>
            <div class="input-group-append">
                <button id="passwordEye1" type="button" tabindex="-1"
                        class="btn btn-lg form-control inputBtn input-group-text">
                    <i id="eye1" class="far fa-eye-slash"></i>
                </button>
            </div>
        </div>
        <form:errors path="password" cssClass="formError" element="p"/>
    </div>
    <div class="form-group">
        <form:label  path="confirmPassword">
            <spring:message code="registerForm.confirmPassword"/> <span class="required-field">*</span>
        </form:label>
        <div class="input-group d-flex justify-content-start align-items-center">
            <form:input type="password" path="confirmPassword" cssClass="form-control"
                        id="password2" name="confirmPassword"
                        aria-describedby="password input" cssErrorClass="form-control is-invalid"/>
            <div class="input-group-append">
                <button id="passwordEye2" type="button" tabindex="-1"
                        class="btn btn-lg form-control inputBtn input-group-text">
                    <i id="eye2" class="far fa-eye-slash"></i>
                </button>
            </div>
        </div>
        <form:errors path="confirmPassword" cssClass="formError" element="p"/>
        <form:errors path="" cssClass="formError" element="p"/>
    </div>

    <div class="form-group">
        <spring:message code="registerForm.phoneNumberPlaceholder" var="phoneNumberPlaceholder"/>
        <form:label  path="phoneNumber"><spring:message code="registerForm.userPhoneTitle"/> <span class="required-field">*</span></form:label>
        <form:input type="text" path="phoneNumber" cssErrorClass="form-control is-invalid" id="phoneNumber"
                    class="form-control" placeholder="${phoneNumberPlaceholder}"/>
        <form:errors path="phoneNumber" cssClass="formError" element="p"/>
    </div>
    <div class="row">
        <div class="col">
            <div class="form-group">
                <spring:message code="registerForm.userStatePlaceholder" var="userStatePlaceholder"/>
                <form:label  path="state"><spring:message code="registerForm.userStateTitle"/> <span class="required-field">*</span></form:label>
                <form:input type="text" path="state" cssErrorClass="form-control is-invalid" id="state"
                            class="form-control" placeholder="${userStatePlaceholder}"/>
                <form:errors path="state" cssClass="formError" element="p"/>
            </div>
        </div>
        <div class="col">
            <div class="form-group">
                <spring:message code="registerForm.userCityTitlePlaceholder" var="userCityTitlePlaceholder"/>
                <form:label  path="city"><spring:message code="registerForm.userCityTitle"/> <span class="required-field">*</span></form:label>
                <form:input type="text" path="city" cssErrorClass="form-control is-invalid" id="city"
                            class="form-control" placeholder="${userCityTitlePlaceholder}"/>
                <form:errors path="city" cssClass="formError" element="p"/>
            </div>
        </div>
    </div>
    <div class="col-12 px-0 d-flex align-items-center justify-content-center">
        <button type="button" id="registerFormButton" form="registerForm" class="w-100 continueBtn my-2 ">
            <spring:message code="registerForm.buttonText"/>
        </button>
    </div>
</form:form>