<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%-- <%@ page import="java.util.List"%> --%>
<!DOCTYPE html>
<html>
	<head>
		<title>I miei Immobili - Rentaly</title>
	</head>

	<body>
		<%@include file="includes/header.jsp" %>

		<ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item">
              	<a class="nav-link active" id="realties-tab" data-toggle="tab" href="#realties" role="tab" aria-controls="realties" aria-selected="true">Immobili</a>
            </li>
            <li class="nav-item">
              	<a class="nav-link" id="insertions-tab" data-toggle="tab" href="#insertions" role="tab" aria-controls="insertions" aria-selected="false">Annunci</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" id="expenses-tab" data-toggle="tab" href="#expenses" role="tab" aria-controls="expenses" aria-selected="false">Spese</a>
		  </li>
    	</ul>
        <div class="tab-content" id="tabContent">

            <div class="tab-pane fade show active" id="realties" role="tabpanel" aria-labelledby="realties-tab">
                
                <ul class="list-group">
                    <c:forEach items="${realtiesList}" var="r">
                        <div class="card list-group-item list-group-item-action justify-content-between align-items-center" href="realties/{r.id}">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-md-6">
                                        <h4 id="title">${r.type} a ${r.latitude}</h4>
                                        <h6 class="text-muted mb-2" id="street">${r.longitude}</h6>
                                    </div>
                                    <div class="col-md-6">
										<c:choose>
                    						<c:when test = "${r.max_holders > 0}">
												<!-- TODO current_holders / max_holders affittuari-->
												<h6 class="text-muted mb-2" id="max_holders">Massimo ${r.max_holders} affittuari.</h6>
											</c:when>
											<c:otherwise>
												<h6 class="text-muted mb-2" id="max_holders">Nessun limite di affittuari.</h6>
											</c:otherwise>
										</c:choose>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </ul>

            </div>
            <div class="tab-pane fade" id="insertions" role="tabpanel" aria-labelledby="insertions-tab">
                
                <ul class="list-group">
                    <c:forEach items="${insertionList}" var="i">
                        <div class="card list-group-item list-group-item-action justify-content-between align-items-center" href="realties/{i.id}">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-md-6 col-lg-3">
                                        <img style="background-color: #000aff; height: 150px;" src="${i.picture_1}">
                                    </div>
                                    <div class="col-md-6 col-lg-6">
                                        <h4 id="title">${i.realty.type} a ${i.realty.latitude}</h4>
                                        <h6 class="text-muted mb-2" id="street">${i.realty.longitude}</h6>
                                        <p id="description">${i.description}</p>
                                    </div>
                                    <div class="col-md-6 col-lg-3">
										<h4 id="cost">${i.cost}</h4>
										
										<c:choose>
                    						<c:when test = "${i.realty.max_holders > 0}">
												<!-- TODO current_holders / max_holders affittuari-->
												<h6 class="text-muted mb-2" id="max_holders">Massimo ${i.max_holders} affittuari.</h6>
											</c:when>
											<c:otherwise>
												<h6 class="text-muted mb-2" id="max_holders">Nessun limite di affittuari.</h6>
											</c:otherwise>
										</c:choose>

                                        <a href="#" id="mail">${i.realty.owner.email}</a>
                                        <p id="date">${i.publish_date}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </ul>

			</div>
			
			<div class="tab-pane fade" id="expenses" role="tabpanel" aria-labelledby="expenses-tab">
                
                <ul class="list-group">
                    <c:forEach items="${expensesList}" var="e">
                        <div class="card list-group-item list-group-item-action justify-content-between align-items-center" href="#">
                            <div class="card-body">
                                <div class="row">
                                    <p> Insert expense here </p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </ul>

            </div>
            
        </div>


	</body>

</html>