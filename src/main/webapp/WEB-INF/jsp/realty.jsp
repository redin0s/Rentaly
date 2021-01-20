<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<%@include file="includes/import.jsp" %>
    <title>Realty</title>
    <script src="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.5.0/build/ol.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.5.0/css/ol.css">

</head>

<body>
    <%@include file="includes/header.jsp" %>
    <br>
    <div class="container">
        <form method="post" action="updateRealty" id="realty">
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
                    <div class="form-group">
                        <p id="address">indirizzo</p>
                    </div>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col-md-6">
                    <div id="map" class="map" style="width:100%;height:300px;" tabindex="0"></div>
                    <script src="../../js/map.js" crossorigin></script>
                </div>
                <div class="col-md-6">
                    <div class="row">
                        <div class="col-md-12">
                            <label for="sqm">Metri quadri:</label>
                            <br>
                            <div class="form-group">
                                <input type="number" id="sqm">
                            </div>
                            <br>
                        </div>

                        <div class="col-md-12">
                            <label for="inq">Numero massimo di inquilini:</label>
                            <br>
                            <div class="form-group">
                                <input type="number" id="inq">
                            </div>
                            <br>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col-md-6">
                    <p> images </p>
                </div>
                <div class="col-md-6">
                    <label for="desc">Descrizione:</label>
                    <div class="form-group">
                        <textarea class="form-control" id="desc" rows="5"></textarea>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <p> exit </p>
                </div>
                <div class="col-md-6">
                    <p> save </p>
                </div>
            </div>
        </form>
    </div>
</body>

</html>