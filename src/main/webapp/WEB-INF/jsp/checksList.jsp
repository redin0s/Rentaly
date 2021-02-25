<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<script src="/js/checks.js"></script>
<ul class="list-group">
    <c:if test="${empty checks}">
        <div class="card list-group-item list-group-item-action justify-content-between align-items-center">
            <div class="card-body">
                <div class="row">
                    <c:choose>
                        <c:when test="${isPaid eq true}">
                            <h4>Non c'è nessuna spesa pagata.</h4>
                        </c:when>
                        <c:otherwise>                           
                            <h4>Non c'è nessuna spesa da pagare.</h4>
                        </c:otherwise>
                    </c:choose>
                </div>

                <c:if test="${owner eq true}">
                    <div class="row">
                        <a class="btn btn-success" id="realties-rents" href="javascript:;">Affitti</a>
                    </div>
                </c:if>
            </div>
        </div>
    </c:if>

    <c:choose>
        <c:when test="${owner eq true}">
            <input type="hidden" value="1" id="owner-check">
        </c:when>
        <c:otherwise>
            <input type="hidden" value="0" id="owner-check">
        </c:otherwise>
    </c:choose>

    <c:forEach items="${checks}" var="c">
        <div class="card list-group-item list-group-item-action justify-content-between align-items-center">
            <div class="card-body">
                <div class="row">
                    <div class="col-xs-4 col-md-2" style="color: red;">    
                        <c:choose>  
                            <c:when test="${c.check_type eq 'Luce'}">
                                <p><i class="fas fa-lightbulb"></i> Luce</p>
                            </c:when>
                            <c:when test="${c.check_type eq 'Acqua'}">
                                <p><i class="fas fa-water"></i> Acqua</p>
                            </c:when>
                            <c:when test="${c.check_type eq 'Gas'}">
                                <p><i class="fas fa-burn"></i> Gas</p>
                            </c:when>                        
                            <c:when test="${c.check_type eq 'Affitto'}">
                                <p><i class="fas fa-file-contract"></i> Affitto</p>
                            </c:when>
                             <c:when test="${c.check_type eq 'Riparazione'}">
                                <p><i class="fas fa-tools"></i> Riparazione</p>
                            </c:when>
                            <c:when test="${c.check_type eq 'Tassa'}">
                                <p><i class="fas fa-check-square"></i> Tassa</p>
                            </c:when>
                            <c:when test="${c.check_type eq 'Condominio'}">
                                <p><i class="fas fa-home"></i> Condominio</p>
                            </c:when>
                        </c:choose>
                    </div>
                    
                    <div class="col-xs-8 col-md-4">
                        <h6>
                            ${c.check_type}
                            <br>
                            ${c.cost} € entro il ${c.expire}
                        </h6>
                    </div>       
                    
                    <div class="col-md-4">
                        <h6>
                            ${c.rent.realty.type} a ${c.rent.realty.city}
                            <br>
                            <c:choose>
                                <c:when test="${owner eq true}">
                                    inquilino <a href="mailto:{$c.rent.holder.email}">${c.rent.holder.email}</a>
                                </c:when>
                                <c:otherwise>                           
                                    proprietario <a href="mailto:${c.rent.realty.owner.email}">${c.rent.realty.owner.email}</a>
                                </c:otherwise>
                            </c:choose>
                        </h6>
                    </div>
                    
                    <div class="col-md-2">
                        <c:choose>
                            <c:when test="${isPaid eq true}">
                                <h4>Pagata</h4>
                            </c:when>
                            <c:otherwise>
                                <a id="paybutton" onclick="setSelected(${c.id});" class="btn btn-success" href="javascript:;">Paga</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                
                </div>
            </div>
        </div>
    </c:forEach>
</div>