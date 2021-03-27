<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Descubre</title>
    <%@ include file="../components/headers.jsp" %>
    <link href='<c:url value="/resources/css/discover.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/css/jobCard.css"/>' rel="stylesheet">
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
                        Filtrar por:
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

    <div class="container-lg">
        <div class="row jobsContainer">
            <div class="col-12 p-0 mb-5 resultHeader d-flex align-items-center">
                <p class="mb-0">Mostrando resultados de: <span>"..."</span></p>
            </div>
            <div class="col-12 p-0">
                <div class="container-fluid">
                    <div class="row align-items-top justify-content-between">
                        <c:forEach var="job" items="${jobs}">
                            <%@ include file="../components/jobCard.jsp" %>
                        </c:forEach>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<%@ include file="../components/footer.jsp" %>
</body>
</html>
