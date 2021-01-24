<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
	<%@include file="includes/import.jsp" %>
    <title>Immobile - Rentaly</title>
</head>

<body>
    <%@include file="includes/header.jsp" %>
    <script src="../../js/realty.js"></script> <!--change later-->
    <br>
    <div class="container">
        <form method="post" action="updateRealty" id="realty">

            <input type="hidden" id="id">
            <input type="hidden" id="display_name" value='${realty.display_name}'>
            <input type="hidden" id="latitude" value='${realty.longitude}'>
            <input type="hidden" id="longitude" value='${realty.longitude}'>

            <div class="row">
                <div class="col-md-6">
                    <div id="map" class="map" style="width:100%;height:400px;" tabindex="0"></div>
                    <script src="../../js/bingmaps/bingmaps.js"></script>
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
                                <input class="form-control" type="text" id="road" placeholder="Via">
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
                                <input class="form-control" type="number" id="square_meters" value='${realty.square_meters}'>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label for="inq">Numero massimo di inquilini:</label>
                            <br>
                            <div class="form-group">
                                <input class="form-control" type="number" id="max_holders" value='${realty.max_holders}'>
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
                <button class="btn btn-danger btn-block col-4" type="submit" id="undo" href="/realties">Annulla</button>
                <button class="btn btn-light btn-block col-4" type="submit" id="savedraft">Salva bozza</button>
                <button class="btn btn-primary btn-block col-4" type="submit" id="save">Salva</button>
            </div>
        </form>
    </div>
</body>

</html>