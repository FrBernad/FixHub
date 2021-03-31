<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Contact</title>
    <%@ include file="../components/headers.jsp" %>
</head>
<body>
<%@ include file="../components/navbar.jsp" %>

<div class="container-fluid d-flex align-items-center justify-content-center"
     style="background-color: rgb(245,245,242)">
    <div class="container-lg w-50 p-5 my-5" style="background-color: white; max-width: 75em">
        <div class="row w-100 m-0 align-items-center justify-content-center">
            <div class="col">
                <h1 class="text-center title">Contactá al proveedor</h1>
                <p class="subtitle text-center mb-4"><span class="font-weight-bold">¡Ingresá tus datos</span> y
                    comencemos a
                    <br>trabajar!</p>
                <div class="container-lg">
                    <div class="row">
                        <div class="col-12 d-flex align-items-center justify-content-center">
                            <%@ include file="../components/contactForm.jsp" %>
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
