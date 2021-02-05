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

    
    <div class="wrapper">
        <nav id="sidebar">
            <div class="sidebar-header">
                <h3>Benvenuto nomeutente</h3>
            </div>

            <ul class="list-unstyled components">
                <li class="active">
                    <a href="#">Il mio account</a>
                </li>
                <li>
                    <a href="#realtiesSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">I miei immobili</a>
                    <ul class="collapse list-unstyled" id="realtiesSubmenu">
                        <li>
                            <a href="#">Nuovo Immobile</a>
                        </li>
                        <li>
                            <a href="#">Immobili</a>
                        </li>
                        <li>
                            <a href="#">Bozze</a>
                        </li>
                        <li>
                            <a href="#">Affitti in corso</a>
                        </li>
                        <li>
                            <a href="#">Affitti terminati</a>
                        </li>
                        <li>
                            <a href="#">Spese</a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="#rentsSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">I miei affitti</a>
                    <ul class="collapse list-unstyled" id="rentsSubmenu">
                        <li>
                            <a href="#">Affitti in corso</a>
                        </li>
                        <li>
                            <a href="#">Affitti terminati</a>
                        </li>
                        <li>
                            <a href="#">Spese</a>
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
            </div>
	    </div> 
        

        
            
        <div id="content">
            <!-- Page Content -->
        </div>
    
    </div>       

</body>

</html>