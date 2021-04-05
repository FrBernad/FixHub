<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url value="/join/newService" var="postPath"/>
<form:form modelAttribute="serviceForm" action="${postPath}" id="serviceForm" class="serviceForm" method="POST">
    <div class="form-group">
        <form:label class="label" path="jobProvided"><spring:message code="serviceForm.serviceNameTitle"/></form:label>
        <form:input type="text" path="jobProvided" id="jobProvided" class="form-control" cssErrorClass="form-control is-invalid"/>
        <form:errors path="jobProvided" cssClass="formError" element="p"/>
    </div>
    <div class="row">
        <div class="col-6">
            <div class="form-group">
                <form:label class="label" path="jobCategory"><spring:message code="serviceForm.serviceTypeTitle"/></form:label>
                <form:select id="jobCategory" path="jobCategory" class="form-control">
                    <c:forEach var="category" items="${categories}">
                        <option selected value="${category}"><spring:message code="home.categories.${category}"/></option>
                    </c:forEach>
                </form:select>
                <form:errors path="jobCategory" cssClass="formError" element="p"/>
            </div>
        </div>
        <div class="col-6">
            <div class="form-group">
                <form:label class="label" path="price"><spring:message code="serviceForm.servicePriceTitle"/></form:label>
                <form:input type="number" path="price" id="price" class="form-control" cssErrorClass="form-control is-invalid"/>
                <form:errors path="price" cssClass="formError" element="p"/>
            </div>
        </div>
    </div>
    <div class="form-group">
        <form:label class="label" path="description"><spring:message code="serviceForm.serviceDescriptionTitle"/></form:label>
        <form:textarea class="form-control" path="description" id="description" cssErrorClass="form-control is-invalid"/>
        <form:errors path="description" cssClass="formError" element="p"/>

    </div>
    <input type="hidden" value="${user.id}" name="userId">

    <div class="col-12 d-flex align-items-center justify-content-center">
        <button type="submit" form="serviceForm" class="w-100 continueBtn my-2"><spring:message code="serviceForm.buttonText"/></button>
    </div>
</form:form>
