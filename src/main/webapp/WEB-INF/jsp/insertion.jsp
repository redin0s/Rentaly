<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<%@include file="includes/import.jsp" %>
	<title>Inserzione - Rentaly</title>
</head>

<body>
	<%@include file="includes/header.jsp" %>
	<div class="insertion">
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-3"></div>
				<div class="col-md-4">
					<h1>${insertion.realty.display_name}</h1>
				</div>
				<div class="col-md-2">
					<h1>Cost: ${insertion.cost} EUR</h1>
				</div>
				<div class="col-md-3"></div>
			</div>

			<div class="row">
				<div class="col-md-3"></div>
				<div class="col-md-6">
					<div class="carousel slide" data-ride="carousel" id="carousel-1">
						<div class="carousel-inner" role="listbox">
							<div class="carousel-item active"><img class="w-100 d-block"
									src="${insertion.picture_1}" alt="Slide Image">
							</div>
							<div class="carousel-item"><img class="w-100 d-block"
									src="${insertion.picture_2}" alt="Slide Image">
							</div>
							<div class="carousel-item"><img class="w-100 d-block"
									src="${insertion.picture_3}" alt="Slide Image">
							</div>
						</div>
						<div><a class="carousel-control-prev" href="#carousel-1" role="button" data-slide="prev"><span
									class="carousel-control-prev-icon"></span><span
									class="sr-only">Previous</span></a><a class="carousel-control-next"
								href="#carousel-1" role="button" data-slide="next"><span
									class="carousel-control-next-icon"></span><span class="sr-only">Next</span></a>
						</div>
						<ol class="carousel-indicators">
							<li data-target="#carousel-1" data-slide-to="0" class="active"></li>
							<li data-target="#carousel-1" data-slide-to="1"></li>
							<li data-target="#carousel-1" data-slide-to="2"></li>
						</ol>
					</div>
				</div>

			</div>

			<div class="row">
				<div class="col-md-4">
					<p>Square Meters: ${insertion.realty.square_meters}</p>
				</div>
				<div class="col-md-4">
					<p>Max Holders: ${insertion.realty.max_holders}</p>
				</div>
				<div class="col-md-4">
					<p>Proprietario: ${insertion.realty.owner.email}</p>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<textarea class="form-control" id="desc" rows="5" readonly >${insertion.description}</textarea>
				</div>
			</div>

		</div>
	</div>
</body>

</html>