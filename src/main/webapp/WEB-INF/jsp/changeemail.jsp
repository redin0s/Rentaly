<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="/js/changeemail.js"></script>
<link rel="stylesheet" href="/css/Login-Register.css">

<div class="login-register">
    <h4>Email attuale: ${pageContext.request.userPrincipal.name}</h4>
    
    <form id="change-email-form">
        <sec:csrfInput />
        <div class="form-group">
            <input class="form-control" type="email" name="email" id="email" placeholder="Nuova email">
        </div>
        <div class="form-group">
            <input class="form-control" type="email" name="confirm-email" id="confirm-email" placeholder="Conferma la nuova email">
        </div>
        <p id="err" style="color: red;"></p>
        <div class="form-group">
            <button class="btn btn-primary btn-block" type="submit">Conferma</button>
        </div>
    </form>

</div>
