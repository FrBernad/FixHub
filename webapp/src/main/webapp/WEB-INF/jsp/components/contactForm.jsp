<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form action="" class="contactForm">
    <div class="form-group">
        <label for="firstName">Nombre</label>
        <input type="text" name="firstName" id="firstName" class="form-control">
    </div>
    <div class="form-group">
        <label for="surName">Apellido</label>
        <input type="text" name="surName" id="surName" class="form-control">
    </div>
    <div class="form-group">
        <label for="phoneNumber">Teléfono de contacto</label>
        <input type="number" name="phoneNumber" id="phoneNumber" class="form-control">
    </div>
    <div class="row">
        <div class="col">
            <div class="form-group">
                <label for="provincia">Provincia</label>
                <input type="text" name="provincia" id="provincia" class="form-control">
            </div>
        </div>
        <div class="col">
            <div class="form-group">
                <label for="localidad">Localidad</label>
                <input type="text" name="localidad" id="localidad" class="form-control">
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <div class="form-group">
                <label for="calle">Calle</label>
                <input type="text" name="calle" id="calle" class="form-control">
            </div>
        </div>
        <div class="col">
            <div class="form-group">
                <label for="numero">Número</label>
                <input type="number" name="numero" id="numero" class="form-control">
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="row">
            <div class="col-6">
                <label for="piso">Piso / Departamento</label>
                <input type="number" name="piso" id="piso" class="form-control">
            </div>
        </div>
    </div>
    <div class="form-group">
        <label for="mensaje">Mensaje</label>
        <textarea class="form-control" id="mensaje"></textarea>
    </div>
    <div class="form-group">
        <button type="submit" class="btn btn-primary">Enviar</button>
    </div>
</form>
