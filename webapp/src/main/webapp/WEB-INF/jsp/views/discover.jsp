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

    <script src='<c:url value="/resources/js/discover.js"/>'></script>
<body>
<div class="outerContainer pb-4">
    <%@ include file="../components/navbar.jsp" %>
    <div class="container-lg">
        <c:url value="/discover/search" var="postPath"/>
        <form:form cssClass="mb-0" action="${postPath}" modelAttribute="searchForm" method="GET" id="searchForm">
            <form:input path="order" type="hidden" id="orderInput"/>
            <form:input path="filter" type="hidden" id="filterInput"/>
            <form:input path="query" type="hidden" id="searchInput"/>
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
                        <c:if test="${filter!=null && !filter.isEmpty()}">
                            <spring:message code="home.categories.${filter}"/>
                        </c:if>
                        <c:if test="${filter!=null && filter.isEmpty()}">
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
                        <c:if test="${order!=null && !order.isEmpty()}">
                            <spring:message code="discover.orderOption.${order}"/>
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
            <div class="row" style="padding: 3em;">
                <c:if test="${searchPhrase!=null && !searchPhrase.isEmpty()}">
                    <div class="col-12 mb-5 resultHeader p-0 d-flex align-items-start">
                        <p class="mb-0"><spring:message code="discover.showingResults"/>
                            <span>"<c:out value='${searchPhrase}'/>"</span>
                        </p>
                    </div>
                </c:if>
                <div class="col-12 p-0">
                    <div class="container-fluid">
                        <c:choose>
                            <c:when test="${jobs.size()>0}">
                                <div class="row align-items-top ${jobs.size()%3 == 0 ? 'justify-content-between': 'justify-content-start'}">
                                    <c:forEach var="job" items="${jobs}">
                                        <div class="col-12 mt-3 col-md-4 mb-4 mb-md-0 d-flex align-items-center justify-content-center">
                                            <%@ include file="../components/jobCard.jsp" %>
                                        </div>
                                    </c:forEach>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="row align-items-center justify-content-center h-100">
                                    <div class="col-12 d-flex align-items-center justify-content-center">
                                        <div class="container mt-2 d-flex align-items-center justify-content-center noJobsFound">
                                            <p class="m-0 text-center p-4" style="font-size: 16px">
                                                <spring:message code="discover.jobsNotFound"/>
                                            </p>
                                        </div>
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

<%@ include file="../components/footer.jsp" %>

<%@ include file="../components/includes/bottomScripts.jsp" %>
<script src='<c:url value="/resources/js/discover.js"/>'></script>

</body>
</html>
