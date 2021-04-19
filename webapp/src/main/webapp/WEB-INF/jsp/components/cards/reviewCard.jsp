<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="container-fluid p-0">
    <div class="row">
        <div class="col-12 d-flex justify-content-start align-items-center">
            <c:forEach begin="1" end="${review.rating}">
                <i class="fas iconsColor fa-star fa-1x mr-2"></i>
            </c:forEach>
            <c:forEach begin="${review.rating}" end="4">
                <i class="far iconsColor fa-star fa-1x mr-2"></i>
            </c:forEach>
        </div>
        <div class="col-12">
            <div class="container-fluid p-0">
                <p class="text-left reviewBody my-2"><c:out value="${review.description}"/></p>
            </div>
        </div>
    </div>
</div>