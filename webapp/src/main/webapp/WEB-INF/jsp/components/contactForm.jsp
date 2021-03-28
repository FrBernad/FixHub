<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form  action="<c:url value="/discover"/>" class="contactForm" method="POST" style="width: 500px; margin: auto;">
    <div class="form-group">
        <label for="name">Nombre</label>
        <input type="text" name="name" id="name" class="form-control">
    </div>
    <div class="form-group">
        <label for="surname">Apellido</label>
        <input type="text" name="surname" id="surname" class="form-control">
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
    <div class="row">
        <div class="col">
            <div class="form-group">
                <label for="street">Calle</label>
                <input type="text" name="street" id="street" class="form-control">
            </div>
        </div>
        <div class="col">
            <div class="form-group">
                <label for="addressNumber">Número</label>
                <input type="number" name="addressNumber" id="addressNumber" class="form-control">
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="row">
            <div class="col-6">
                <label for="floor">Piso / Departamento</label>
                <input type="number" name="floor" id="floor" class="form-control">
            </div>
        </div>
    </div>
    <div class="form-group">
        <label for="message">Mensaje</label>
        <textarea class="form-control" name="message" id="message" style="resize: none; height: 150px;"></textarea>
    </div>
    <div class="form-group">
        <button type="submit" class="btn btn-primary">Enviar</button>
    </div>
</form>
