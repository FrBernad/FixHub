<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:url value="/user/join/chooseCity" var="postPath"/>
<form:form modelAttribute="chooseCityForm" action="${postPath}" id="chooseCityForm" method="POST">
    <div class="form-group">
        <form:label class="label" path="city"><spring:message code="joinForm.availableCities"/>*</form:label>
        <form:input path="state" type="hidden" id="state" value="${state}"/>
        <form:input path="startTime" type="hidden" id="state" value="${startTime}"/>
        <form:input path="endTime" type="hidden" id="state" value="${endTime}"/>
        <div class="row">
            <div class="col d-flex align-items-center justify-content-center">
                <div class="dropdown">
                    <button class="dropdown-custom dropdown-toggle" type="button" id="cityDropdown"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <spring:message code="cityForm.city"/>
                        <span class="state" id="cityName">
                        </span>
                    </button>
                    <div class="dropdown-menu">
                        <c:forEach var="city" items="${cities}">
                            <div class="input-group">
                                <button class="dropdown-item cityButton" type="button" data-id="${city.id}" data-name="${city.name}">
                                        ${city.name}
                                </button>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <form:errors path="city" cssClass="formError text-center" element="p"/>
    <div class="form-group">
        <div class="row">
            <div class="col">
                <div class="container-fluid" id="citiesHolder">

                </div>
            </div>
        </div>
    </div>
    <div class="col-12 d-flex align-items-center justify-content-center">
        <button class="w-100 continueBtn my-2" type="button" id="submitButton">
            <spring:message code="joinForm.Finish"/>
        </button>
    </div>
</form:form>