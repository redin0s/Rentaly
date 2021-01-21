<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

            <input type="hidden" id="id">
            <input type="hidden" id="user_id">
            <input type="hidden" id="id">

            <div class="row">
                <div class="col-md-6">
                    <div id="map" class="map" style="width:100%;height:400px;" tabindex="0"></div>
                    <script src="../../js/map.js" crossorigin></script>
                    <br>
                </div>
                <div class="col-md-6">
                    <div class="row">
                        <div class="col-md-12">
                            <select class="form-control" id="type">
                                <option>Appartamento</option>
                                <option>Monolocale</option>
                                <option>Bilocale</option>
                                <option>Villa indipendente</option>
                                <option>Villetta a schiera</option>
                                <option>Garage</option>
                                <option>Spazio commerciale</option>
                            </select>
                            <br>
                        </div>

                        <div class="col-md-9">
                            <div class="form-group">
                                <input class="form-control" type="text" id="road" placeholder="Via" value='<c:out value="${realty.display_name}"/>'>
                            </div>
                        </div>

                        <div class="col-md-3">
                            <div class="form-group">
                                <input class="form-control" id="number" placeholder="Civico">
                            </div>
                        </div>

                        <div class="col-md-9">
                            <div class="form-group">
                                <input class="form-control" id="city" placeholder="Città">
                            </div>
                        </div>

                        <div class="col-md-3">
                            <div class="form-group">
                                <input class="form-control" id="cap" placeholder="CAP">
                            </div>
                        </div>

                        <div class="col-md-6">
                            <label for="sqm">Metri quadri:</label>
                            <br>
                            <div class="form-group">
                                <input class="form-control" type="number" id="sqm">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label for="inq">Numero massimo di inquilini:</label>
                            <br>
                            <div class="form-group">
                                <input class="form-control" type="number" id="inq">
                            </div>
                            <br>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <!--div class="row">
                <div class="col-md-6">
                    <p> images </p>
                </div>
                <div class="col-md-6">
                    <label for="desc">Descrizione:</label>
                    <div class="form-group">
                        <textarea class="form-control" id="desc" rows="5"></textarea>
                    </div>
                </div>
            </div-->
            <div class="row">
                <div class="col-sm-4">
                    <button class="btn btn-primary btn-block" type="submit" id="undo">Annulla</button>
                </div>
                <div class="col-sm-4">
                    <button class="btn btn-primary btn-block" type="submit" id="savedraft">Salva come bozza</button>
                </div>
                <div class="col-sm-4">
                    <button class="btn btn-primary btn-block" type="submit" id="save">Salva</button>
                </div>
            </div>
        </form>
    </div>
</body>

</html>