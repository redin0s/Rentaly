<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>

<head>
	<%@include file="includes/import.jsp" %>
	<title>Accedi - Rentaly</title>
    <link rel="stylesheet" href="../../../css/Login-Register.css">
</head>

<body>
	<%@include file="includes/header.jsp" %>
    
    <div class="login-register">
        <c:url var="login_url" value="/login"/>
        <form method="post" action="${login_url}" id="login">
            <h2 class="sr-only">Accedi</h2>

            <div class="form-group">
                <input class="form-control" type="email" name="email" id="email" placeholder="Email">
            </div>

            <div class="form-group">
                <input class="form-control" type="password" name="password" id="password" placeholder="Password">
            </div>

            <div class="form-group">
                <button class="btn btn-primary btn-block" type="submit">Accedi</button>
            </div>
        </form>
            
        <c:choose>
            <c:when test="${param.error != null}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <p id="error">Email o password sbagliata. <a href="#">Hai dimenticato la password?</a> </p>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:when>
        </c:choose>

        <p class="bottom">Non hai ancora un account? <a href="register">Registrati</a></p>
        
    </div> 

</body>

</html>