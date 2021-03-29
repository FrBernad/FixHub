<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form action="<c:url value="/jobs/${job.id}"/>" class="reviewForm p-3" method="POST" style="width: 500px; margin: auto; background-color: #FAFAFA;">
    <div class="form-group">
        <label for="description">¿Qué le pareció el trabajo?</label>
        <input type="text" name="description" id="description" class="form-control">
    </div>
    <div class="form-group">
        <label for="rating">Calificación</label>
        <select id="rating" name="rating" class="form-control">
            <option selected>Choose...</option>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
        </select>
    </div>
    <div class="form-group">
        <button type="submit" class="btn btn-primary">Calificar</button>
    </div>
</form>
