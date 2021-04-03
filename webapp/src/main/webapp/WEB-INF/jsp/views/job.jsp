<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
    <div class="container-lg p-4" style="background-color: white;">
        <div class="row mt-3">
            <div class="col-12 col-md-6 d-flex justify-content-center align-items-start">
                <div class="container">
                    <div style="position: relative;height: 400px; width:400px;">
                        <img
                                src="<c:url value='/resources/images/${job.category}.jpg'/>"
                                alt="${job.category}" class="rounded"
                                style="object-fit: cover; height: 100%; width: 100%">
                        <span class="badge badge-pill badge-secondary jobCategory">
                            <spring:message code="home.categories.${job.category}"/></span>
                        <div class="jobPrice">
                            <p class="text-left mb-0">A partir de $<c:out value="${job.price}"/></p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-12 col-md-6 d-flex justify-content-start align-items-start">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-7">
                            <div class="container-fluid p-0">
                                <row>
                                    <div class="col-12 pl-0">
                                        <h1 class="jobTitle mt-3"><c:out value="${job.jobProvided}"/></h1>
                                    </div>
                                    <div class="col-12 pl-0">
                                        <h1 class="contactInfo"><c:out value="${job.provider.name}"/> <c:out value="${job.provider.surname}"/></h1>
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
                                                        <span class="font-weight-bold">Nombre:</span> <c:out value="${job.provider.name}"/> <c:out value="${job.provider.surname}"/>
                                                    </p>
                                                </div>
                                                <div class="col-12">
                                                    <p class="text-left">
                                                        <span class="font-weight-bold">Email:</span> <c:out value="${job.provider.email}"/>
                                                    </p>
                                                </div>
                                                <div class="col-12">
                                                    <p class="text-left">
                                                        <span class="font-weight-bold">Teléfono de contacto:</span> <c:out value="${job.provider.phoneNumber}"/>
                                                    </p>
                                                </div>
                                                <div class="col-12">
                                                    <p class="text-left">
                                                        <span class="font-weight-bold">Provincia:</span> <c:out value="${job.provider.state}"/>
                                                    </p>
                                                </div>
                                                <div class="col-12">
                                                    <p class="text-left">
                                                        <span class="font-weight-bold">Localidad:</span> <c:out value="${job.provider.city}"/>
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
                        <c:out value="${job.description}"/>
                    </p>
                </div>
            </div>
        </div>

        <hr class="text-left ml-0 my-5" style="width: 80%;">

        <div class="container-fluid mt-3">
            <div class="row">
                <div class="col-12 d-flex justify-content-start align-items-center">
                    <h2 class="sectionTitle">Opiniones sobre <c:out value="${job.jobProvided}"/></h2>
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
