<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>

<head>
	<%@include file="includes/import.jsp" %>
	<title>Registrati - Rentaly</title>
    <link rel="stylesheet" href="/css/Login-Register.css">
</head>

<body>
    <%@include file="includes/header.jsp" %>
    <script src="/js/register.js"></script>

	<div class="login-register">
        <form id="register-form">
            <h2 class="sr-only">Registrati</h2>

            <div class="form-group">
                <input class="form-control" type="email" name="email" id="email" placeholder="Email">
            </div>

            <div class="form-group">
                <input class="form-control" type="password" name="password" id="password" placeholder="Password">
            </div>
            <sec:csrfInput />

            <div class="form-group">
                <button class="btn btn-primary btn-block" type="submit">Registrati</button>
            </div>
            <div id="error" class="alert alert-danger alert-dismissible fade show" role="alert">
                <p>Errore nella registrazione, riprova più tardi.</p>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <p class="bottom">Hai già un account? <a href="login">Accedi</a></p>
        </form>
    </div> 

    <%@include file="includes/footer.jsp" %>
</body>

</html>