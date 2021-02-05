<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<!DOCTYPE html>
	<html>

	<head>
		<%@include file="includes/import.jsp" %>
			<title>Rentaly</title>
	</head>

	<body>
		<%@include file="includes/header.jsp" %>

			<section id="hero">
				<div class="hero-container">
					<c:choose>
						<c:when test='${pageContext.request.userPrincipal.authenticated == true}'>
							<h1>Benvenuto nomeutente</h1>
							<h2>Cosa vuoi fare oggi?</h2>
							<div class="container">
								<a href="/account" class="btn-green">Area Personale</a>
							</div>
						</c:when>

						<c:otherwise>
							<h1>Trova una nuova casa su Rentaly</h1>
							<h2>Pubblica annunci, gestisci le spese e molto altro</h2>
							<a href="/register" class="btn-green">Registrati</a>
						</c:otherwise>
					</c:choose>

				</div>
			</section>

			<%-- @include file="includes/searchbar.jsp" --%>


				<%-- <div class="generic">
					<div class="container hero">
						<div class="row">
							<div class="col-12 col-lg-6 col-xl-5 offset-xl-1">
								<h1>Affitta. Velocemente.</h1>
								<p>Sei pronto a trovare il tuo nuovo appartamento?</p>
								<button class="btn btn-light btn-lg action-button" type="button">Cerca la tua nuova
									casa</button>
							</div>
						</div>
					</div>
					</div>
					--%>
	</body>
	</html>