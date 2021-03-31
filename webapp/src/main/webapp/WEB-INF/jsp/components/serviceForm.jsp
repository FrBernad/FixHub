<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/join/newService" var="postPath"/>
<form:form modelAttribute="serviceForm" action="${postPath}" id="serviceForm" class="serviceForm" method="POST">
    <div class="form-group">
        <form:label class="label" path="jobProvided">Servicio que va a proveer</form:label>
        <form:input type="text" path="jobProvided" id="jobProvided" class="form-control"/>
        <form:errors path="jobProvided" cssClass="formError" element="p"/>
    </div>
    <div class="row">
        <div class="col-6">
            <div class="form-group">
                <form:label class="label" path="jobCategoryId">Tipo de servicio</form:label>
                <form:select id="jobCategoryId" path="jobCategoryId" class="form-control">
                    <c:forEach var="category" items="${categories}">
                        <option selected value="${category.id}">${category.name}</option>
                    </c:forEach>
                </form:select>
                <form:errors path="jobCategoryId" cssClass="formError" element="p"/>
            </div>
        </div>
    </div>
    <div class="form-group">
        <form:label class="label" path="description">Descripción</form:label>
        <form:textarea class="form-control" path="description" id="description"/>
        <form:errors path="description" cssClass="formError" element="p"/>

        <input type="hidden" value="${user.id}" name="userId">
    </div>

    <div class="col-12 d-flex align-items-center justify-content-center">
        <button type="submit" form="serviceForm" class="w-100 continueBtn my-2">Crear</button>
    </div>
</form:form>
