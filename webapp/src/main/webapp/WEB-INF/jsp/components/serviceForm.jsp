<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form action="<c:url value="/join"/>" class="serviceForm" method="POST">
    <div class="form-group">
        <label for="name">Nombre</label>
        <input type="text" name="name" id="name" class="form-control">
    </div>
    <div class="form-group">
        <label for="surname">Apellido</label>
        <input type="text" name="surname" id="surname" class="form-control">
    </div>
    <div class="form-group">
        <label for="email">E-mail</label>
        <input type="text" name="email" id="email" class="form-control">
    </div>
    <div class="form-group">
        <label for="phoneNumber">Teléfono de contacto</label>
        <input type="number" name="phoneNumber" id="phoneNumber" class="form-control">
    </div>
    <div class="row">
        <div class="col">
            <div class="form-group">
                <label for="state">Provincia</label>
                <input type="text" name="state" id="state" class="form-control">
            </div>
        </div>
        <div class="col">
            <div class="form-group">
                <label for="city">Localidad</label>
                <input type="text" name="city" id="city" class="form-control">
            </div>
        </div>
    </div>
    <hr>
    <div class="form-group">
        <label for="jobProvided">Servicio que va a proveaer</label>
        <input type="text" name="jobProvided" id="jobProvided" class="form-control">
    </div>
    <div class="row">
        <div class="col-6">
            <div class="form-group">
                <label for="jobType">Tipo de servicio</label>
                <select id="jobType" name="jobType" class="form-control">
                    <option selected value="1">1</option>
                </select>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label for="description">Descripción</label>
        <textarea class="form-control" name="description" id="description"></textarea>
    </div>
    <div class="form-group">
        <button type="submit" class="btn btn-primary">Crear Servicio</button>
    </div>
</form>
