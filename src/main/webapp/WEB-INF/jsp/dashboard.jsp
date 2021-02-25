<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>

<head>
	<%@include file="includes/import.jsp" %>
		<title>Account - Rentaly</title>
</head>

<body>

	<jsp:include page="includes/header.jsp">
		<jsp:param name="account" value="1" />
	</jsp:include>

	<c:if test="${active eq false}">
		<div class="alert alert-warning alert-dismissible fade show" role="alert">
			<sec:csrfInput />
			<strong>Il tuo account non è ancora stato confermato.</strong>
			<p>Controlla la tua casella di posta elettronica, o in alternativa, <a href="javascript:;"
					id="confirm-email">possiamo inviarti una nuova email.</a></p>
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
	</c:if>

	<div class="wrapper">
		<nav id="sidebar">
			<div class="sidebar-header">
				<h6>Benvenuto, ${pageContext.request.userPrincipal.name}!</h6>
			</div>
			<ul class="list-unstyled components">
				<li class="inactive">
					<a href="javascript:;" id="account">Il mio account</a>
				</li>
				<c:choose>
					<c:when test="${active eq false}">
						<a data-toggle="modal" data-target="#realtyError">Conferma account</a>
						<div class="modal fade" id="realtyError" tabindex="-1" role="dialog"
							aria-labelledby="realtyErrorLabel" aria-hidden="true">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title" id="realtyErrorLabel">Attenzione!</h5>
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
									<div class="modal-body">
										<strong>Il tuo account non è ancora stato confermato.</strong>
										<p>Controlla la tua casella di posta elettronica, o in alternativa,
											<a href="javascript:;" id="confirm-email">possiamo inviarti una nuova
												email.</a></p>
									</div>
								</div>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<li class="inactive">
							<a href="javascript:;" id="saved-searches">Ricerche salvate</a>
						</li>
						<li>
							<a href="#realtiesSubmenu" data-toggle="collapse" aria-expanded="false"
								class="dropdown-toggle">I miei immobili</a>
							<ul class="collapse list-unstyled" id="realtiesSubmenu">
								<li class="inactive">
									<a href="realty/new">Nuovo Immobile</a>
								</li>
								<li class="inactive">
									<a href="javascript:;" id="realties">Immobili</a>
								</li>
								<li class="inactive">
									<a href="javascript:;" id="drafts">Bozze</a>
								</li>
								<li class="inactive">
									<a href="javascript:;" id="own-ongoing">Affitti in corso</a>
								</li>
								<li class="inactive">
									<a href="javascript:;" id="own-ended">Affitti terminati</a>
								</li>
								<li class="inactive">
									<a href="javascript:;" id="own-checks-r">Spese ricevute</a>
								</li>
								<li class="inactive">
									<a href="javascript:;" id="own-checks-notr">Spese da ricevere</a>
								</li>
							</ul>
						</li>
						<li>
							<a href="#rentsSubmenu" data-toggle="collapse" aria-expanded="false"
								class="dropdown-toggle">I miei affitti</a>
							<ul class="collapse list-unstyled" id="rentsSubmenu">
								<li class="inactive">
									<a href="javascript:;" id="rents-ongoing">Affitti in corso</a>
								</li>
								<li class="inactive">
									<a href="javascript:;" id="rents-ended">Affitti terminati</a>
								</li>
								<li class="inactive">
									<a href="javascript:;" id="rents-checks-r">Spese pagate</a>
								</li>
								<li class="inactive">
									<a href="javascript:;" id="rents-checks-notr">Spese da pagare</a>
								</li>
							</ul>
						</li>
					</c:otherwise>
				</c:choose>
				<li>
					<a data-toggle="modal" data-target="#problemsModal" href="javascript:;" id="problem">Segnala un problema</a>
				</li>
			</ul>
		</nav>

		<div id="overlay">
			<div id="dismiss">
				<i class="fas fa-arrow-left"></i>
			</div>
		</div>


		<!-- Modal -->
		<div id="problemsModal" class="modal fade" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title">Segnala un problema</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<div class="modal-body">
						<form id="segnalation-form">
							<div class="form-group">
								<label for="problemTitle">Titolo Segnalazione</label>
								<input type="title" class="form-control" id="problemTitle"
									aria-describedby="titleHelp" placeholder="Inserisci titolo">
								<small id="titleHelp" class="form-text text-muted">Usa parole chiave per il titolo, ad esempio "Upload immagine inserzione fallita"</small>
							</div>
							<div class="form-group">
								<label for="descriptionText">Descrizione</label>
								<textarea class="form-control" id="descriptionText"
									rows="3"></textarea>
								<small id="descriptionHelp" class="form-text text-muted">Sii il più preciso possibile, per facilitare la comprensione problema.</small>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<div class="row"> 
							<button type="button" id="problemSubmit" class="btn btn-success btn-block">Invia</button>
						</div>
					</div>
				</div>

			</div>
		</div>

		<div id="content" style="background: linear-gradient(180deg, #dee2e6, #dee2e6);">
			<!-- Page Content -->
		</div>

	</div>


	<div class="modal fade" id="okModal" tabindex="-1" aria-labelledby="okModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="okModalLabel">Operazione completata</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<p id="okModalContent"></p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-success" data-dismiss="modal">Conferma</button>
				</div>
			</div>
		</div>
	</div>

	

	<%@include file="includes/footer.jsp" %>
</body>

</html>