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
		<div class="page-wrap d-flex flex-row align-items-center">
			<div class="container">
				<div class="row justify-content-center">
					<div class="col-md-6 text-center">
						<c:if test="${empty type}">
							<c:set var="type" value="alert-danger"/>
							<c:set var="title" value="Errore!"/>
							<c:set var="message" value="Qualcosa è andato storto, ti preghiamo di tornare alla pagina iniziale"/>
						</c:if>
						<div class="alert ${type} mx-auto" role="alert">
							<h4 class="alert-heading">
								${title} <i class="fa fa-frown-o" aria-hidden="true"></i>
							</h4>
							<p>${message}</p>
						</div>
						<hr>
						<a href="/" class="mb-0">Torna alla home</a>
					</div>
				</div>
			</div>
		</div>
	</body>
	
    <%@include file="includes/footer.jsp" %>
</html>