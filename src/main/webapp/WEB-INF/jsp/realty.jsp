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
        <form id="realty">

            <input type="hidden" id="id" value='${realty.id}'>
            <input type="hidden" id="latitude" value='${realty.longitude}'>
            <input type="hidden" id="longitude" value='${realty.longitude}'>

            <div class="row">
                <div class="col-md-6">
                    <div id="map" class="map" style="width:100%;height:400px;" tabindex="0"></div>
                    <script type='text/javascript' src='https://www.bing.com/api/maps/mapcontrol?callback=GetMap&key=AghYmNhfhWJ5VGkmfMOX2eg63AmJNXX6dR4DpC7-q6YjCgNGqrzOzJyPUK3OQbqB&setMkt=it-IT&setLang=it' async defer></script>
                    <script src="../../js/map.js" crossorigin async defer></script>
                    <br>
                </div>
                <div class="col-md-6">
                    <div class="row">
                        <div class="col-12">
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

                        <div class="col-12">
                            <div class="form-group">
                                <input class="form-control" type="text" id="address" placeholder="Via">
                            </div>
                        </div>

                        <div class="col-sm-9">
                            <div class="form-group">
                                <input class="form-control" id="city" placeholder="Città" value="${realty.city}">
                            </div>
                        </div>

                        <div class="col-sm-3">
                            <div class="form-group">
                                <input class="form-control" id="cap" placeholder="CAP">
                            </div>
                        </div>

                        <div class="col-lg-6">
                            <label for="sqm">Metri quadri:</label>
                            <br>
                            <div class="form-group">
                                <input class="form-control" type="number" min="1" id="square_meters" value='${realty.square_meters}'>
                            </div>
                        </div>
                        <div class="col-lg-6">
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
            <div class="row">
                <div class="col-12">
                    <input type="checkbox" class="form-check-input" id="is_visible">
                    <label class="form-check-label" for="is_visible">Inserzione visibile pubblicamente</label>
                </div>

                
                <div class="col-lg-6">
                    <p> images </p>
                </div>
                <div class="col-lg-4">
                    <label for="desc">Descrizione:</label>
                    <div class="form-group">
                        <textarea class="form-control" id="description" rows="5"></textarea>
                    </div>
                </div>
                <div class="col-lg-2">
                    <label for="desc">Costo mensile:</label>
                    <div class="form-group">
                        <input class="form-control" type="number" min="1" id="cost" value='${realty.insertion.cost}'>
                    </div>
                </div>
            </div>
            <div class="row">
                <c:choose>
                    <c:when test="${realty.draft == true}">
                        <button class="btn btn-danger btn-block col-4" type="submit" id="undo" href="myRealties">Annulla</button>
                        <button class="btn btn-light btn-block col-4" type="button" id="savedraft">Salva bozza</button>
                        <button class="btn btn-primary btn-block col-4" type="button" id="save">Salva</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-danger btn-block col-6" type="submit" id="undo" href="myRealties">Annulla</button>
                        <button class="btn btn-primary btn-block col-6" type="button" id="save">Salva</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </form>
    </div>
</body>

</html>