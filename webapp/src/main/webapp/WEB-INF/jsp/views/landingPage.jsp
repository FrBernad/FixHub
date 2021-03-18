<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <link href="resources/css/landingPage.css" rel="stylesheet">

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
        <div class="col-7 h-25 w-50 d-flex justify-content-center align-items-center">
            <div class="input-group">
                <input placeholder="Busca el especialista que necesites" class="form-control p-2">
                <div class="input-group-prepend">
                    <button class="btn btn-primary p-2">Buscar</button>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="container h-50 d-flex align-items-center">
    <div class="row align-items-center">
        <div class="cols-12">
            <h1 class="p-3" style="font-size: x-large">Como funciona</h1>
        </div>
        <div class="col-4 d-flex justify-content-center align-items-center">
            <div class="row">
                <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                    <i class="fas fa-search fa-3x"></i>
                </div>
                <div class="col-12 my-3 d-flex justify-content-center align-items-center">
                    <p class="text-start font-weight-bold headerP">1. Lorem ipsum dolor sit amet, consectetur
                        adipisicing elit.
                        Aspernatur aut autem, consectetur
                        delectus dolor explicabo fugiat</p>
                </div>
                <div class="col-12 my-3 d-flex justify-content-center align-items-center">
                    <p class="text-start">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aspernatur aut
                        autem, consectetur
                        delectus dolor explicabo fugiat</p>
                </div>
            </div>
        </div>
        <div class="col-4 d-flex justify-content-center align-items-center">
            <div class="row">
                <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                    <i class="fas fa-star fa-3x"></i>
                </div>
                <div class="col-12 my-3 d-flex justify-content-center align-items-center">
                    <p class="text-start font-weight-bold headerP">2. Lorem ipsum dolor sit amet, consectetur
                        adipisicing elit.
                        Aspernatur aut autem, consectetur
                        delectus dolor explicabo fugiat</p>
                </div>
                <div class="col-12 my-3 d-flex justify-content-center align-items-center">
                    <p class="text-start">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aspernatur aut
                        autem, consectetur
                        delectus dolor explicabo fugiat</p>
                </div>
            </div>
        </div>
        <div class="col-4 d-flex justify-content-center align-items-center">
            <div class="row">
                <div class="col-12 my-3 d-flex justify-content-start align-items-center">
                    <i class="fas fa-handshake fa-3x"></i>
                </div>
                <div class="col-12 my-3 d-flex justify-content-center align-items-center">
                    <p class="text-start font-weight-bold headerP">3. Lorem ipsum dolor sit amet, consectetur
                        adipisicing elit.
                        Aspernatur aut autem, consectetur
                        delectus dolor explicabo fugiat</p>
                </div>
                <div class="col-12 my-3 d-flex justify-content-center align-items-center">
                    <p class="text-start">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aspernatur aut
                        autem, consectetur
                        delectus dolor explicabo fugiat</p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>
