<div class="container-lg py-1">
    <div class="row">
        <div class="col-2 p-0 d-flex justify-content-center align-items-center">
            <c:choose>
                <c:when test="${follower.profileImageId == 0}">
                    <img src="<c:url value='/resources/images/userProfile.png'/>"
                         class="avatarPicture">
                </c:when>
                <c:otherwise>
                    <img src="<c:url value='/user/images/profile/${follower.profileImageId}'/>"
                         class="avatarPicture">
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-8 p-0 d-flex justify-content-center align-items-center">
            <div class="container-fluid px-0">
                <div class="row">
                    <div class="col-12 d-flex align-items-center justify-content-start">
                        <a href="<c:url value='/user/${follower.id}'/>">
                            <span>
                                <c:out value="${follower.name} ${follower.surname}"/>
                            </span>
                        </a>
                    </div>
                    <div class="col-10 px-0">
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-1 d-flex align-items-center justify-content-start">
                                    <i class="far fa-envelope" aria-hidden="true"></i>
                                </div>
                                <div class="col-10 d-flex align-items-center justify-content-start">
                                    <span>
                                      <c:out value="${follower.email}"/>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%--        <c:if test="${loggedUser != null && loggedUser.id==user.id}">--%>
        <%--            <div class="col-3 p-0 d-flex justify-content-center align-items-center">--%>
        <%--                <c:choose>--%>
        <%--                    <c:when test="${results.results.stream().anyMatch(u-> u.getId()==user.id)}">--%>
        <%--                        <button class="contactBtn"><spring:message code="profilePage.unfollow"/></button>--%>
        <%--                    </c:when>--%>
        <%--                    <c:otherwise>--%>
        <%--                        <button class="contactBtn"><spring:message code="profilePage.unfollow"/></button>--%>
        <%--                    </c:otherwise>--%>
        <%--                </c:choose>--%>
        <%--            </div>--%>
        <%--        </c:if>--%>
    </div>

</div>
