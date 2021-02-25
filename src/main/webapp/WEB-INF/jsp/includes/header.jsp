<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" href="../../../css/Header.css">
<div class="header">
    <nav class="navbar navbar-light navbar-expand-md navigation-clean-search">
        <div class="container-fluid">

            <c:if test="${param.account == 1}">
                <button type="button" id="sidebarCollapse" class="btn btn-dark d-md-none">
                    <i class="fas fa-align-justify"></i>
                </button>
                <script src="/js/sidebar.js"></script>
            </c:if>

            <a class="navbar-brand" href="/index">Rentaly</a>

            <div class="justify-content-right" id="navcol-1">
                <a class="btn login" role="button" href="/search"> Cerca <i class="fas fa-search"></i> </a>
                <c:choose>
                    <c:when test = '${pageContext.request.userPrincipal.authenticated == true}'>
                        <span class="navbar-text"> 
                            <a class="btn login" href="/account"><i class="fas fa-user"></i> ${pageContext.request.userPrincipal.name}</a>
                        </span>
                        <form class="btn" method="post" action="/logout">
                            <sec:csrfInput />
                            <button class="btn-light action-button" type="submit"><i class="fas fa-sign-out-alt"></i></button>
                        </form>
                    </c:when>
            
                    <c:otherwise>
                        <span class="navbar-text"> 
                            <a class="btn login" href="/login">Accedi</a>
                        </span>
                        <a class="btn btn-light action-button" role="button" href="/register">Registrati</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </nav>
</div>