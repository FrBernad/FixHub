<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container-fluid px-0 d-none" id="navFix" style="height: 82px"></div>
<div class="container-fluid p-0">
    <nav class="navbar navbar-expand navbar-light navbarTop" data-user="${loggedUser}" id="navbar">
        <div class="container-lg">
            <a href="<c:url value='/'/>" class="navbar-brand mr-4">
                <img src='<c:url value="/resources/images/navbrand.png"/>' alt="FixHub brand logo"
                     height="56px" width="160px" type="image/png">
            </a>
            <ul class="navbar-nav ml-auto">
                <li class="nav-item mx-1">
                    <a href="<c:url value='/'/>" class="nav-link navbarText">
                        <spring:message code="navBar.home"/>
                    </a>
                </li>
                <li class="nav-item mx-1">
                    <a href="<c:url value='/discover'/>" class="nav-link navbarText">
                        <spring:message code="navBar.discover"/>
                    </a>
                </li>
                <li class="nav-item mx-1">
                    <c:choose>
                        <c:when test="${loggedUser!=null}">
                            <form id="logoutForm" class="mb-0" action="<c:url value='/logout'/>" method="post">
                                <button type="submit" id="logoutInput" class="nav-link navbarText">
                                    Logout
                                </button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <a href="<c:url value='/login'/>" class="nav-link navbarText">
                                Login
                            </a>
                        </c:otherwise>
                    </c:choose>
                </li>
            </ul>
        </div>
    </nav>
</div>
