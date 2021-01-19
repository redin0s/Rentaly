<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<%@include file="includes/import.jsp" %>
	<title>Registrati - Rentaly</title>
	<script src="../../js/register.js"></script>
    <link rel="stylesheet" href="../../../css/Login-Register.css">
</head>

<body>
	<%@include file="includes/header.jsp" %>

	<div class="login-register">
        <form method="post" action="doRegister">
            <h2 class="sr-only">Registrati</h2>

            <div class="form-group">
                <input class="form-control" type="email" name="email" id="#email" placeholder="Email">
            </div>

            <div class="form-group">
                <input class="form-control" type="password" name="password" id="#password" placeholder="Password">
            </div>

            <div class="form-group">
                <button class="btn btn-primary btn-block" type="submit">Accedi</button>
            </div>
            
            <p class="forgot">Hai già un account? <a href="login">Accedi</a></p>
        </form>
    </div> 

</body>

</html>