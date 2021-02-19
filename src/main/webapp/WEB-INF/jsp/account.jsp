<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<div class="box" style="position: relative;">
    <div class="h">
        <h4>Email: ${pageContext.request.userPrincipal.name}</h4>
        <h5><a href="#" id="changeEmail">Cambia indirizzo email</a></h5>
    </div>
    <hr>
    <div class="h">
        <h4>Immobili di proprietà attivi: ${realtiesCount}</h4>
        <h5>
            In questo momento ci sono ${ownRentsCount} inquilini
            <br>
            e ${ownChecksToPayCount} spese ancora non pagate
        </h5>
    </div>
    <hr>
    <div class="h">
        <h4>Immobili in cui sei inquilino: ${rentsCount}</h4>
        <h5>Hai ${checksToPayCount} spese da pagare</h5>
    </div>
    <hr>
    <div class="h">
        <h4><a href="#" id="changePassword">Cambia password</a></h4>
    </div>
</div>