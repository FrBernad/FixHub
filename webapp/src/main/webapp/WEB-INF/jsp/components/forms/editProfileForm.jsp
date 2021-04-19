<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/editProfile" var="postPath"/>

<form:form modelAttribute="editProfileForm" action="${postPath}" id="editProfileForm" method="POST">
    <div class="form-group">
        <spring:message code="registerForm.namePlaceholder" var="namePlaceholder"/>
        <form:label class="label" path="name"><spring:message code="editProfileForm.userNameLabel"/></form:label>
        <form:input type="text" path="name" value="${loggedUser.name}" id="name" class="form-control" cssErrorClass="form-control is-invalid"/>
        <form:errors path="name" cssClass="formError" element="p"/>
    </div>
    <div class="form-group">
        <form:label class="label" path="surname"><spring:message code="editProfileForm.userSurnameLabel"/></form:label>
        <form:input type="text" path="surname" value="${loggedUser.surname}" cssErrorClass="form-control is-invalid" id="surname" class="form-control"/>
        <form:errors path="surname" cssClass="formError" element="p"/>
    </div>
    <div class="form-group">
        <form:label class="label" path="phoneNumber"><spring:message code="editProfileForm.userPhoneLabel"/></form:label>
        <form:input type="text" path="phoneNumber" value="${loggedUser.phoneNumber}" cssErrorClass="form-control is-invalid" id="phoneNumber" class="form-control"/>
        <form:errors path="phoneNumber" cssClass="formError" element="p"/>
    </div>
    <div class="row">
        <div class="col">
            <div class="form-group">
                <form:label class="label" path="state"><spring:message code="editProfileForm.userStateLabel"/></form:label>
                <form:input type="text" path="state" value="${loggedUser.state}" cssErrorClass="form-control is-invalid" id="state" class="form-control"/>
                <form:errors path="state" cssClass="formError" element="p"/>
            </div>
        </div>
        <div class="col">
            <div class="form-group">
                <form:label class="label" path="city"><spring:message code="editProfileForm.userCityLabel"/></form:label>
                <form:input type="text" path="city" value="${loggedUser.city}" cssErrorClass="form-control is-invalid" id="city" class="form-control"/>
                <form:errors path="city" cssClass="formError" element="p"/>
            </div>
        </div>
    </div>
    <div class="col-12 d-flex align-items-center justify-content-center">
        <button type="submit" id="editFormButton" form="editProfileForm" class="w-100 continueBtn my-2 ">
            <spring:message code="editProfileForm.submit"/>
        </button>
    </div>
    </div>

</form:form>
