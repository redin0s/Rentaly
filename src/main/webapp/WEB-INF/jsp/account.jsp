<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>

<head>
	<%@include file="includes/import.jsp" %>
	<title>Account - Rentaly</title>
	<link rel="stylesheet" href="../../../css/Generic.css">
</head>

<body>
    <%@include file="includes/header.jsp" %>
    <div class="generic">
        <div class="highlight-blue">
            <div class="intro">
                <h2 class="text-center">Benvenuto!</h2>
                <!--p class="text-center">Vuoi trovare un nuovo immobile?</p>
            </div>
            <div class="buttons">
                <a class="btn btn-primary" role="button" href="search">Cerca</a-->
            </div>
        </div>

        <div class="highlight-clean">
            <!--div class="intro">
                <p class="text-center">This is the first test text</p>
            </div-->
            <div class="buttons">
                <a class="btn btn-primary" role="button" href="myRealties">Immobili</a>
                <a class="btn btn-primary" role="button" href="myRents">Affitti</a>
            </div>

            <!--div class="intro">
                <p class="text-center">This is the second text</p>
            </div-->
            <div class="buttons">
                <a class="btn btn-light" role="button" href="/edit">Gestione account</a>
            </div>

        </div>  
    </div>  



</body>

</html>