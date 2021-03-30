<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="newReview" tabindex="-1" aria-labelledby="newReviewLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="container-lg p-4" style="background-color: #FAFAFA;">
                <div class="row">
                    <div class="col-12">
                        <form id="reviewForm" action="<c:url value="/jobs/${job.id}"/>" class="reviewForm px-0"
                              method="POST"
                        >
                            <div class="form-group">
                                <label for="description">¿Qué te pareció el trabajo?</label>
                                <textarea type="text" name="description" id="description"
                                          class="form-control"></textarea>
                            </div>
                            <div class="form-group">
                                <label for="rating">Calificación</label>
                                <select id="rating" name="rating" class="form-control">
                                    <option selected>Elije...</option>
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                </select>
                            </div>
                        </form>
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

