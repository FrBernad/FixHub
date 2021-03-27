<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form  action="<c:url value="/discover"/>" class="serviceForm" method="POST">
    <div class="form-group">
        <label for="name">Nombre</label>
        <input type="text" name="name" id="name" class="form-control">
    </div>
    <div class="form-group">
        <label for="surname">Apellido</label>
        <input type="text" name="surname" id="surname" class="form-control">
    </div>
    <div class="form-group">
        <label for="mail">Mail</label>
        <input type="text" name="mail" id="mail" class="form-control">
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
    <div class="row">
        <div class="col">
            <label for="serviceProvided">Servicio que va a proveer</label>
            <input type="text" name="serviceProvided" id="serviceProvided" class="form-control">
        </div>


    </div>

    <div class="form-group">
        <button type="submit" class="btn btn-primary">Enviar</button>
    </div>
</form>
