<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand navbar-light navbarTop" id="navbarLanding">
    <div class="container-lg">
        <a href="<c:url value='/'/>" class="navbar-brand mr-4">
            <img src='<c:url value="/resources/images/navbrand.png"/>' alt="FixHub brand logo"
                 height="56px" width="160px" type="image/png">
        </a>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item mx-1">
                <a href="<c:url value='/'/>" class="nav-link navbarText">Inicio</a>
            </li>
            <li class="nav-item mx-1">
                <a href="<c:url value='/discover'/>" class="nav-link navbarText">Descubre</a>
            </li>
        </ul>
    </div>
</nav>
