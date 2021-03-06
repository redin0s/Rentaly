<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>

<head>
	<%@include file="includes/import.jsp" %>
	<title>Nuova Password - Rentaly</title>
    <link rel="stylesheet" href="/css/Login-Register.css">
    <script src="/js/newpassword.js"></script>
</head>

<body>
	<%@include file="includes/header.jsp" %>

    <div class="login-register">
        <form id="new-password-form">
            <sec:csrfInput />
            <h2 class="sr-only">Nuova password</h2>

            <h6>Conferma il tuo indirizzo email, poi inserisci la nuova password.</h6>

            <hr>

            <div class="form-group">
                <input class="form-control" type="email" name="email" id="email" placeholder="Email">
            </div>

            <div class="form-group">
                <input class="form-control" type="password" name="new-password" id="new-password" placeholder="Nuova password">
            </div>

            <div class="form-group">
                <input class="form-control" type="password" name="confirm-password" id="confirm-password" placeholder="Conferma la nuova password">
            </div>

            <div class="form-group">
                <button class="btn btn-primary btn-block" type="submit">Conferma</button>
            </div>

            <div id="err"></div>
        </form>
    </div>


    <%@include file="includes/footer.jsp" %>
</body>
</html