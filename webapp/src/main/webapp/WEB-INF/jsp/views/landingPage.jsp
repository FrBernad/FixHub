<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <link href='<c:url value="/resources/css/landingPage.css"/>' rel="stylesheet">

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
<div class="container-fluid h-75">
    <div class="row align-items-center justify-content-center h-100 bgImg">
        <div class="col-12 w-100">
            <div class="container w-100">
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
<div class="container-fluid h-75" style=" background-color: rgb(255,255,255)">
    <div class="container d-flex align-items-center h-100">
        <div class="row align-items-center">
            <div class="col-12">
                <h1 class="py-3 stepSectionTitle">Como funciona</h1>
            </div>
            <div class="col-4 d-flex justify-content-center align-items-center">
                <div class="row">
                    <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                        <i class="fas iconColor fa-search fa-2x"></i>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-center align-items-center">
                        <p class="text-start stepHeader">1. Lorem ipsum dolor sit amet, consectetur
                            adipisicing elit.
                            Aspernatur aut autem, consectetur
                            delectus dolor explicabo fugiat</p>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-center align-items-center">
                        <p class="text-start stepBody">Lorem ipsum dolor sit amet, consectetur adipisicing elit.
                            Aspernatur
                            aut
                            autem, consectetur
                            delectus dolor explicabo fugiat</p>
                    </div>
                </div>
            </div>
            <div class="col-4 d-flex justify-content-center align-items-center">
                <div class="row">
                    <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                        <i class="fas iconColor fa-star fa-2x"></i>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-center align-items-center">
                        <p class="text-start stepHeader">2. Lorem ipsum dolor sit amet, consectetur
                            adipisicing elit.
                            Aspernatur aut autem, consectetur
                            delectus dolor explicabo fugiat</p>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-center stepBody align-items-center">
                        <p class="text-start">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aspernatur aut
                            autem, consectetur
                            delectus dolor explicabo fugiat</p>
                    </div>
                </div>
            </div>
            <div class="col-4 d-flex justify-content-center align-items-center">
                <div class="row">
                    <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                        <i class="fas iconColor fa-handshake fa-2x"></i>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-center align-items-center">
                        <p class="text-start stepHeader">3. Lorem ipsum dolor sit amet, consectetur
                            adipisicing elit.
                            Aspernatur aut autem, consectetur
                            delectus dolor explicabo fugiat</p>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-center stepBody align-items-center">
                        <p class="text-start">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aspernatur aut
                            autem, consectetur
                            delectus dolor explicabo fugiat</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="container-fluid h-75" style=" background-color: rgb(245,245,242)">
    <div class="container d-flex align-items-center h-100">
        <div class="row align-items-center">
            <div class="col-12">
                <h1 class="py-3 stepSectionTitle">Servicios más populares</h1>
            </div>
            <div class="col-4 d-flex justify-content-center align-items-center">
                <div class="row">
                    <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                        <i class="fas iconColor fa-search fa-2x"></i>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-center align-items-center">
                        <p class="text-start stepHeader">1. Lorem ipsum dolor sit amet, consectetur
                            adipisicing elit.
                            Aspernatur aut autem, consectetur
                            delectus dolor explicabo fugiat</p>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-center align-items-center">
                        <p class="text-start stepBody">Lorem ipsum dolor sit amet, consectetur adipisicing elit.
                            Aspernatur
                            aut
                            autem, consectetur
                            delectus dolor explicabo fugiat</p>
                    </div>
                </div>
            </div>
            <div class="col-4 d-flex justify-content-center align-items-center">
                <div class="row">
                    <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                        <i class="fas iconColor fa-star fa-2x"></i>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-center align-items-center">
                        <p class="text-start stepHeader">2. Lorem ipsum dolor sit amet, consectetur
                            adipisicing elit.
                            Aspernatur aut autem, consectetur
                            delectus dolor explicabo fugiat</p>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-center stepBody align-items-center">
                        <p class="text-start">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aspernatur aut
                            autem, consectetur
                            delectus dolor explicabo fugiat</p>
                    </div>
                </div>
            </div>
            <div class="col-4 d-flex justify-content-center align-items-center">
                <div class="row">
                    <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                        <i class="fas iconColor fa-handshake fa-2x"></i>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-center align-items-center">
                        <p class="text-start stepHeader">3. Lorem ipsum dolor sit amet, consectetur
                            adipisicing elit.
                            Aspernatur aut autem, consectetur
                            delectus dolor explicabo fugiat</p>
                    </div>
                    <div class="col-12 my-3 d-flex justify-content-center stepBody align-items-center">
                        <p class="text-start">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aspernatur aut
                            autem, consectetur
                            delectus dolor explicabo fugiat</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../components/footer.jsp" %>
</body>

</html>
