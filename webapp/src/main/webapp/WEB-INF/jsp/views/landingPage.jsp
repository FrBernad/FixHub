<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Fixhub</title>
    <%@ include file="../components/headers.jsp" %>
    <link href='<c:url value="/resources/css/landingPage.css"/>' rel="stylesheet">
</head>

<body>
<%@ include file="../components/navbar.jsp" %>
<div class="container-fluid h-75">
    <div class="row align-items-center justify-content-center h-100 bgImg">
        <div class="col-12 w-100">
            <div class="container-lg w-100">
                <div class="row w-100">
                    <div class="col-10 col-md-8 w-50 d-flex justify-content-start align-items-center">
                        <h2 class="text-left photoText">Your home for<br>everything home</h2>
                    </div>
                    <div class="col-8 col-md-7 w-50 d-flex justify-content-center align-items-center">
                        <div class="input-group">
                            <input placeholder="¿Qué servicio necesitas hoy?"
                                   class="inputRadius form-control p-4">
                            <div class="input-group-prepend">
                                <button class="btn btn-lg inputBtn">Buscar</button>
                            </div>
                        </div>
                    </div>
                    <div class="col-7 mt-3 w-50 d-flex justify-content-start align-items-center">
                        <button class="btn-sm mr-2 suggestionBtn">Plomeria</button>
                        <button class="btn-sm mx-2 suggestionBtn">Electricista</button>
                        <button class="btn-sm ml-2 suggestionBtn">Mecanico</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="container-fluid" style="background-color: rgb(255,255,255)">
    <div class="container-lg d-flex align-items-center py-5">
        <div class="row align-items-start">
            <div class="col-12">
                <h1 class="py-3 stepSectionTitle">Como funciona</h1>
            </div>
            <div class="col-4 d-flex justify-content-center align-items-start">
                <div class="row">
                    <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                        <i class="fas iconColor fa-search fa-2x"></i>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                        <p class="text-start stepHeader">1. Buscá lo que necesites</p>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-center align-items-center">
                        <p class="text-start stepBody">Desde plomería hasta renovaciones totales, nosotros nos encargamos de conectarte con los mejores profesionales.</p>
                    </div>
                </div>
            </div>
            <div class="col-4 d-flex justify-content-center align-items-start">
                <div class="row">
                    <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                        <i class="fas iconColor fa-star fa-2x"></i>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                        <p class="text-start stepHeader">2. Explorá las diferentes opciones</p>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-center stepBody align-items-center">
                        <p class="text-start">La calidad del trabajo es nuestra garantía, para ello, contamos con un sistema de calificaciones que valora el buen servicio.</p>
                    </div>
                </div>
            </div>
            <div class="col-4 d-flex justify-content-center align-items-start">
                <div class="row">
                    <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                        <i class="fas iconColor fa-handshake fa-2x"></i>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                        <p class="text-start stepHeader">3. Contactate con quien más te guste</p>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-center stepBody align-items-center">
                        <p class="text-start">Completá el formulario e intercambia los detalles con nuestros expertos. Fixhub te asegura que quedes satisfecho con el resultado.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="container-fluid py-5" style="background-color: rgb(245,245,242)">
    <div class="container-lg d-flex align-items-center w-100">
        <div class="row align-items-center justify-content-center w-100">
            <div class="col-12">
                <h1 class="py-3 stepSectionTitle mb-4">Servicios más populares</h1>
                <div class="row justify-content-between align-items-center m-0">
                    <c:forEach var="job" items="${jobs}">
                        <%@ include file="../components/popularCard.jsp" %>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../components/footer.jsp" %>
</body>

</html>
