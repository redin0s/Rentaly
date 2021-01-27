<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isErrorPage="true" %>  

<!DOCTYPE html>
<html>
	<head>
        <%@include file="includes/import.jsp" %>
		<title>Rentaly</title>
	</head>
	<body>
		<%@include file="includes/header.jsp" %>
		<div class="generic">
				<div class="container">
					<div class="row">
						<div class="alert alert-danger mx-auto" role="alert">
						  <h4 class="alert-heading">Errore! <i class="fa fa-frown-o" aria-hidden="true"></i></h4>
						  <p>Qualcosa è andato storto, ti preghiamo di ritornare alla pagina iniziale</p>
						  <hr>
						  <a href="/" class="mb-0">Torna alla home</a>
						</div>
					</div>
				</div>
		</div>
	</body>
</html>