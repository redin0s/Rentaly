<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<!DOCTYPE html>
	<html>

	<head>
		<%@include file="includes/import.jsp" %>
			<title>Rentaly</title>
	</head>

	<body>
		<%@include file="includes/header.jsp" %>

			<div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
				<!--<ol class="carousel-indicators">
					<li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
					<li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
					<li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
				</ol>-->
				<div class="carousel-inner" role="listbox">
					<!-- Slide One - Set the background image for this slide in the line below -->
					<div class="carousel-item active"
						<img src="https://source.unsplash.com/LAaSoL0LrYs/1920x1080" class="d-block w-100">
					</div>
					<!-- Slide Two - Set the background image for this slide in the line below -->
					<div class="carousel-item"
						<img src="https://source.unsplash.com/bF2vsubyHcQ/1920x1080" class="d-block w-100">
					</div>
					<!-- Slide Three - Set the background image for this slide in the line below -->
					<div class="carousel-item"
						<img src="https://source.unsplash.com/szFUQoyvrxM/1920x1080" class="d-block w-100">
						<!--<section id="hero">
							<div class="hero-container">
								<h1>Benvenuto nomeutente</h1>
								<h2>Cosa vuoi fare oggi?</h2>
								<div class="container">
									<a href="/account" class="btn-green">Area Personale</a>
								</div>
							</div>
						</section>-->
					</div>
				</div>
				<!--<a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
					<span class="carousel-control-prev-icon" aria-hidden="true"></span>
					<span class="sr-only">Previous</span>
				</a>
				<a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
					<span class="carousel-control-next-icon" aria-hidden="true"></span>
					<span class="sr-only">Next</span>
				</a>-->
			</div>

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
		
		<%@include file="includes/footer.jsp" %>
	</body>

	</html>