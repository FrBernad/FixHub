<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <%@ include file="../../components/includes/headers.jsp" %>
    <link href='<c:url value="/resources/css/dashboard.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/css/jobCard.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/css/pagination.css"/>' rel="stylesheet">
    <script src='<c:url value="/resources/js/dashboard.js"/>'></script>

    <title><spring:message code="productName"/> | <spring:message code="navBar.dashboard"/></title>

    <spring:message code="navBar.dashboard" var="dashboard"/>
    <spring:message code="dashboard.Work" var="work"/>
    <spring:message code="dashboard.Contacts" var="contacts"/>
</head>
<body>

<div class="container-fluid outerContainer p-0">
    <%@ include file="../../components/navbar.jsp" %>
    <div class="container py-5" style="min-height: 100%">
        <div class="row" style="min-height: 100%">
            <div class="col-3">
                <div class="list-group" role="tablist" id="tabList">
                    <a href="#dashboard" class="list-group-item list-group-item-action active" data-toggle="list"
                       role="tab" data-name="${dashboard}" id="tabDashboard">
                        <i class="fas fa-chart-line mr-2"></i><span class="text"><c:out value="${dashboard}"/></span>
                    </a>
                    <a href="#works" class="list-group-item list-group-item-action inactive" data-toggle="list"
                       role="tab" data-name="${work}" id="tabWorks">
                        <i class="fas fa-wrench mr-2"></i><span class="text">${work}</span><span
                            class="badge badge-pill badge-secondary"><c:out value="${stats.jobsCount}"/></span>
                    </a>
                    <a href="#contacts" class="list-group-item list-group-item-action inactive" data-toggle="list"
                       role="tab" data-name="${contacts}" id="tabContacts">
                        <i class="fas fa-address-book mr-2"></i>
                        <span class="text"><c:out value="${contacts}"/></span>
                        <span class="badge badge-pill badge-secondary">
                            <c:out value="${contactsResults.totalItems}"/>
                        </span>
                    </a>
                </div>
            </div>
            <div class="col-9">
                <div class="card">
                    <div class="card-header" id="panelTitle">
                        ${dashboard}
                    </div>
                    <div class="card-body">
                        <div class="container">
                            <div class="row">
                                <div class="tab-content w-100">


                                    <%--Dashboard--%>

                                    <div class="tab-pane fade ${!contactTab && !searched ? 'show active': ""}" role="tabpanel" id="dashboard">
                                        <div class="col">
                                            <div class="container-fluid p-0">
                                                <div class="row align-items-center justify-content-between">
                                                    <div class="col">
                                                        <div class="card card-body info-box">
                                                            <h2><i class="fas fa-wrench mr-2"></i><c:out
                                                                    value="${stats.jobsCount}"/></h2>
                                                            <h4><spring:message code="dashboard.Works"/></h4>
                                                        </div>
                                                    </div>
                                                    <div class="col">
                                                        <div class="card card-body info-box">
                                                            <h2><i class="fas fa-star mr-2"></i><c:out
                                                                    value="${stats.avgRating}"/>
                                                            </h2>
                                                            <h4><spring:message code="dashboard.Rating"/></h4>
                                                        </div>
                                                    </div>
                                                    <div class="col">
                                                        <div class="card card-body info-box">
                                                            <h2><i class="fas fa-comment mr-2"></i><c:out
                                                                    value="${stats.reviewCount}"/></h2>
                                                            <h4><spring:message code="dashboard.Reviews"/></h4>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>


                                    <%--Tus Trabajos--%>
                                    <c:url value="/user/dashboard/jobs/search" var="postPath"/>
                                    <form:form cssClass="mb-0" action="${postPath}" modelAttribute="searchForm"
                                               method="GET"
                                               id="searchForm">
                                        <form:input path="order" type="hidden" id="orderInput"/>
                                        <form:input path="query" type="hidden" id="searchInput"/>
                                        <form:input path="page" type="hidden" id="pageInput"/>
                                    </form:form>

                                    <div class="tab-pane fade" role="tabpanel" id="works">
                                        <div class="container-fluid">
                                            <div class="row">
                                                <div class="col-7 d-flex align-items-center justify-content-start">
                                                    <div class="input-group">
                                                        <input placeholder="<spring:message code="dashboard.barPlaceholder"/>"
                                                               id="searchBar"
                                                               class="inputRadius form-control p-4"/>
                                                        <div class="input-group-prepend">
                                                            <button id="searchButton"
                                                                    class="btn inputBtn">
                                                                <spring:message code="discover.search"/>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-5 d-flex align-items-center justify-content-end">
                                                    <div class="dropdown ">
                                                        <button class="dropdown-custom dropdown-toggle" type="button"
                                                                id="orderDropdown"
                                                                data-toggle="dropdown" aria-haspopup="true"
                                                                aria-expanded="false">
                                                            <spring:message code="discover.orderBy"/>
                                                            <span class="resultQuery">
                                                            <c:if test="${results.order!=null && !results.order.isEmpty()}">
                                                                <spring:message
                                                                        code="discover.orderOption.${results.order}"/>
                                                            </c:if>
                                                            </span>
                                                        </button>
                                                        <div class="dropdown-menu" aria-labelledby="orderDropdown">
                                                            <c:forEach var="order" items="${orderOptions}">
                                                                <div class="input-group">
                                                                    <button data-order="${order}"
                                                                            class="dropdown-item orderButton">
                                                                        <spring:message
                                                                                code="discover.orderOption.${order}"/>
                                                                    </button>
                                                                </div>
                                                            </c:forEach>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-12 justify-content-start align-items-center">
                                                    <small class="text-danger" style="display: none;"
                                                           id="searchInvalidLength">
                                                        <spring:message code="landingPage.searchInvalidLength"/>
                                                    </small>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <c:if test="${results.query!=null && !results.query.isEmpty()}">
                                                    <div class="col-12 my-2 resultHeader d-flex align-items-start">
                                                        <p class="mb-0"><spring:message code="discover.showingResults"/>
                                                            <span>"<c:out value='${results.query}'/>"</span>
                                                        </p>
                                                    </div>
                                                </c:if>

                                                <c:choose>
                                                    <c:when test="${fn:length(results.results) gt 0}">
                                                        <div class="col-12">
                                                            <div class="container-fluid px-0">
                                                                <div class="row align-items-top ${fn:length(results.results)%2 == 0 ? 'justify-content-between': 'justify-content-start'}">
                                                                    <c:forEach var="job" items="${results.results}">
                                                                        <div class="col-12 mt-3 col-md-6 mb-4 mb-md-0 d-flex align-items-center
                                                                        justify-content-center">
                                                                            <%@ include
                                                                                    file="../../components/cards/jobCard.jsp" %>
                                                                        </div>
                                                                    </c:forEach>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="col-12 d-flex py-4 align-items-center justify-content-center">
                                                            <div class="container mt-2 d-flex align-items-center justify-content-center noJobsFound">
                                                                <p class="m-0 text-center p-4"
                                                                   style="font-size: 16px">
                                                                    <spring:message code="discover.jobsNotFound"/>
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                                <%@ include file="../../components/pagination.jsp" %>
                                            </div>
                                        </div>
                                    </div>

                                    <%--Contactos--%>
                                    <c:url value="/user/dashboard/contacts/search" var="postPath"/>
                                    <form:form cssClass="mb-0" action="${postPath}" modelAttribute="searchForm"
                                               method="GET"
                                               id="searchForm2">
                                        <form:input path="order" type="hidden" id="orderInput2"/>
                                        <form:input path="query" type="hidden" id="searchInput2"/>
                                        <form:input path="page" type="hidden" id="pageInput2"/>
                                    </form:form>
                                    <div class="tab-pane fade" role="tabpanel" id="contacts">
                                        <c:choose>
                                            <c:when test="${contactsResults.totalPages > 0}">
                                                <c:forEach var="contact" items="${contactsResults.results}"
                                                           varStatus="status">
                                                    <div class="mt-2">
                                                        <%@ include
                                                                file="../../components/cards/clientsContactCard.jsp" %>

                                                    </div>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="col-12 d-flex align-items-center justify-content-center">
                                                    <div class="container mt-2 d-flex align-items-center justify-content-center">
                                                        <p class="m-0 text-center p-4" style="font-size: 16px">
                                                            <spring:message code="dashboard.Contact.Empty"/>

                                                        </p>
                                                    </div>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>


                                        <div class="col-12 align-items-center justify-content-center mt-4
                                                     ${contactsResults.totalPages<=1 ? 'd-none':''} ">
                                            <nav class="d-flex align-items-center justify-content-center">
                                                <ul class="pagination mb-0" id="pagination2"
                                                    data-page="${contactsResults.page}"
                                                    data-contactmodal="${contactTab}">
                                                    <li class="page-item ${contactsResults.isFirst() ? 'disabled' : ''}">
                                                        <a class="page-link" id="prev2"
                                                           aria-label="Previous">
                                                            <span aria-hidden="true">&laquo;</span>
                                                        </a>
                                                    </li>
                                                    <c:forEach begin="1"
                                                               end="${contactsResults.totalPages}"
                                                               varStatus="status">
                                                        <li class="page-item ${contactsResults.page == status.index-1 ? 'active' : ''}">
                                                            <a
                                                                    class="page-link index2"
                                                                    data-index="${status.index}">${status.index}
                                                            </a>
                                                        </li>
                                                    </c:forEach>
                                                    <li class="page-item ${contactsResults.isLast() ? 'disabled' : ''}">
                                                        <a class="page-link" id="next2"
                                                           aria-label="Next">
                                                            <span aria-hidden="true">&raquo;</span>
                                                        </a>
                                                    </li>
                                                </ul>
                                            </nav>
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

<%@ include file="../../components/footer.jsp" %>
<%@ include file="../../components/includes/globalScripts.jsp" %>

</body>
</html>