<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="/js/changepassword.js"></script>

<div class="box">
    <form id="change-password-form">
        <sec:csrfInput />
        <div class="form-group">
            <input class="form-control" type="password" name="old-password" id="old-password" placeholder="Password corrente">
        </div>
        <p id="err" style="color: red;"></p>
        <input type="hidden" id="email" placeholder="${pageContext.request.userPrincipal.name}">
        <div class="form-group">
            <input class="form-control" type="password" name="new-password" id="new-password" placeholder="Nuova password">
        </div>
        <div class="form-group">
            <input class="form-control" type="password" name="confirm-password" id="confirm-password" placeholder="Conferma la nuova password">
        </div>
        <div class="form-group">
            <button class="btn btn-primary btn-block" type="submit">Conferma</button>
        </div>
    </form>

</div>
