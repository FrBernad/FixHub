<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Descubre</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <link href='<c:url value="/resources/css/discover.css"/>' rel="stylesheet">

    <script src="https://kit.fontawesome.com/a4ef34ae89.js" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
            crossorigin="anonymous"></script>
</head>
<body>
<%@ include file="../components/navbar.jsp" %>
<div class="outerContainer h-100">
    <div class="row align-items-center justify-content-around">
        <div class="col-6 d-flex align-items-center">
            <div class="input-group">
                <input placeholder="Buscar plomería, jardinería, y más..."
                       class="inputRadius form-control p-4">
                <div class="input-group-prepend">
                    <button class="btn btn-lg inputBtn">Buscar</button>
                </div>
            </div>
        </div>
        <div class="col-6 d-flex align-items-center justify-content-center">
            <div class="dropdown">
                <button class="btn btn-secondary btn-sm dropdown-toggle" type="button" id="dropdownMenuButton"
                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Ordenar por:
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <a class="dropdown-item" href="#">Action</a>
                    <a class="dropdown-item" href="#">Another action</a>
                    <a class="dropdown-item" href="#">Something else here</a>
                </div>
            </div>
        </div>
    </div>

    <div class="container w-75 h-75 align-self-center servicesContainer">
        <div class="row px-3">
            <div class="col-12 py-3">
                <h4>Mostrando resultados de: "..."</h4>
            </div>
            <div class="col-12 d-flex align-self-center">

                <%@ include file="../components/serviceCard.jsp" %>

            </div>

        </div>
    </div>

</div>

<%@ include file="../components/footer.jsp" %>
</body>
</html>
