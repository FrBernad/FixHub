<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <link href="resources/css/landingPage.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0"
            crossorigin="anonymous"></script>
</head>

<body>
<%@ include file="../components/navbar.jsp" %>
<div class="container-fluid h-75">
    <div class="row align-items-center justify-content-center h-100 bgImg">
        <div class="col-8 h-75 w-50 d-flex justify-content-center align-items-center">
            <input placeholder="Busca el especialista que necesites" class="form-control">
        </div>
    </div>
</div>
</body>

</html>
