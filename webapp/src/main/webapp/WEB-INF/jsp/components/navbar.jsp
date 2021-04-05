<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<nav class="navbar navbar-expand navbarDefault">
    <div class="container-lg">
        <a href="<c:url value='/'/>" class="navbar-brand mr-4">
            <img src='<c:url value="/resources/images/navbrand.png"/>' alt=<spring:message code="productName"/>
                 height="56px" width="160px" type="image/png">
        </a>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item mx-1">
                <a href="<c:url value='/'/>" class="nav-link navbarText"><spring:message code="navBar.firstItemText"/></a>
            </li>
            <li class="nav-item mx-1">
                <a href="<c:url value='/discover'/>" class="nav-link navbarText"><spring:message code="navBar.secondItemText"/></a>
            </li>
        </ul>
    </div>
</nav>
