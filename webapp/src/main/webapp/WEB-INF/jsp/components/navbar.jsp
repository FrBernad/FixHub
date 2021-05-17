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
                <c:if test="${loggedUser != null && loggedUser.isProvider}">
                    <li class="nav-item mx-1 navOption d-flex justify-content-center align-items-center">
                        <a href="<c:url value='/user/dashboard'/>" class="nav-link navbarText">
                            <spring:message code="navBar.dashboard"/>
                        </a>
                    </li>
                    <li class="nav-item mx-1 navOption d-flex justify-content-center align-items-center">
                        <a href="<c:url value='/jobs/new'/>" class="nav-link navbarText">
                            <spring:message code="navBar.newJob"/>
                        </a>
                    </li>
                </c:if>

            </ul>
            <ul class="navbar-nav ml-auto">
                <c:choose>
                    <c:when test="${loggedUser!=null}">
                        <li class="nav-item mx-1 d-flex justify-content-center align-items-center">
                            <div class="userContainer pr-0 nav-link">
                                <div id="moreOptions" class="dropdown" data-toggle="dropdown">
                                    <c:choose>
                                        <c:when test="${loggedUser.profileImage == null}">
                                            <img src="<c:url value='/resources/images/userProfile.png'/>"
                                                 class="avatar"
                                                 alt="profileImg"/>
                                        </c:when>
                                        <c:otherwise>
                                            <img src="<c:url value='/user/images/profile/${loggedUser.profileImage.imageId}'/>"
                                                 alt="profileImg"
                                                 class="avatar"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <i class="fas ml-2 fa-chevron-down navbarText" id="navBarArrow"></i>
                                </div>
                                <div class="dropdown-menu dropdown-menu-right">
                                    <a href="<c:url value='/user/account'/>" class="dropdownItem pl-3">
                                        <spring:message code="navBar.signInAs"/>
                                        <span class="username names">
                                            <c:out value="${loggedUser.name}"/>
                                        </span>
                                    </a>
                                    <div class="dropdown-divider"></div>
                                    <a href="<c:url value='/user/account'/>" class="dropdown-item pl-3">
                                        <spring:message code="navBar.profile"/>
                                    </a>
                                    <c:if test="${loggedUser.isProvider}">
                                        <a href="<c:url value='/user/dashboard'/>" class="dropdown-item pl-3">
                                            <spring:message code="navBar.dashboardItem"/>
                                        </a>
                                    </c:if>
                                    <div class="dropdown-divider"></div>
                                    <form id="logoutForm" class="mb-0 dropdown-item px-0"
                                          action="<c:url value='/logout'/>"
                                          method="post">
                                        <button type="submit" id="logoutInput" class="pl-3 btn-block text-left">
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
