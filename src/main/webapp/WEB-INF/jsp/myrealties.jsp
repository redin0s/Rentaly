<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page errorPage="error.jsp" %> 
<%-- <%@ page import="java.util.List"%> --%>
<!DOCTYPE html>
<html>
	<head>
        <%@include file="includes/import.jsp" %>
		<title>I miei Immobili - Rentaly</title>
	</head>

	<body>
		<%@include file="includes/header.jsp" %>


        <ul class="nav nav-tabs justify-content-center" id="tabList" role="tablist">
            <li class="nav-item">
                <a class="nav-link active" id="realties-tab" data-toggle="tab" href="#realties" role="tab" aria-controls="realties" aria-selected="true">Immobili</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="drafts-tab" data-toggle="tab" href="#drafts" role="tab" aria-controls="drafts" aria-selected="false">Bozze</a>
            </li>
             <li class="nav-item">
                <a class="nav-link" id="rents-tab" data-toggle="tab" href="#" role="tab" aria-controls="rents" aria-selected="false">Affitti</a>
            </li>
                
                
            <%-- <!--    <li class="nav-item">
                <a class="nav-link" id="expenses-tab" data-toggle="tab" href="#" role="tab" aria-controls="expenses" aria-selected="false">Spese</a>
            </li> --> --%>
        </ul>
        <div class="tab-content" id="tabContent">

            <div class="tab-pane fade show active" id="realties" role="tabpanel" aria-labelledby="realties-tab">
                
                <div class="card list-group-item list-group-item-action justify-content-between align-items-center">
                    <a class="btn btn-primary justify-content-center" role="button" href="realty/new">Nuovo Immobile</a>
                </div>

                <ul class="list-group">
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
                                                <c:forEach begin="1" end="${r.current_holders}">
                                                    <i class="fa fa-male" aria-hidden="true"></i>
                                                </c:forEach>
                                                <c:forEach begin="${current_holders}+1" end="${r.max_holders}">
                                                    <i class="fa fa-male " style="color: LightGray;"></i>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <h6 class="text-muted mb-2" id="max_holders">Nessun limite di affittuari.</h6>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <div class="col-md-4">
                                        <c:choose>
                                            <c:when test = "${r.insertion == null}">
                                                <h4>Nessuna inserzione per questo immobile.</h4>
                                            </c:when>
                                            <c:otherwise>
                                                <c:choose>
                                                    <c:when test = "${r.insertion.is_visible == false}">
                                                        <h4>Inserzione nascosta.</h4>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <h4>Inserzione visibile.</h4>
                                                    </c:otherwise>
                                                </c:choose>
                                                <div class="btn btn-primary justify-content-center" id="deleteInsertion" href="#">Elimina inserzione</div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <div class="col-md-2">
                                        
                                        <div class="btn btn-primary justify-content-center" data-toggle="modal" data-target="#addHolderModal">Aggiungi Inquilino</div>
                                        
                                        <div class="modal fade" id="addHolderModal" tabindex="-1" role="dialog" aria-labelledby="addHolderModalLabel" aria-hidden="true">
                                            <div class="modal-dialog" role="document">
                                              <div class="modal-content">
                                                <div class="modal-header">
                                                  <h5 class="modal-title" id="addHolderModalLabel">Modal title</h5>
                                                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                  </button>
                                                </div>
                                                <div class="modal-body">
                                                  <form id="addHolderForm">
                                                    <div class="form-group">
                                                        <label for="recipient-name" class="col-form-label">Email:</label>
                                                        <input type="email" class="form-control" id="rent-user_email">
                              
                                                        <label for="message-text" class="col-form-label">Costo:</label>
                                                        <input type="number" min="1" class="form-control" id="rent-cost">
                              
                                                        <label for="start">Start date:</label>
                                                        <input type="date" id="start" name="rent-start_date">

                                                        <label for="message-text" class="col-form-label">Durata (mesi):</label>
                                                        <input type="number" min="0" class="form-control" id="rent-duration">

                                                        <input type="hidden" id="rent-realty" value="${r.id}">
                                                    </div>
                                                  </form>
                                                </div>
                                                <div class="modal-footer">
                                                  <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                                  <button type="button" class="btn btn-primary" id="addHolder">Save changes</button>
                                                </div>
                                              </div>
                                            </div>
                                          </div>
                
                                          <div class="btn btn-primary justify-content-center" id="deleteRealty" href="#">Elimina immobile</div>
                                        
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                </ul>

            </div>

            <div class="tab-pane fade show" id="drafts" role="tabpanel" aria-labelledby="drafts-tab">
                
                <div class="card list-group-item list-group-item-action justify-content-between align-items-center">
                    <a class="btn btn-primary justify-content-center" role="button" href="realty/new">Nuovo Immobile</a>
                </div>

                <ul class="list-group">
                    <c:forEach items="${drafts}" var="r">
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
                                                <c:forEach begin="1" end="${r.max_holders}">
                                                    <i class="fa fa-male " style="color: LightGray;"></i>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <h6 class="text-muted mb-2" id="max_holders">Nessun limite di affittuari.</h6>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <div class="col-md-4">
                                        <c:choose>
                                            <c:when test = "${r.insertion == null}">
                                                <h4>Nessuna inserzione per questo immobile.</h4>
                                            </c:when>
                                            <c:otherwise>
                                                <h4>Inserzione non visibile.</h4>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <div class="col-md-2">
                                        <div class="btn btn-primary justify-content-center" id="deleteRealty" href="#">Elimina immobile</div>
                                        
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                </ul>

            </div>

            <div class="tab-pane fade show" id="rents" role="tabpanel" aria-labelledby="rents-tab">
                <ul class="list-group">
                    <c:forEach items="${rents}" var="r">
                        <div class="card list-group-item list-group-item-action justify-content-between align-items-center">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-md-6">
                                        <h4 id="title">${r.realty.type} a ${r.realty.city}</h4>
                                        <h6 class="text-muted mb-2" id="street">${r.realty.address}</h6>
                                        <h6 class="text-muted mb-2" id="holder">Inquilino: ${r.holder.email}</h6>
                                    </div>
                                    <div class="col-md-6">
                                        <br>
                                        <h6 class="text-muted mb-2" id="cost">Affitto mensile: ${r.cost}</h6>
                                        <h6 class="text-muted mb-2" id="cost">Data di inizio: ${r.start_date}</h6>
                                        <h6 class="text-muted mb-2" id="cost">Data di fine: ${r.end_date}</h6>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </ul>

            </div>
            
        </div>


	</body>

</html>