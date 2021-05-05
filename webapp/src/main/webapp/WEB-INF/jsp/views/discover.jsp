<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title><spring:message code="productName"/> | <spring:message code="discover.title"/></title>

    <%@ include file="../components/includes/headers.jsp" %>

    <link href='<c:url value="/resources/css/discover.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/css/jobCard.css"/>' rel="stylesheet">
    <link href='<c:url value="/resources/css/pagination.css"/>' rel="stylesheet">
    <script src='<c:url value="/resources/js/discover.js"/>'></script>

<body>
<div class="outerContainer pb-4">
    <%@ include file="../components/navbar.jsp" %>
    <div class="container-lg">
        <c:url value="/discover/search" var="postPath"/>
        <form:form cssClass="mb-0" action="${postPath}" modelAttribute="searchForm" method="GET"
                   id="searchForm">
            <form:input path="order" type="hidden" id="orderInput"/>
            <form:input path="category" type="hidden" id="categoryInput"/>
            <form:input path="state" type="hidden" id="stateInput"/>
            <form:input path="city" type="hidden" id="cityInput"/>
            <form:input path="query" type="hidden" id="searchInput"/>
            <form:input path="page" type="hidden" id="pageInput"/>
        </form:form>
        <div class="row pb-4 align-items-center justify-content-between">
            <div class="col-12 px-0 d-flex align-items-center justify-content-start">
                <div class="input-group">
                    <input placeholder="<spring:message code="discover.barPlaceholder"/>"
                           id="searchBar"
                           class="inputRadius form-control p-4"/>
                    <div class="input-group-prepend">
                        <button id="searchButton" class="btn inputBtn" style="background-color: #003258">
                            <spring:message code="discover.search"/>
                        </button>
                    </div>
                </div>
            </div>
            <div class="col-12 px-0 justify-content-start align-items-center">
                <small class="text-danger" style="display: none;" id="searchInvalidLength">
                    <spring:message code="landingPage.searchInvalidLength"/>
                </small>
            </div>
            <div class="col-12 mt-4 d-flex p-0 align-items-center justify-content-md-end justify-content-around">
                <div class="container-fluid px-0">
                    <div class="row justify-content-around align-items-center">

                        <spring:message var="allString" code="discover.all"/>
                        <%--CATEGORY--%>
                        <div class="col-6 col-md-3 d-flex align-items-center justify-content-md-end">
                            <div class="dropdown w-100">
                                <button class="dropdown-custom dropdown-toggle d-flex align-items-center justify-content-between"
                                        type="button"
                                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <span><spring:message code="discover.category"/></span>
                                    <span class="resultQuery">
                                        <c:if test="${results.category!=null && !results.category.isEmpty()}">
                                            <spring:message code="home.categories.${results.category}"/>
                                        </c:if>
                                        <c:if test="${results.category!=null && results.category.isEmpty()}">
                                            ${allString}
                                        </c:if>
                                    </span>
                                </button>
                                <div class="dropdown-menu limitSizeDropdown" id="categoryDropdownMenu"
                                     aria-labelledby="categoryDropdown">
                                    <div class="input-group">
                                        <button id="emptyCategoryButton" data-category=""
                                                class="dropdown-item">
                                            ${allString}
                                        </button>
                                    </div>
                                    <c:forEach var="category" items="${categories}">
                                        <div class="input-group">
                                            <button data-category="${category}" class="dropdown-item categoryButton">
                                                <spring:message code="home.categories.${category}"/>
                                            </button>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>

                        <%--STATE--%>
                        <div class="col-6 col-md-3 mt-4 mt-md-0 d-flex align-items-center justify-content-md-end">
                            <div class="dropdown w-100">
                                <button class="dropdown-custom dropdown-toggle d-flex align-items-center justify-content-between"
                                        type="button" id="stateDropdown"
                                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <span><spring:message code="discover.state"/></span>
                                    <span class="resultQuery">
                                        <c:if test="${results.state!=null && !results.state.isEmpty()}">
                                            <c:out value="${results.state}"/>
                                        </c:if>
                                         <c:if test="${results.state!=null && results.state.isEmpty()}">
                                             <spring:message code="discover.all"/>
                                         </c:if>
                                   </span>
                                </button>
                                <div class="dropdown-menu limitSizeDropdown" aria-labelledby="orderDropdown">
                                    <div class="input-group">
                                        <button id="emptyStateButton" data-state="" class="dropdown-item">${allString}</button>
                                    </div>
                                    <c:forEach var="state" items="${states}">
                                        <div class="input-group">
                                            <button data-state="${state.id}" class="dropdown-item stateButton">
                                                <c:out value="${state.name}"/>
                                            </button>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>

                        <%--CITY--%>
                        <div class="col-6 col-md-3 mt-4 mt-md-0 d-flex align-items-center justify-content-md-end">
                            <div class="dropdown w-100">
                                <button class="dropdown-custom dropdown-toggle d-flex align-items-center justify-content-between"
                                        type="button" id="cityDropdown"
                                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <span><spring:message code="discover.city"/></span>
                                    <span class="resultQuery">
                                         <c:if test="${results.city!=null && !results.city.isEmpty()}">
                                             <c:out value="${results.city}"/>
                                         </c:if>
                                         <c:if test="${results.city!=null && results.city.isEmpty()}">
                                             <spring:message code="discover.all"/>
                                         </c:if>
                                    </span>
                                </button>
                                <div class="dropdown-menu limitSizeDropdown" aria-labelledby="orderDropdown">
                                    <div class="input-group">
                                        <button id="emptyCityButton" data-city="" class="dropdown-item">${allString}</button>
                                    </div>
                                    <c:forEach var="city" items="${cities}">
                                        <div class="input-group">
                                            <button data-city="${city.id}" class="dropdown-item cityButton">
                                                <c:out value="${city.name}"/>
                                            </button>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>

                        <%--ORDER--%>
                        <div class="col-6 col-md-3 d-flex align-items-center justify-content-md-end">
                            <div class="dropdown w-100">
                                <button class="dropdown-custom dropdown-toggle d-flex align-items-center justify-content-between"
                                        type="button" id="orderDropdown"
                                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <span><spring:message code="discover.orderBy"/></span>
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
                    </div>
                </div>
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
                                <c:when test="${fn:length(results.results) gt 0}">
                                    <div class="col-12">
                                        <div class="container-fluid p-0">
                                            <div class="row align-items-top w-100  mx-0 ${fn:length(results.results)%3 == 0 ? 'justify-content-between': 'justify-content-start'}">
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
                            <%@ include file="../components/pagination.jsp" %>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

<%@ include file="../components/footer.jsp" %>
<%@ include file="../components/includes/globalScripts.jsp" %>

</body>
</html>
