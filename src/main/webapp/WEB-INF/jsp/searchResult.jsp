<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="../../js/holder.js"></script>


<input type="hidden" id="latitude" value="${latitude}">
<input type="hidden" id="longitude" value="${longitude}">
<ul class="list-group">
    <sec:csrfInput />
    <div class="card list-group-item list-group-item-action justify-content-between align-items-center">
        <div class="card-body">
            <div class="row justify-content-center">
                <c:if test = '${pageContext.request.userPrincipal.authenticated == true}'>
                    <a class="btn btn-success" href="#" id="save-search">Salva questa ricerca</a>
                </c:if>
            </div>
        </div>
    </div>
    
    <c:set var="count" value="0"/> 

    <c:forEach items="${realties}" var="r">
        <c:if test="${(r.realty.insertion.is_visible eq true) && ((r.realty.max_holders > r.realty.current_holders) eq (r.realty.max_holders > 0)) && (maxprice > r.realty.insertion.cost) && (r.realty.insertion.cost > minprice) && (r.realty.insertion.cost > 0)}">
            <c:set var="count" value="${count + 1}"/> 
            
            <div class="card list-group-item list-group-item-action justify-content-between align-items-center">
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-2">
                            <c:choose>
                                <c:when test = "${empty r.files}">
                                    <img style="object-fit: cover; max-width:150; max-height: 100px;" src="/images/casadefault.png">
                                </c:when>
                                <c:otherwise>
                                    <img style="object-fit: cover; max-width:150; max-height: 100px;" src="/${r.files[0]}" alt="/images/casadefault.png">
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="col-md-4">
                            <!--a href="realty/${r.realty.id}" class="stretched-link"> TODO ADD LINK TO INSERTION / MODAL FOR PICS </a-->

                            <h4 id="title">${r.realty.type} a ${r.realty.city}</h4>
                            <h6 class="text-muted mb-2" id="street">${r.realty.address}</h6>
                            <h6 class="text-muted mb-2" id="holder">Proprietario: <a href="mailto:${r.realty.owner.email}">${r.realty.owner.email}</a></h6>

                        </div>

                        <div class="col-md-3">
                            <h6 class="text-muted mb-2" id="sqm">Metri quadri: ${r.realty.square_meters}</h6>
                            <br>
                            <c:choose>
                                <c:when test = "${r.realty.max_holders > 0}">
                                    <h6 class="text-muted mb-2" id="max_holders">Affittuari:</h6>
                                    <c:forEach var = "i" begin="1" end="${r.realty.max_holders}">
                                        <c:choose>
                                            <c:when test = "${i <= r.realty.current_holders}">
                                                <i class="fa fa-male" aria-hidden="true"></i>
                                            </c:when>
                                            <c:otherwise>
                                                <i class="fa fa-male " style="color: LightGray;"></i>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <h6 class="text-muted mb-2" id="max_holders">Nessun limite di affittuari.</h6>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="col-md-3">
                            <h4>Costo mensile: €${r.realty.insertion.cost}</h4>
                            <c:choose>
                                <c:when test="${empty r.realty.insertion.description}">
                                    <h4>Nessuna descrizione</h4>
                                </c:when>
                                <c:otherwise>
                                    <h4>Descrizione:</h4>
                                    <br>
                                    <h6 class="text-muted mb-2" id="desc">${r.realty.insertion.description}</h6>
                                </c:otherwise>
                            </c:choose>

                        </div>
                        
                        
                    </div>
                </div>
            </div>
        </c:if>
    </c:forEach>

    <c:if test="${count == 0}">
        <div class="card list-group-item list-group-item-action justify-content-between align-items-center">
            <div class="card-body">
                <div class="row justify-content-center">
                    <h4>Nessun risultato per questa ricerca.</h4>
                </div>
            </div>
        </div>
    </c:if>
</ul>