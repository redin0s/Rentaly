<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<script src="/js/rents.js"></script>

<ul class="list-group">
    <c:if test="${empty rents}">
        <div class="card list-group-item list-group-item-action justify-content-between align-items-center">
            <div class="card-body">
                <div class="row col-12 justify-content-center">
                    <c:choose>
                        <c:when test="${ongoing eq true}">
                            <h4>Nessun affitto in corso.</h4>
                            <c:if test="${owner eq true}">
                                </div>
                                <div class="row col-12 justify-content-center">
                                    <a class="btn btn-success" id="realties" href="javascript:;">Immobili</a>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <h4>Nessun affitto terminato.</h4>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </c:if>
    <c:forEach items="${rents}" var="r">
        <div class="card list-group-item list-group-item-action justify-content-between align-items-center">
            <div class="card-body">
                <div class="row">
                    <div class="col-md-5">
                        <h4 id="title">${r.realty.type} a ${r.realty.city}</h4>
                        <h6 class="text-muted mb-2" id="street">${r.realty.address}</h6>
                        <c:choose>
                            <c:when test="${owner eq true}">
                                <h6 class="text-muted mb-2" id="holder">Inquilino: <a href="mailto:${r.holder.email}">${r.holder.email}</a></h6>
                            </c:when>
                            <c:otherwise>
                                <h6 class="text-muted mb-2" id="holder">Proprietario: <a href="mailto:${r.realty.owner.email}">${r.realty.owner.email}</a></h6>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-md-5">
                        <h6 class="text-muted mb-2" id="cost">Affitto mensile: ${r.cost}</h6>
                        <h6 class="text-muted mb-2" id="start">Data di inizio: ${r.start}</h6>
                        <h6 class="text-muted mb-2" id="end">Data di fine: ${r.end}</h6>
                    </div>
                    <c:if test="${owner eq true && ongoing eq true}">
                        <div class="col-md-2">
                            <button class="btn btn-danger m-1" id="terminateRent" onclick="setSelected(${r.id});">Termina affitto</button>
                            <button class="btn btn-success m-1" data-toggle="modal" data-target="#addCheckModal" onclick="setSelected(${r.id});">Crea spesa</button>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </c:forEach>
</ul>

<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="infoModalLabel">Attenzione!</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p id="infoModalContentDelete">Vuoi davvero terminare questo affitto?</p>
            </div>
            <div class="modal-footer">
                <button type="button" id="deleteConfirm" class="btn btn-danger" data-dismiss="modal">Conferma</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="addCheckModal" tabindex="-1" role="dialog" aria-labelledby="addCheckModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addCheckModalLabel">Crea spesa</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="addCheckForm">
                    <div class="form-group row">
                        <div class="col-sm-3">
                            <p>Tipologia:</p>
                            <p>Costo:</p>
                            <p>Già pagata:</p>
                            <p>Scadenza:</p>
                        </div>
                        <div class="col-sm-9">
                            <select class="form-control" id="check-type">
                                <option>Luce</option>
                                <option>Acqua</option>
                                <option>Gas</option>
                                <option>Affitto</option>
                                <option>Riparazione</option>
                                <option>Tassa</option>
                                <option>Condominio</option>
                            </select>
                            <input type="number" min="1" class="form-control" id="check-cost"> 
                            <select class="form-control" id="check-paid">
                                <option>No</option>
                                <option>Si</option>
                            </select>
                            <input class="form-control it-date-datepicker" id="check-end_date" type="text">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" id="saveCheck">Salva</button>
            </div>
        </div>
    </div>
</div>