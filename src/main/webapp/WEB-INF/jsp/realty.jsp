<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<%@include file="includes/import.jsp" %>
	<title>Realty</title>
</head>

<body>
    <%@include file="includes/header.jsp" %>
    <br>
    <div class="container">
        <form method="post" action="updateRealty">
            <div class="row">
                <div class="col-md-6">
                    <select class="form-control" id="type">
                        <option>Appartamento</option>
                        <option>Monolocale</option>
                        <option>Bilocale</option>
                        <option>Villa indipendente</option>
                        <option>Villetta a schiera</option>
                        <option>Garage</option>
                        <option>Spazio commerciale</option>
                    </select>
                </div>
                <div class="col-md-6">
                    <p>a <p id="town">Roma</p></p> 
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <p id="address">via Italia, 420</p>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <iframe allowfullscreen="false" frameborder="0" src="https://www.google.com/maps/embed/v1/place?key=AIzaSyDVlfpE5wladOSGnBpT_hkX3UbBiaeCEgg&amp;q=Rome%2C+Italy&amp;zoom=5" width="100%" height="400">
                    </iframe>
                </div>
                <div class="col-md-6">
                    <div class="row">
                        <div class="col-md-12">
                            <label for="sqm">Metri quadri:</label>
                            <br>
                            <input type="number" id="sqm">
                            <br>
                        </div>
                        <div class="col-md-12">
                            <label for="inq">Numero massimo di inquilini:</label>
                            <br>
                            <input type="number" id="inq">
                            <br>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12"></div>
            </div>
            <div class="row">
                <div class="col-md-6">

                </div>
                <div class="col-md-6">

                </div>
            </div>
            <div class="row">
                <div class="col-md-6">

                </div>
                <div class="col-md-6">

                </div>
            </div>
        </form>
    </div>
</body>

</html>