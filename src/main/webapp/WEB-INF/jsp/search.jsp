<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <%@include file="includes/import.jsp" %>
    <title>Rentaly</title>
    <link rel="stylesheet"  href="/css/Rentaly.css"/>
</head>

<body>
    <%@include file="includes/header.jsp" %>

    <script src="/js/search.js"></script>
    
    <div class="container search">
        <div class="row">
            <div class="input-group search-panel form-outline">
                <div class="col-sm-6">
                    <a class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                        <c:if test="${empty type}">
                            <c:set var="type" value="Qualsiasi"/>
                        </c:if>
                        <span id="search-concept">${type}</span>
                    </a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="javascript:;" id="apt">Appartamento</a></li>
                        <li><a href="javascript:;" id="mon">Monolocale</a></li>
                        <li><a href="javascript:;" id="bil">Bilocale</a></li>
                        <li><a href="javascript:;" id="vil">Villa indipendente</a></li>
                        <li><a href="javascript:;" id="sch">Villetta a schiera</a></li>
                        <li><a href="javascript:;" id="gar">Garage</a></li>
                        <li><a href="javascript:;" id="com">Spazio commerciale</a></li>
                        <li><a href="javascript:;" id="all">Qualsiasi</a></li>
                    </ul>
                </div>
        
                <div class="col-sm-6">
                    <span class="input-group">      
                        <input type="text" class="form-control" name="x" placeholder="Dove" id="search-query" value="${searchquery}">
                        <input type="hidden" id="search-query-hidden" value="${searchquery}">
                        <button class="btn btn-default" type="button" id="search-button">
                            <i class="fas fa-search"></i>
                        </button>
                    </span>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-6">
                <p>
                    Range di prezzo: <span id = "price"></span>
                </p>
                <div id = "price-slider"></div>
                <input type="hidden" id="min-price" value="${minprice}"/>
                <input type="hidden" id="max-price" value="${maxprice}"/>

                <input type=hidden id="global_maxprice" value="${global_maxprice}"/>
            </div>
            <div class="col-sm-6">
                <p>
                    Distanza massima: <span id = "distance-n">${distance} km</span>
                </p>
                <div id = "distance-slider"></div>
                <input type="hidden" id="distance" value="${distance}"/>
                <input type="hidden" id="max-distance" value="${global_maxdistance}"/>
            </div>
        </div>
    </div>
            
    <div class="content" id="content">
        <c:if test="${not empty both}">
            <%@include file="searchResult.jsp" %>
        </c:if>
    </div>
      

    <%@include file="includes/footer.jsp" %>
</body>
</html>