<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container-fluid px-0 d-none" id="navFix" style="height: 82px"></div>
<div class="container-fluid p-0">
    <nav class="navbar navbar-expand navbar-light navbarTop" id="navbar">
        <div class="container-lg">
            <a href="<c:url value='/'/>" class="navbar-brand mr-4">
                <img src='<c:url value="/resources/images/navbrand.png"/>' alt="FixHub brand logo"
                     height="56px" width="160px" type="image/png">
            </a>
            <ul class="navbar-nav mr-auto">
                <li class="nav-item mx-1 navOption d-flex justify-content-center align-items-center">
                    <a href="<c:url value='/'/>" class="nav-link navbarText">
                        <spring:message code="navBar.home"/>
                    </a>
                </li>
                <li class="nav-item navOption mx-1 d-flex justify-content-center align-items-center">
                    <a href="<c:url value='/discover'/>" class="nav-link navbarText">
                        <spring:message code="navBar.discover"/>
                    </a>
                </li>
                <li class="nav-item mx-1 navOption d-flex justify-content-center align-items-center">
                    <a href="<c:url value='/dashboard'/>" class="nav-link navbarText">
                        <spring:message code="navBar.dashboard"/>
                    </a>
                </li>
            </ul>
            <ul class="navbar-nav ml-auto">
                <c:choose>
                    <c:when test="${loggedUser!=null}">
                        <li class="nav-item mx-1 d-flex justify-content-center align-items-center">
                            <div class="userContainer pr-0 nav-link">
                                <div id="moreOptions" class="dropdown" data-toggle="dropdown">
                                    <img class="avatar"
                                         src="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fcdn.iconscout.com%2Ficon%2Ffree%2Fpng-256%2Favatar-372-456324.png&f=1&nofb=1"/>
                                    <i class="fas ml-2 fa-chevron-down navbarText" id="navBarArrow"></i>
                                </div>
                                <div class="dropdown-menu dropdown-menu-right">
                                    <p class="mb-0 pl-3 dropdownItem ">
                                        <spring:message code="navBar.signInAs"/>
                                        <span class="username">
                                            <c:out value="${loggedUser.name}"/>
                                        </span>
                                    </p>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item pl-3" href="#">
                                        <spring:message code="navBar.profile"/>
                                    </a>
                                    <div class="dropdown-divider"></div>
                                    <form id="logoutForm" class="mb-0 dropdown-item pl-3"
                                          action="<c:url value='/logout'/>"
                                          method="post">
                                        <button type="submit" id="logoutInput" class="px-0">
                                            <spring:message code="navBar.logout"/>
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item mx-1 d-flex justify-content-center align-items-center navOption">
                            <a href="<c:url value='/login'/>" class="nav-link navbarText">
                                <spring:message code="navBar.login"/>
                            </a>
                        </li>
                    </c:otherwise>
                </c:choose>
                </li>
            </ul>
        </div>
    </nav>
</div>
