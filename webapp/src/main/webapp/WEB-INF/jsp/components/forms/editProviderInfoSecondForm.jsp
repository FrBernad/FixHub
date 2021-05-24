<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:url value="/user/account/updateProviderCity" var="postPath"/>
<form:form modelAttribute="providerInfoSecondForm" action="${postPath}" id="providerInfoSecondForm" method="POST">
    <div class="form-group">
        <form:label class="label text-center font-weight-bold" path="city" cssStyle="display: block;"><spring:message code="joinForm.availableCities"/></form:label>
        <form:input path="state" type="hidden" id="state" value="${state}"/>
        <form:input path="startTime" type="hidden" id="startTime" value="${startTime}"/>
        <form:input path="endTime" type="hidden" id="endTime" value="${endTime}"/>
        <div class="row">
            <div class="col d-flex align-items-center justify-content-center">
                <div class="dropdown">
                    <button class="dropdown-custom dropdown-toggle" type="button" id="cityDropdown"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <spring:message code="cityForm.city"/>
                        <span class="state" id="cityName">
                        </span>
                    </button>
                    <div class="dropdown-menu dropdown-size">
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
                <c:if test="${!newState}">
                <c:forEach var="city" items="${loggedUser.providerDetails.location.cities}">
                        <span class="d-none previousCities cityButton" data-id="${city.id}" data-name="${city.name}"></span>
                </c:forEach>
                </c:if>
                <div class="container-fluid" id="citiesHolder" style="width: 418px;">

                </div>
            </div>
        </div>
    </div>
    <div class="col-12 px-0 d-flex align-items-center justify-content-center">
        <button class="w-100 continueBtn my-2" type="button" id="submitButton">
            <spring:message code="joinForm.Finish"/>
        </button>
    </div>
</form:form>