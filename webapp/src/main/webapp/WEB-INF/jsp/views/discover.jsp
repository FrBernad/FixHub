<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title><spring:message code="productName"/> | <spring:message code="discover.title"/></title>

    <%@ include file="../components/includes/headers.jsp" %>

    <link href='<c:url value="/resources/css/discover.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/css/jobCard.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/css/pagination.css"/>' rel="stylesheet">

<body>
<div class="outerContainer pb-4">
    <%@ include file="../components/navbar.jsp" %>
    <div class="container-lg">
        <c:url value="/discover/search" var="postPath"/>
        <form:form cssClass="mb-0" action="${postPath}" modelAttribute="searchForm" method="GET"
                   id="searchForm">
            <form:input path="order" type="hidden" id="orderInput"/>
            <form:input path="filter" type="hidden" id="filterInput"/>
            <form:input path="query" type="hidden" id="searchInput"/>
            <form:input path="page" type="hidden" id="pageInput"/>
        </form:form>
        <div class="row pb-4 align-items-center justify-content-between">
            <div class="col-7 p-0 d-flex align-items-center justify-content-start">
                <div class="input-group">
                    <input placeholder="<spring:message code="discover.barPlaceholder"/>"
                           id="searchBar"
                           class="inputRadius form-control p-4"/>
                    <div class="input-group-prepend">
                        <button id="searchButton" class="btn btn-lg inputBtn">
                            <spring:message code="discover.search"/>
                        </button>
                    </div>
                </div>
            </div>
            <div class="col-5 d-flex p-0 align-items-center justify-content-end">
                <div class="dropdown mr-4">
                    <button class="dropdown-custom dropdown-toggle" type="button" id="filterDropdown"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <spring:message code="discover.filterBy"/>
                        <span class="resultQuery">
                        <c:if test="${results.filter!=null && !results.filter.isEmpty()}">
                            <spring:message code="home.categories.${results.filter}"/>
                        </c:if>
                        <c:if test="${results.filter!=null && results.filter.isEmpty()}">
                            -
                        </c:if>
                        </span>
                    </button>
                    <div class="dropdown-menu" aria-labelledby="filterDropdown">
                        <div class="input-group">
                            <button id="emptyFilterButton" data-filter="" class="dropdown-item">-</button>
                        </div>
                        <c:forEach var="filter" items="${filters}">
                            <div class="input-group">
                                <button data-filter="${filter}" class="dropdown-item filterButton">
                                    <spring:message code="home.categories.${filter}"/>
                                </button>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <div class="dropdown ">
                    <button class="dropdown-custom dropdown-toggle" type="button" id="orderDropdown"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <spring:message code="discover.orderBy"/>
                        <span class="resultQuery">
                        <c:if test="${results.order!=null && !results.order.isEmpty()}">
                            <spring:message code="discover.orderOption.${results.order}"/>
                        </c:if>
                        </span>
                    </button>
                    <div class="dropdown-menu" aria-labelledby="orderDropdown">
                        <c:forEach var="order" items="${orderOptions}">
                            <div class="input-group">
                                <button data-order="${order}" class="dropdown-item orderButton">
                                    <spring:message code="discover.orderOption.${order}"/>
                                </button>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="col-12 justify-content-start align-items-center">
                <small class="text-danger" style="display: none;" id="searchInvalidLength">
                    <spring:message code="landingPage.searchInvalidLength"/>
                </small>
            </div>
        </div>
    </div>


    <div class="container-lg px-0">
        <div class="container-fluid p-0 jobsContainer">
            <div class="row" style="padding: 3em;min-height: 75%">
                <c:if test="${results.query!=null && !results.query.isEmpty()}">
                    <div class="col-12 mb-3 resultHeader p-0 d-flex align-items-start">
                        <p class="mb-0"><spring:message code="discover.showingResults"/>
                            <span>"<c:out value='${results.query}'/>"</span>
                        </p>
                    </div>
                </c:if>
                <div class="col-12">
                    <div class="container-fluid px-0" style="min-height: 100%">
                        <div class="row align-content-between" style="min-height: 100%">
                            <c:choose>
                                <c:when test="${results.results.size()>0}">
                                    <div class="col-12">
                                        <div class="container-fluid p-0">
                                            <div class="row align-items-top w-100  mx-0 ${results.results.size()%3 == 0 ? 'justify-content-between': 'justify-content-start'}">
                                                <c:forEach var="job" items="${results.results}">
                                                    <div class="col-12 mt-3 col-md-4 mb-4 mb-md-0 d-flex align-items-center justify-content-center">
                                                        <%@ include file="../components/cards/jobCard.jsp" %>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="col-12 h-100 d-flex align-items-center justify-content-center">
                                        <div class="container mt-2 d-flex align-items-center justify-content-center noJobsFound">
                                            <p class="m-0 text-center p-4" style="font-size: 16px">
                                                <spring:message code="discover.jobsNotFound"/>
                                            </p>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <div class="col-12 align-items-center justify-content-center mt-4
                                        ${results.totalPages<=1 ? 'd-none':''} ">
                                <nav class="d-flex align-items-center justify-content-center">
                                    <ul class="pagination mb-0" id="pagination" data-page="${results.page}">
                                        <li class="page-item ${results.isFirst() ? 'disabled' : ''}">
                                            <a class="page-link" id="prev" aria-label="Previous">
                                                <span aria-hidden="true">&laquo;</span>
                                            </a>
                                        </li>
                                        <c:forEach begin="1" end="${results.totalPages}" varStatus="status">
                                            <li class="page-item ${results.page == status.index-1 ? 'active' : ''}"><a
                                                    class="page-link index"
                                                    data-index="${status.index}">${status.index}</a>
                                            </li>
                                        </c:forEach>
                                        <li class="page-item ${results.isLast() ? 'disabled' : ''}">
                                            <a class="page-link" id="next" aria-label="Next">
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

<%@ include file="../components/footer.jsp" %>

<%@ include file="../components/includes/bottomScripts.jsp" %>
<script src='<c:url value="/resources/js/discover.js"/>'></script>

</body>
</html>
