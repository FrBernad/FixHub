<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form action="<c:url value="/join"/>" class="serviceForm" method="POST">
    <div class="form-group">
        <label class="label" for="jobProvided">Servicio que va a proveer</label>
        <input type="text" name="jobProvided" id="jobProvided" class="form-control">
    </div>
    <div class="row">
        <div class="col-6">
            <div class="form-group">
                <label class="label" for="jobType">Tipo de servicio</label>
                <select id="jobType" name="jobType" class="form-control">
                    <c:forEach var="category" items="${categories}">
                        <option selected value="${category.id}">${category.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="label" for="description">Descripción</label>
        <textarea class="form-control" name="description" id="description"></textarea>
    </div>
</form>
