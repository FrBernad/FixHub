<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/join/register" var="postPath"/>
<form:form modelAttribute="registerForm" action="${postPath}" id="registerForm" class="serviceForm" method="POST">
    <div class="form-group">
        <form:label class="label" path="name">Nombre</form:label>
        <form:input type="text" path="name" id="name" class="form-control"/>
        <form:errors path="name" cssClass="formError" element="p"/>
    </div>
    <div class="form-group">
        <form:label class="label" path="surname">Apellido</form:label>
        <form:input type="text" path="surname" id="surname" class="form-control"/>
        <form:errors path="surname" cssClass="formError" element="p"/>

    </div>
    <div class="form-group">
        <form:label class="label" path="email">E-mail</form:label>
        <form:input type="text" path="email" id="email" class="form-control"/>
        <form:errors path="email" cssClass="formError" element="p"/>

    </div>
    <div class="form-group">
        <form:label class="label" path="phoneNumber">Teléfono de contacto</form:label>
        <form:input type="number" path="phoneNumber" id="phoneNumber" class="form-control"/>
        <form:errors path="phoneNumber" cssClass="formError" element="p"/>
    </div>
    <div class="row">
        <div class="col">
            <div class="form-group">
                <form:label class="label" path="state">Provincia</form:label>
                <form:input type="text" path="state" id="state" class="form-control"/>
                <form:errors path="phoneNumber" cssClass="formError" element="p"/>

            </div>
        </div>
        <div class="col">
            <div class="form-group">
                <form:label class="label" path="city">Localidad</form:label>
                <form:input type="text" path="city" id="city" class="form-control"/>
                <form:errors path="city" cssClass="formError" element="p"/>

            </div>
        </div>
    </div>
    <div class="col-12 d-flex align-items-center justify-content-center">
        <button type="submit" form="registerForm" class="w-100 continueBtn my-2 ">Registar</button>
    </div>
</form:form>