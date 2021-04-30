<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="container-fluid p-0">
    <div class="row">
        <div class="col-2">
            <c:choose>
                <c:when test="${review.reviewer.profileImageId == 0}">
                    <img width="50px" height="50px" src="<c:url value='/resources/images/userProfile.png'/>"
                    >
                </c:when>
                <c:otherwise>
                    <img width="50px" height="50px"
                         src="<c:url value='/user/images/profile/${review.reviewer.profileImageId}'/>">
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-10">
            <div class="container-fluid p-0">
                <div class="row">
                    <div class="col-7">
                        <a href="<c:url value="/user/${review.reviewer.userId}"/>">
                            <c:out value="${review.reviewer.name} ${review.reviewer.surname}"/>
                        </a>

                    </div>

                    <div class="col-5 d-flex justify-content-end align-items-center">
                        <c:forEach begin="1" end="${review.rating}">
                            <i class="fas iconsColor fa-star fa-1x mr-2"></i>
                        </c:forEach>
                        <c:forEach begin="${review.rating}" end="4">
                            <i class="far iconsColor fa-star fa-1x mr-2"></i>
                        </c:forEach>
                    </div>

                    <div class="col-12 d-flex justify-content-start align-items-center">
                        <p class="text-left reviewBody my-2"><c:out value="${review.description}"/></p>
                    </div>
                </div>

            </div>


        </div>
    </div>
</div>