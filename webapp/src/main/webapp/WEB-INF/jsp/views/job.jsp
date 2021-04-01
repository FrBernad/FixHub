<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Job Name</title>
    <%@ include file="../components/headers.jsp" %>
    <link href='<c:url value="/resources/css/job.css"/>' rel="stylesheet">

</head>

<body>
<%@ include file="../components/navbar.jsp" %>
<div class="container-fluid py-5" style="background-color: rgb(245,245,242);">
    <div class="container-lg p-4" style="background-color: white">
        <div class="row mt-3">
            <div class="col-12 col-md-6 d-flex justify-content-center align-items-start">
                <img src="data:image/jpeg;base64,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"
                     alt="Imagen no disponoble" class="rounded" style="height: 400px; width:400px; object-fit: cover">
            </div>
            <div class="col-12 col-md-6 d-flex justify-content-start align-items-start">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-7">
                            <div class="container-fluid p-0">
                                <row>
                                    <div class="col-12 pl-0">
                                        <h1 class="jobTitle mt-3">${job.jobProvided}</h1>
                                    </div>
                                    <div class="col-12 pl-0">
                                        <h1 class="contactInfo ">${job.provider.name} ${job.provider.surname}</h1>
                                    </div>
                                    <div class="col-12 pl-0 mt-2">
                                        <c:forEach begin="1" end="${job.averageRating}">
                                            <i class="fas iconsColor fa-star fa-1x mr-2"></i>
                                        </c:forEach>
                                        <c:forEach begin="${job.averageRating}" end="4">
                                            <i class="far iconsColor fa-star fa-1x mr-2"></i>
                                        </c:forEach>
                                    </div>
                                </row>
                            </div>
                        </div>
                        <div class="col-5 d-flex justify-content-start align-items-center">
                            <a href="<c:url value='/jobs/${job.id}/contact'/>">
                                <button class="btn btn-primary">Contactar</button>
                            </a>
                        </div>

                        <hr class="text-left ml-0 my-4" style="width: 80%;">
                        <div class="col-12 mt-2">
                            <div class="container-fluid p-0">
                                <div class="row">
                                    <div class="col-12 d-flex justify-content-start align-items-center">
                                        <h2 class="sectionTitle mb-3">Informacion del proveedor</h2>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-12">
                                        <div class="container-fluid p-0">
                                            <div class="row">
                                                <div class="col-12">
                                                    <p class="text-left contactInfo">
                                                        <span class="font-weight-bold">Nombre:</span> ${job.provider.name} ${job.provider.surname}
                                                    </p>
                                                </div>
                                                <div class="col-12">
                                                    <p class="text-left">
                                                        <span class="font-weight-bold">Email:</span> ${job.provider.email}
                                                    </p>
                                                </div>
                                                <div class="col-12">
                                                    <p class="text-left">
                                                        <span class="font-weight-bold">Teléfono de contacto:</span> ${job.provider.phoneNumber}
                                                    </p>
                                                </div>
                                                <div class="col-12">
                                                    <p class="text-left">
                                                        <span class="font-weight-bold">Provincia:</span> ${job.provider.state}
                                                    </p>
                                                </div>
                                                <div class="col-12">
                                                    <p class="text-left">
                                                        <span class="font-weight-bold">Localidad:</span> ${job.provider.city}
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>

        </div>

        <hr class="text-left ml-0 my-5" style="width: 80%;">

        <div class="container-fluid">
            <div class="row">
                <div class="col-12">
                    <h1 class="sectionTitle">Descripción</h1>
                </div>
                <div class="col-12">
                    <p class="text-left contactInfo">
                        ${job.description}
                    </p>
                </div>
            </div>
        </div>

        <hr class="text-left ml-0 my-5" style="width: 80%;">

        <div class="container-fluid mt-3">
            <div class="row">
                <div class="col-12 d-flex justify-content-start align-items-center">
                    <h2 class="sectionTitle">Opiniones sobre ${job.jobProvided}</h2>
                </div>
                <div class="col-12 d-flex justify-content-start align-items-center">
                    <a href="#" type="button" data-toggle="modal" data-target="#newReview">
                        Danos tu opinion.
                    </a>
                    <%@ include file="../components/reviewForm.jsp" %>
                </div>
            </div>
            <div class="row mt-2">
                <div class="col-12 d-flex align-items-center">
                    <div class="container-fluid">
                        <div class="row">
                            <c:choose>
                                <c:when test="${reviews.size()>0}">
                                    <c:forEach var="review" items="${reviews}">
                                        <%@ include file="../components/reviewCard.jsp" %>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <div class="col-12 d-flex align-items-center justify-content-center">
                                        <div class="container mt-2 d-flex align-items-center justify-content-center">
                                            <p class="m-0 text-center p-4" style="font-size: 16px">
                                                Aún no hay reviews.
                                            </p>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
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
