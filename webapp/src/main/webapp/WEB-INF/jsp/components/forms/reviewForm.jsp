<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="modal fade" id="newReview" data-show="true" data-error="${error}" tabindex="-1" aria-labelledby="newReviewLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="container-lg roundedBorder p-4" style="background-color: #FAFAFA; border-radius: 8px">
                <div class="row">
                    <div class="col-12">
                        <c:url value="/jobs/${job.id}" var="postPath"/>
                        <form:form modelAttribute="reviewForm" action="${postPath}" id="reviewForm"
                                   class="reviewForm px-0" method="POST">
                            <div class="form-group">
                                <form:label path="description"><spring:message code="reviewForm.descriptionTitle"/> <span class="required-field">*</span></form:label>
                                <form:textarea type="text" path="description" id="reviewFormTextArea"
                                               class="form-control"  cssErrorClass="form-control is-invalid" cssStyle="resize: none;"/>
                                <form:errors path="description" cssClass="formError" element="p"/>
                            </div>
                            <div class="form-group">
                                <form:label path="rating"><spring:message code="reviewForm.qualificationTitle"/> <span class="required-field">*</span></form:label>
                                <form:select path="rating" class="form-control"  cssErrorClass="form-control is-invalid">
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                </form:select>
                                <form:errors path="rating" cssClass="formError" element="p"/>
                            </div>
                        </form:form>
                    </div>
                    <div class="col-12 d-flex justify-content-start align-items-center">
                        <button id="reviewFormButton" class="rateBtn mr-4">
                            <spring:message code="reviewForm.submitButtonText"/>
                        </button>
                        <button type="button" id="reviewFormCloseButton" class="closeBtn" data-dismiss="modal">
                            <spring:message code="reviewForm.cancelButtonText"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

