<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<link rel="stylesheet" href="../../../css/Header.css">
<div class="header-blue">
    <nav class="navbar navbar-light navbar-expand-md navigation-clean-search">
        <div class="container-fluid"><a class="navbar-brand" href="index">Rentaly</a><button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
            <div class="collapse navbar-collapse" id="navcol-1">
                <ul class="nav navbar-nav">
                    <li class="nav-item" role="presentation"><a class="nav-link" href="#">Chi siamo</a></li>
                </ul>

                <ul class="nav navbar-nav ml-auto"></ul>
                
                    <!-- ${pageContext.request.userPrincipal.name} -->
                <span>
                        
                </span>

                <c:choose>
                    <c:when test = '${pageContext.request.userPrincipal.authenticated == true}'>
                        <span class="navbar-text"> 
                            <a class="login" href="account">Account</a>
                        </span>
                        <a class="btn btn-light action-button" role="button" href="logout">Logout</a>
                    </c:when>
            
                    <c:otherwise>
                        <span class="navbar-text"> 
                            <a class="login" href="login">Accedi</a>
                        </span>
                        <a class="btn btn-light action-button" role="button" href="register">Registrati</a>
                    </c:otherwise>
                </c:choose>

                <!-- <span class="navbar-text"> 
                    <a class="login" href="login">Accedi</a>
                </span>
                <a class="btn btn-light action-button" role="button" href="register">Registrati</a> -->

                </div>
                </ul>
        </div>
    </nav>
</div>




<!--<form class="form-inline mr-auto" target="_self">
    <div class="form-group"><label for="search-field"><i class="fa fa-search"></i></label><input class="form-control search-field" type="search" id="search-field" name="search"></div>
</form> -->