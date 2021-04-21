<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value="/user/account/updateInfo" var="postPath"/>
<form:form modelAttribute="userInfoForm" action="${postPath}" id="userInfoForm" method="POST" enctype="multipart/form-data">

    <div class="form-group">
        <form:label path="name"><spring:message code="userInfoForm.userNameLabel"/> <span class="required-field">*</span></form:label>
        <form:input type="text" path="name" value="${loggedUser.name}" id="name" class="form-control" cssErrorClass="form-control is-invalid"/>
        <form:errors path="name" cssClass="formError" element="p"/>
    </div>
    <div class="form-group">
        <form:label path="surname"><spring:message code="userInfoForm.userSurnameLabel"/> <span class="required-field">*</span></form:label>
        <form:input type="text" path="surname" value="${loggedUser.surname}" cssErrorClass="form-control is-invalid" id="surname" class="form-control"/>
        <form:errors path="surname" cssClass="formError" element="p"/>
    </div>
    <div class="form-group">
        <form:label path="phoneNumber"><spring:message code="userInfoForm.userPhoneLabel"/> <span class="required-field">*</span></form:label>
        <form:input type="text" path="phoneNumber" value="${loggedUser.phoneNumber}" cssErrorClass="form-control is-invalid" id="phoneNumber" class="form-control"/>
        <form:errors path="phoneNumber" cssClass="formError" element="p"/>
    </div>
    <div class="row">
        <div class="col">
            <div class="form-group">
                <form:label path="state"><spring:message code="userInfoForm.userStateLabel"/> <span class="required-field">*</span></form:label>
                <form:input type="text" path="state" value="${loggedUser.state}" cssErrorClass="form-control is-invalid" id="state" class="form-control"/>
                <form:errors path="state" cssClass="formError" element="p"/>
            </div>
        </div>
        <div class="col">
            <div class="form-group">
                <form:label path="city"><spring:message code="userInfoForm.userCityLabel"/> <span class="required-field">*</span></form:label>
                <form:input type="text" path="city" value="${loggedUser.city}" cssErrorClass="form-control is-invalid" id="city" class="form-control"/>
                <form:errors path="city" cssClass="formError" element="p"/>
            </div>
        </div>
    </div>
    <div class="col-12 px-0 d-flex align-items-center justify-content-center">
        <button type="submit" id="editFormButton" form="userInfoForm" class="w-100 continueBtn my-2 ">
            <spring:message code="userInfoForm.submit"/>
        </button>
    </div>


</form:form>
