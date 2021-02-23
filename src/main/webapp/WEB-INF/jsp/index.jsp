
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<!DOCTYPE html>
	<html>

	<head>
		<%@include file="includes/import.jsp" %>
			<title>Rentaly</title>
	</head>

	<body>
		<%@include file="includes/header.jsp" %>

			<div id="indexCarousel" class="carousel slide" data-ride="carousel">
				<div class="carousel-inner" role="listbox">
					<div class="carousel-item index active">
						<!--img src="https://source.unsplash.com/LAaSoL0LrYs/1920x1080"-->
						<img src="/images/apartment_buildings-wallpaper-1920x1080.jpg">
					</div>
					<div class="carousel-item index">
						<!--img src="https://source.unsplash.com/bF2vsubyHcQ/1920x1080"-->
						<img src="/images/Betts-Luxury-apartment.png">			
					</div>
					<div class="carousel-item index">
						<!--img src="https://source.unsplash.com/szFUQoyvrxM/1920x1080"-->
						<img src="/images/buildings_up-wallpaper-1920x1080.jpg">

						<!-- <img src="https://i.imgur.com/VAViwrf.jpg"> -->
					</div>
					<section id="hero">
						<div class="hero-container">
							<c:choose>
								<c:when test='${pageContext.request.userPrincipal.authenticated == true}'>
									<h1>Benvenuto, ${pageContext.request.userPrincipal.name}!</h1>
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
				</div>
			</div>

			
		
		<%@include file="includes/footer.jsp" %>
	</body>

	</html>