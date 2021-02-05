<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page errorPage="error.jsp" %> 
<!DOCTYPE html>
<html>
	<head>
        <%@include file="includes/import.jsp" %>
        <title>I miei Affitti - Rentaly</title>
	</head>

	<body>
		<%@include file="includes/header.jsp" %>

        <ul class="nav nav-tabs justify-content-center" id="tabList" role="tablist">
            
            <li class="nav-item">
                <a class="nav-link active" id="rents-tab" data-toggle="tab" href="#rents" role="tab" aria-controls="rents" aria-selected="true">Affitti</a>
            </li>
                
                
            <%-- <!--    <li class="nav-item">
                <a class="nav-link" id="expenses-tab" data-toggle="tab" href="#" role="tab" aria-controls="expenses" aria-selected="false">Spese</a>
            </li> --> --%>
        </ul>
        <div class="tab-content" id="tabContent">

            <div class="tab-pane fade show active" id="rents" role="tabpanel" aria-labelledby="rents-tab">
                <ul class="list-group">
                    <c:forEach items="${rents}" var="r">
                        <div class="card list-group-item list-group-item-action justify-content-between align-items-center">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-md-6">
                                        <h4 id="title">${r.realty.type} a ${r.realty.city}</h4>
                                        <h6 class="text-muted mb-2" id="street">${r.realty.address}</h6>
                                        <h6 class="text-muted mb-2" id="holder">Proprietario: ${r.realty.owner.email}</h6>
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