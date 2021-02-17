<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>

<head>
    <%@include file="includes/import.jsp" %>
    <title>Account - Rentaly</title>
</head>

<body>

    <jsp:include page="includes/header.jsp">
            <jsp:param name="account" value="1"/>
    </jsp:include> 

    <c:if test="${active eq false}">
        <div class="alert alert-warning alert-dismissible fade show" role="alert">
            <strong>Il tuo account non è ancora stato confermato.</strong>
            <p>Controlla la tua casella di posta elettronica, o in alternativa, <a>possiamo inviarti una nuova email.</a></p> <!-- TODO AJAX REQUEST FOR EMAIL -->
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
        </div>
    </c:if>
    
    <div class="wrapper">
        <nav id="sidebar">
            <div class="sidebar-header">
                <h6>Benvenuto, ${pageContext.request.userPrincipal.name}!</h6>
            </div>

            <ul class="list-unstyled components">
                <li class="active">
                    <a href="#">Il mio account</a>
                </li>
                <li>
                    <a href="#realtiesSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">I miei immobili</a>
                    <ul class="collapse list-unstyled" id="realtiesSubmenu">
                        <li>
                            <c:choose>
                                <c:when test="${active eq false}">
                                    <a data-toggle="modal" data-target="#realtyError">Nuovo Immobile</a>
                                    <div class="modal fade" id="realtyError" tabindex="-1" role="dialog" aria-labelledby="realtyErrorLabel" aria-hidden="true">
                                        <div class="modal-dialog" role="document">
                                          <div class="modal-content">
                                            <div class="modal-header">
                                              <h5 class="modal-title" id="realtyErrorLabel">Errore!</h5>
                                              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                              </button>
                                            </div>
                                            <div class="modal-body">
                                                <strong>Il tuo account non è ancora stato confermato.</strong>
                                                <p>Controlla la tua casella di posta elettronica, o in alternativa, <a>possiamo inviarti una nuova email.</a></p>
                                            </div>
                                          </div>
                                        </div>
                                      </div>
                                </c:when>
                                <c:otherwise>
                                    <a href="realty/new">Nuovo Immobile</a>
                                </c:otherwise>
                            </c:choose>
                        </li>
                        <li>
                            <a id="realties">Immobili</a>
                        </li>
                        <li>
                            <a id="drafts">Bozze</a>
                        </li>
                        <li>
                            <a id="own-ongoing">Affitti in corso</a>
                        </li>
                        <li>
                            <a id="own-ended">Affitti terminati</a>
                        </li>
                        <li>
                            <a id="own-expenses">Spese</a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="#rentsSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">I miei affitti</a>
                    <ul class="collapse list-unstyled" id="rentsSubmenu">
                        <li>
                            <a href="rents-ongoing">Affitti in corso</a>
                        </li>
                        <li>
                            <a href="rents-ended">Affitti terminati</a>
                        </li>
                        <li>
                            <a href="rents-expenses">Spese</a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="#">Segnala un problema</a>
                </li>
            </ul>

        </nav>
        
        <div id="overlay">
            <div id="dismiss">
                <i class="fas fa-arrow-left"></i>
                <h1 class="justify-content-center">Questa è una prova e io mi drogo.</h1>
            </div>
	    </div> 
        

        
            
        <div id="content">
            <!-- Page Content -->
        </div>
    
    </div>       

</body>

</html>