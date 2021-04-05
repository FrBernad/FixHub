<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <%@ include file="../components/headers.jsp" %>
    <title><spring:message code="discover.title"/></title>
    <link href='<c:url value="/resources/css/discover.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/css/jobCard.css"/>' rel="stylesheet">

    <script src='<c:url value="/resources/js/discoverySearch.js"/>'></script>
<body>
<%@ include file="../components/navbar.jsp" %>
<div class="outerContainer py-4 ${jobs.size()==0 ? 'h-75' : ''}">
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
                        <button onclick="setSearchValueAndSubmit()" class="btn btn-lg inputBtn"><spring:message code="discover.search"/></button>
                    </div>
                </div>
            </div>
            <div class="col-5 d-flex p-0 align-items-center justify-content-end">
                <div class="dropdown mr-4">
                    <button class="btn btn-secondary btn-sm dropdown-toggle" type="button" id="filterDropdown"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <spring:message code="discover.filterBy"/>
                        <c:if test="${filter!=null && !filter.isEmpty()}">
                            <spring:message code="home.categories.${filter}"/>
                        </c:if>
                    </button>
                    <div class="dropdown-menu" aria-labelledby="filterDropdown">
                        <c:forEach var="filter" items="${filters}">
                            <div class="input-group">
                                <button onclick="setFilterValueAndSubmit('${filter}')" class="dropdown-item">
                                    <spring:message code="home.categories.${filter}"/>
                                </button>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <div class="dropdown">
                    <button class="btn btn-secondary btn-sm dropdown-toggle" type="button" id="orderDropdown"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <spring:message code="discover.orderBy"/>
                        <c:if test="${order!=null && !order.isEmpty()}">
                            <spring:message code="discover.orderOption.${order}"/>
                        </c:if>
                    </button>
                    <div class="dropdown-menu" aria-labelledby="orderDropdown">
                        <c:forEach var="order" items="${orderOptions}">
                            <div class="input-group">
                                <button onclick="setOrderValueAndSubmit('${order}')" class="dropdown-item">
                                    <spring:message code="discover.orderOption.${order}"/>
                                </button>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <div class="container-lg ">
        <div class="row jobsContainer ">
            <c:if test="${searchPhrase!=null && !searchPhrase.isEmpty()}">
                <div class="col-12 p-0 mb-5 resultHeader d-flex align-items-center">
                    <p class="mb-0"><spring:message code="discover.showingResults"/><span>"<c:out value='${searchPhrase}'/>"</span></p>
                </div>
            </c:if>
            <div class="col-12 p-0">
                <div class="container-fluid">
                    <div class="row align-items-top ${jobs.size()%3 == 0 ? 'justify-content-between': 'justify-content-start'}">
                        <c:choose>
                            <c:when test="${jobs.size()>0}">
                                <c:forEach var="job" items="${jobs}">
                                    <%@ include file="../components/jobCard.jsp" %>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div class="col-12 d-flex align-items-center justify-content-center">
                                    <div class="container mt-2 d-flex align-items-center justify-content-center"
                                         style="height: 300px; width: auto; background-color: white">
                                        <p class="m-0 text-center p-4" style="font-size: 16px"><spring:message code="discover.jobsNotFound"/></p>
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
<%@ include file="../components/bottomScripts.jsp" %>

</body>
</html>
