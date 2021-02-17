<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <%@include file="includes/import.jsp" %>
    <title>Rentaly</title>
    <link rel="stylesheet"  href="../../css/Rentaly.css"/>
</head>

<body>
    <%@include file="includes/header.jsp" %>

    <div class="container">
    <div class="row">    
      <div class="col-xs-8 col-xs-offset-2">
      <div class="input-group">
              <div class="input-group-btn search-panel">
                  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    <span id="search_concept">Cosa</span> <span class="caret"></span>
                  </button>
                  <ul class="dropdown-menu" role="menu">
                    <li><a href="#apt">Appartamento</a></li>
                    <li><a href="#mon">Monolocale</a></li>
                    <li><a href="#bil">Bilocale</a></li>
                    <li><a href="#vil">Villa indipendente</a></li>
                    <li><a href="#sch">Villetta a schiera</a></li>
                    <li><a href="#gar">Garage</a></li>
                    <li><a href="#com">Spazio commerciale</a></li>
                    <li class="divider"></li>
                    <li><a href="#all">Qualsiasi</a></li>
                  </ul>
              </div>
              <input type="hidden" name="search_param" value="all" id="search_param">         
              <input type="text" class="form-control" name="x" placeholder="Dove">
              <span class="input-group-btn">
                  <button class="btn btn-default" type="button"><span class="glyphicon glyphicon-search"></span></button>
              </span>
          </div>
      </div>
    </div>
      <script>
        //https://bootsnipp.com/snippets/9Avx
        $(document).ready(function(e){
    $('.search-panel .dropdown-menu').find('a').click(function(e) {
		e.preventDefault();
		var param = $(this).attr("href").replace("#","");
		var concept = $(this).text();
		$('.search-panel span#search_concept').text(concept);
		$('.input-group #search_param').val(param);
	});
});
      </script>
</div>
  

              
      


</body>
</html>