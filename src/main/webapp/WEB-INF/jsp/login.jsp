<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
    <%@include file="includes/import.jsp" %>
    <title>Accedi - Rentaly</title>
</head>

<body>
    <div>
        <div class="header-blue">
            <%@include file="includes/header.jsp" %>
            
            <div class="container hero">
                <form method="POST" action="doLogin">
                <div class="row">
                    <div class="col-12 col-lg-6 col-xl-5 offset-md-0 offset-xl-1"><input type="text" id="u" name="user" default="Username"></div>
                </div>
                <div class="row">
                    <div class="col-12 col-lg-6 col-xl-5 offset-md-0 offset-xl-1"><input type="password" id="p" name="pass"></div>
                </div>
                <div class="row">
                    <div class="col-12 col-lg-6 col-xl-5 offset-md-0 offset-xl-1"><input class="btn btn-primary" type="submit" value="Accedi" id="doLogin"></div>
                </div>
                </form>
                <div class="row">
                    <div class="col">
                        <p>Non hai ancora un account? <a href="register">Registrati</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>
