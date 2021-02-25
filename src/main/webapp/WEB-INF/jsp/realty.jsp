<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>

<head>
	<%@include file="includes/import.jsp" %>
    <title>Immobile - Rentaly</title>
    <sec:csrfMetaTags />
</head>

<body>
    <%@include file="includes/header.jsp" %>
    <script src="/js/realty.js"></script> <!--change later-->
    <br>
    <div class="container">
        <form id="realty" enctype="multipart/form-data">
            <sec:csrfInput />
            <input type="hidden" id="id" value='${realty.id}'>
            <input type="hidden" id="latitude" value='${realty.latitude}'>
            <input type="hidden" id="longitude" value='${realty.longitude}'>

            <div class="row">
                <div class="col-md-6">
                    <div id="map" class="map" style="width:100%;height:400px;" tabindex="0"></div>
                    <script type='text/javascript' src='https://www.bing.com/api/maps/mapcontrol?callback=GetMap&key=AghYmNhfhWJ5VGkmfMOX2eg63AmJNXX6dR4DpC7-q6YjCgNGqrzOzJyPUK3OQbqB&setMkt=it-IT&setLang=it' async defer></script>
                    <script src="/js/map.js" crossorigin async defer></script>
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
                                <input class="form-control" type="text" id="address" placeholder="Via" value="${realty.address}">
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
                                <input class="form-control" type="number" min="1" id="square_meters" value='${realty.square_meters}' placeholder="0">
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <label for="inq">Numero massimo di inquilini:</label>
                            <br>
                            <div class="form-group">
                                <input class="form-control" type="number" id="max_holders" value='${realty.max_holders}' placeholder="Nessuno">
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

                
                <div class="col-lg-6 text-center">
                        <button class="btn btn-danger m-2" id="deleteImage">Elimina</button>
                        <div id="carouselImages" class="carousel slide minicarousel m-2" data-ride="carousel">
                        <div class="carousel-inner">
                            <c:forEach items="${images}" var="img" varStatus="loop">
                            <div class="carousel-item <c:if test='${loop.index == 0}'>active</c:if>">
                            <img class="d-block w-100" src="/${img}" alt="First slide">
                            </div>
                            </c:forEach>
                        </div>
                        <a class="carousel-control-prev" href="#carouselImages" role="button" data-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="carousel-control-next" href="#carouselImages" role="button" data-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="sr-only">Next</span>
                        </a>
                        </div>
                    <input type='file' id='files' name='files' class='m-2' multiple>
                </div>
                <div class="col-lg-4">
                    <label for="desc">Descrizione:</label>
                    <div class="form-group">
                        <textarea class="form-control" id="description" rows="5">${realty.insertion.description}</textarea>
                    </div>
                </div>
                <div class="col-lg-2">
                    <label for="desc">Costo mensile:</label>
                    <div class="form-group">
                        <input class="form-control" type="number" min="1" id="cost" value='${realty.insertion.cost}'>
                    </div>
                </div>
            </div>
            <div class="row button-row">
                <c:choose>
                    <c:when test="${realty.draft == true}">
                        <button class="btn btn-danger btn-block col m-2" id="undo">Annulla</button>
                        <button class="btn btn-secondary btn-block col m-2" type="button" id="savedraft">Salva bozza</button>
                        <button class="btn btn-success btn-block col m-2" type="button" id="save">Salva</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-danger btn-block col m-2" id="undo">Annulla</button>
                        <div class="col"></div>
                        <button class="btn btn-success col m-2" type="button" id="save">Salva</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </form>
    </div>

    <div class="modal fade" id="undoModal" tabindex="-1" role="dialog" aria-labelledby="undoModalLabel" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="undoModalLabel">Confermi</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Chiudi">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <a>Confermi di voler annullare?</a>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
            <button type="button" class="btn btn-success" id="account">Conferma</button>
          </div>
        </div>
      </div>
    </div>

    <div class="modal fade" id="saveModal" tabindex="-1" role="dialog" aria-labelledby="saveModalLabel" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="saveDraftModalLabel">Salvataggio completato</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Chiudi">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <p id="infoContent"> </p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-success" data-dismiss="modal" id="account">Chiudi</button>
          </div>
        </div>
      </div>
    </div>

    <%@include file="includes/footer.jsp" %>
</body>

</html>