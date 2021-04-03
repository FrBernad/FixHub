<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Fixhub</title>
    <%@ include file="../components/headers.jsp" %>
    <link href='<c:url value="/resources/css/join.css"/>' rel="stylesheet">
</head>

<body>
<%@ include file="../components/navbar.jsp" %>

<div class="container-fluid h-75 d-flex align-items-center justify-content-center"
     style="background-color: rgb(245,245,242)">
    <div class="container-lg h-75 w-50 p-5" style="background-color: white; max-width: 32em">
        <div class="row w-100 m-0 align-items-center justify-content-center">
            <div class="col-12">
                <h1 class="text-center title">Ofrece tu servicio</h1>
                <p class="subtitle text-center mb-4"><span class="font-weight-bold">Trabaja con fixhub</span>, nuevos
                    clientes ofrecen<br>nuevos servicios dia a dia.</p>
            </div>
            <div class="col-12">
                <div class="container-lg">
                    <div class="row">
                        <div class="col-12">
                            <div class="row">
                                <div class="col-12">
                                    <c:url value="/join" var="postPath"/>
                                    <form:form modelAttribute="emailForm" id="mailForm" action="${postPath}"
                                               method="POST">
                                        <div class="form-group">
                                            <form:label path="email">Email</form:label>
                                            <form:input type="text" class="form-control input" path="email" id="email"
                                                        aria-describedby="email input"/>
                                            <form:errors path="email" cssClass="formError" element="p"/>
                                        </div>
                                    </form:form>
                                </div>
                                <div class="col-12 d-flex align-items-center justify-content-center">
                                    <button form="mailForm" type="submit" class="w-100 continueBtn my-2">
                                        Continuar
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div class="col-12">
                            <p class="my-4 text-center" style="font-size: 14px">No tienes un email asociado?
                                <a href="<c:url value='/join/register'/>">Únete</a>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../components/footer.jsp" %>
</body>

</html>