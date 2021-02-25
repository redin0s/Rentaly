<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<script src="/js/realties.js"></script>

<ul class="list-group">
    <c:if test="${empty realties}">
        <div class="card list-group-item list-group-item-action justify-content-between align-items-center">
            <div class="card-body">
                <div class="row">
                    <h4>Non hai ancora creato un immobile.</h4>
                    <a class="btn btn-success justify-content-center m-1" href="/realty/new">Nuovo</a>
                    <a class="btn btn-success justify-content-center m-1" id="drafts" href="javascript:;">Bozze</a>
                </div>
            </div>
        </div>
    </c:if>
    <c:forEach items="${realties}" var="r">
        <div class="card list-group-item list-group-item-action justify-content-between align-items-center">
            <div class="card-body">
                <div class="row">
                    <div class="col-md-2">
                        <c:choose>
                            <c:when test = "${r.insertion == null}">
                                <img style="height: 150px;">
                            </c:when>
                            <c:otherwise>
                                <img style="height: 150px;">
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="col-md-4">
                        <a href="realty/${r.id}" class="stretched-link"></a>

                        <h4 id="title">${r.type} a ${r.city}</h4>
                        <h6 class="text-muted mb-2" id="street">${r.address}</h6>
                        <c:choose>
                            <c:when test = "${r.max_holders > 0}">
                                <h6 class="text-muted mb-2" id="max_holders">Affittuari:</h6>
                                <c:choose>
                                    <c:when test = "${r.max_holders >= 8}">
                                        <a>${r.current_holders}/${r.max_holders}</a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var = "i" begin="1" end="${r.max_holders}">
                                            <c:choose>
                                                <c:when test = "${i <= r.current_holders}">
                                                    <i class="fa fa-male" aria-hidden="true"></i>
                                                </c:when>
                                                <c:otherwise>
                                                    <i class="fa fa-male " style="color: LightGray;"></i>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>                            
                            </c:when>
                            <c:otherwise>
                                <h6 class="text-muted mb-2" id="max_holders">Nessun limite di affittuari.</h6>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="col-md-4">
                        <c:choose>
                            <c:when test = "${r.insertion eq null}">
                                <h4>Nessuna inserzione per questo immobile.</h4>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test = "${r.insertion.is_visible eq false || r.draft eq true}">
                                        <h4>Inserzione nascosta.</h4>
                                    </c:when>
                                    <c:otherwise>
                                        <h4>Inserzione visibile.</h4>
                                    </c:otherwise>
                                </c:choose>
                                <div class="btn btn-danger justify-content-center m-1" id="deleteInsertion" href="javascript:;">Elimina inserzione</div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="col-md-2">
                        <c:choose>
                            <c:when test="${r.draft eq false}">
                                <div class="btn btn-success justify-content-center m-1" data-toggle="modal" data-target="#addHolderModal" onclick="setSelected(${r.id});">Aggiungi Inquilino</div>
                                <br>
                                <div class="btn btn-danger justify-content-center m-1" id="deleteRealty" href="javascript:;">Elimina immobile</div>
                            </c:when>

                            <c:otherwise>
                                <div class="btn btn-danger justify-content-center m-1" id="deleteRealty" href="javascript:;">Elimina bozza</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</ul>

<div class="modal fade" id="addHolderModal" tabindex="-1" role="dialog" aria-labelledby="addHolderModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addHolderModalLabel">Aggiungi inquilino</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="addHolderForm">
                    <div class="form-group row">
                        <div class="col-sm-3">
                            <p>Email:</p>
                            <p>Costo:</p>
                            <p>Inizio:</p>
                            <p>Mesi:</p>
                        </div>
                        <div class="col-sm-9">
                            <input type="email" class="form-control" id="rent-user_email">
                            <input type="number" min="1" class="form-control" id="rent-cost">
                            <input class="form-control it-date-datepicker" id="rent-start_date" type="text">
                            <input type="number" min="0" class="form-control" id="rent-duration">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" id="addHolder">Salva</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="infoModal" tabindex="-1" aria-labelledby="infoModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="infoModalLabel">Info</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p id="infoModalContent"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal">Conferma</button>
            </div>
        </div>
    </div>
</div>

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
                <p id="infoModalContentDelete"></p>
            </div>
            <div class="modal-footer">
                <button type="button" id="deleteConfirm" class="btn btn-danger" data-dismiss="modal">Conferma</button>
            </div>
        </div>
    </div>
</div>