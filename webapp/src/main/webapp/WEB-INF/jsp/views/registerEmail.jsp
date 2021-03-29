<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Fixhub</title>
    <%@ include file="../components/headers.jsp" %>
    <link href='<c:url value="/resources/css/join.css"/>' rel="stylesheet">
</head>

<body>
<%@ include file="../components/navbar.jsp" %>

<div class="container-fluid d-flex align-items-center justify-content-center"
     style="background-color: rgb(245,245,242)">
    <div class="container-lg w-50 p-5 my-5" style="background-color: white; max-width: 32em">
        <div class="row w-100 m-0 align-items-center justify-content-center">
            <div class="col-12">
                <h1 class="text-center title">Registra tu email</h1>
                <p class="subtitle text-center mb-4"><span class="font-weight-bold">Ingresa tus datos</span> y empieza a
                    <br>brindar tus servicios.</p>
            </div>
            <div class="col-12">
                <div class="container-lg">
                    <div class="row">
                        <div class="col-12 d-flex align-items-center justify-content-center">
                            <form action="<c:url value="/join/register"/>" id="registerForm" class="serviceForm" method="POST">
                                <div class="form-group">
                                    <label class="label" for="name">Nombre</label>
                                    <input type="text" name="name" id="name" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label class="label" for="surname">Apellido</label>
                                    <input type="text" name="surname" id="surname" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label class="label" for="email">E-mail</label>
                                    <input type="text" name="email" id="email" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label class="label" for="phoneNumber">Teléfono de contacto</label>
                                    <input type="number" name="phoneNumber" id="phoneNumber"
                                           class="form-control">
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <div class="form-group">
                                            <label class="label" for="state">Provincia</label>
                                            <input type="text" name="state" id="state" class="form-control">
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="form-group">
                                            <label class="label" for="city">Localidad</label>
                                            <input type="text" name="city" id="city" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="col-12 d-flex align-items-center justify-content-center">
                            <button type="submit" form="registerForm" class="w-100 continueBtn my-2 ">Registar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../components/footer.jsp" %>
</body>

</html>