<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
    <%@include file="includes/import.jsp" %>
    <title>Registrati - Rentaly</title>
    <script src="../../js/login.js"></script>
</head>

<body>
    <div class="header-blue">
        <%@include file="includes/header.jsp" %>
    </div>
        <div class="container hero">
            <form method="POST" action="doRegister">
            <div class="row">
                <div class="col-12 col-lg-6 col-xl-5 offset-md-0 offset-xl-1"><input type="text" id="u" name="user"><label for="u">Username</label></div>
            </div>
            <div class="row">
                <div class="col-12 col-lg-6 col-xl-5 offset-md-0 offset-xl-1"><input type="password" id="p" name="pass"><label for="p">Password</label></div>
            </div>
            <div class="row">
                <div class="col-12 col-lg-6 col-xl-5 offset-md-0 offset-xl-1"><input class="btn btn-primary" type="submit" value="Send">Registrati</div>
            </div>
            </form>
            <div class="row">
                <div class="col">
                    <p>Hai già un account? <a href="login">Accedi</a></p>
                </div>
            </div>
        </div>
       

</body>

</html>