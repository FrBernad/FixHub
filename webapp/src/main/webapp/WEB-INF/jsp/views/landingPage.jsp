<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Fixhub</title>
    <%@ include file="../components/headers.jsp" %>
    <link href='<c:url value="/resources/css/landingPage.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/css/jobCard.css"/>' rel="stylesheet">
</head>

<body>
<%@ include file="../components/navbar.jsp" %>
<div class="container-fluid h-75">
    <div class="row align-items-center justify-content-center h-100 bgImg">
        <div class="col-12 w-100">
            <div class="container-lg w-100">
                <div class="row w-100">
                    <div class="col-10 col-md-8 w-50 d-flex justify-content-start align-items-center">
                        <h2 class="text-left photoText">Todos los servicios<br>en un solo lugar</h2>
                    </div>
                    <div class="col-8 col-md-7 w-50 d-flex justify-content-center align-items-center">
                        <form action="<c:url value="/discover/search"/>" method="GET" class="mb-0" style="width: 100%">
                        <div class="input-group">
                            <input placeholder="¿Qué servicio necesitas hoy?"
                                   class="inputRadius form-control p-4" name="searchPhrase">
                            <div class="input-group-prepend">
                                <button class="btn btn-lg inputBtn" type="submit">Buscar</button>
                            </div>
                        </div>
                        </form>
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
<div class="container-fluid py-5" style="background-color: rgb(255,255,255)">
    <div class="container-lg d-flex align-items-center py-5">
        <div class="row align-items-start">
            <div class="col-12">
                <h1 class="py-3 stepSectionTitle">Como funciona</h1>
            </div>
            <div class="col-4 d-flex justify-content-center align-items-start">
                <div class="row">
                    <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                        <i class="fas iconsColor fa-search fa-2x"></i>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                        <p class="text-start stepHeader">1. Buscá lo que necesites</p>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-center align-items-center">
                        <p class="text-start stepBody">Desde servicios siemples hasta renovaciones totales, nosotros nos
                            encargamos de conectarte con los mejores profesionales.</p>
                    </div>
                </div>
            </div>
            <div class="col-4 d-flex justify-content-center align-items-start">
                <div class="row">
                    <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                        <i class="fas iconsColor fa-star fa-2x"></i>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                        <p class="text-start stepHeader">2. Explorá las diferentes opciones</p>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-center stepBody align-items-center">
                        <p class="text-start">La calidad del trabajo es nuestra garantía, para ello, contamos con un
                            sistema de calificaciones que valora el buen servicio.</p>
                    </div>
                </div>
            </div>
            <div class="col-4 d-flex justify-content-center align-items-start">
                <div class="row">
                    <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                        <i class="fas iconsColor fa-handshake fa-2x"></i>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                        <p class="text-start stepHeader">3. Contactate con quien más te guste</p>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-center stepBody align-items-center">
                        <p class="text-start">Completá el formulario e intercambia los detalles con nuestros expertos.
                            Fixhub te asegura que quedes satisfecho con el resultado.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="container-fluid py-5" style="background-color: rgb(245,245,242)">
    <div class="container-lg py-5 d-flex align-items-center w-100">
        <div class="row align-items-center justify-content-center w-100">
            <div class="col-12">
                <h1 class="py-3 stepSectionTitle mb-4">Servicios más populares</h1>
                <div class="row ${jobs.size()>=3 ? 'justify-content-between': 'justify-content-start'} align-items-center m-0">
                    <c:choose>
                        <c:when test="${jobs.size()>0}">
                            <c:forEach var="job" items="${jobs}">
                                <%@ include file="../components/popularJobCard.jsp" %>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="col-12 d-flex align-items-center justify-content-center">
                                <div class="container mt-2 d-flex align-items-center justify-content-center"
                                     style="height: 300px; width: auto; background-color: white">
                                    <p class="m-0 text-center p-4" style="font-size: 16px">Todavía no hay trabajos
                                        disponibles,<br>intenta mas
                                        adelante.</p>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="container-fluid py-5" style="background-color:rgb(0,51,89)">
    <div class="container-lg px-0 d-flex align-items-center py-5">
        <div class="row align-items-center justify-content-between w-100">
            <div class="col-12 col-md-6 d-flex justify-content-start align-items-center">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-12 mb-4 d-flex justify-content-start align-items-center">
                            <h2 class="p-0 text-left" style="color: white; font-weight: bold; font-size: 40px">
                                Empieza a
                                trabajar<br>con nosotros. Provee<br>tus servicios hoy.</h2>
                        </div>
                        <div class="col-12 d-flex justify-content-start align-items-center">
                            <a href="<c:url value='/join'/>">
                                <button class="learnMoreBtn">Únete</button>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-12 col-md-6 d-flex justify-content-end align-items-center">
                <img class="jobProvideImg" src='<c:url value="/resources/images/manWorking.jpg"/>'
                     alt="Hombre trabajando">
            </div>
        </div>
    </div>
</div>
<%@ include file="../components/footer.jsp" %>
</body>
</html>
