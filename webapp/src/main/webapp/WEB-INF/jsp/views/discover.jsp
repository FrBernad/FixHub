<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Descubre</title>
    <%@ include file="../components/headers.jsp" %>
    <link href='<c:url value="/resources/css/discover.css"/>' rel="stylesheet">
</head>
<body>
<%@ include file="../components/navbar.jsp" %>
<div class="outerContainer py-4">
    <div class="container-lg">
        <div class="row pb-4 align-items-center justify-content-between">
            <div class="col-8 p-0 d-flex align-items-center justify-content-start">
                <div class="input-group">
                    <input placeholder="Buscar plomería, jardinería, y más..."
                           class="inputRadius form-control p-4">
                    <div class="input-group-prepend">
                        <button class="btn btn-lg inputBtn">Buscar</button>
                    </div>
                </div>
            </div>
            <div class="col-4 d-flex p-0 align-items-center justify-content-end">
                <div class="dropdown mr-4">
                    <button class="btn btn-secondary btn-sm dropdown-toggle" type="button" id="filterDropdown"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Ordenar por:
                    </button>
                    <div class="dropdown-menu" aria-labelledby="filterDropdown">
                        <a class="dropdown-item" href="#">Action</a>
                        <a class="dropdown-item" href="#">Another action</a>
                        <a class="dropdown-item" href="#">Something else here</a>
                    </div>
                </div>
                <div class="dropdown">
                    <button class="btn btn-secondary btn-sm dropdown-toggle" type="button" id="orderDropdown"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Ordenar por:
                    </button>
                    <div class="dropdown-menu" aria-labelledby="orderDropdown">
                        <a class="dropdown-item" href="#">Action</a>
                        <a class="dropdown-item" href="#">Another action</a>
                        <a class="dropdown-item" href="#">Something else here</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="container-lg align-self-center servicesContainer">
        <div class="row px-3">
            <div class="col-12 py-3">
                <h4>Mostrando resultados de: "..."</h4>
            </div>
            <div class="col-12 d-flex align-items-center justify-content-center">
                <%@ include file="../components/jobCard.jsp" %>
            </div>

        </div>
    </div>

</div>

<%@ include file="../components/footer.jsp" %>
</body>
</html>
