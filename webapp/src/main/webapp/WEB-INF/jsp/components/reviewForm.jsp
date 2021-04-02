<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="modal fade" id="newReview" tabindex="-1" aria-labelledby="newReviewLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="container-lg p-4" style="background-color: #FAFAFA;">
                <div class="row">
                    <div class="col-12">
                        <c:url value="/jobs/${job.id}" var="postPath"/>
                        <form:form modelAttribute="reviewForm" action="${postPath}" id="reviewForm"
                                   class="reviewForm px-0" method="POST">
                            <div class="form-group">
                                <form:label path="description">¿Qué te pareció el trabajo?</form:label>
                                <form:textarea type="text" path="description" id="description"
                                               class="form-control"  cssErrorClass="form-control is-invalid"/>
                                <form:errors path="description" cssClass="formError" element="p"/>
                            </div>
                            <div class="form-group">
                                <form:label path="rating">Calificación</form:label>
                                <form:select id="rating" path="rating" class="form-control"  cssErrorClass="form-control is-invalid">
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
                        <button type="submit" form="reviewForm" class="btn btn-primary mr-4">Calificar</button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

