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
		<div class="generic-alert">
				<div class="container">
					<div class="row">
						<c:if test="${empty type}">
							<c:set var="type" value="alert-danger"/>
							<c:set var="title" value="Errore! "/>
							<c:set var="message" value="Qualcosa è andato storto, ti preghiamo di tornare alla pagina iniziale"/>
						</c:if>
						<div class="alert ${type} mx-auto" role="alert"> <!--REF for alert typ: https://getbootstrap.com/docs/4.0/components/alerts/ -->
						<h4 class="alert-heading">${title} <i class="fa fa-frown-o" aria-hidden="true"></i></h4>
						<p>${message}</p>
						</div>
						<hr>
						<a href="/" class="mb-0">Torna alla home</a>
					</div>
				</div>
		</div>
	</body>
</html>