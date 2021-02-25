<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<ul class="list-group">
    <c:forEach items="${searches}" var="s">
        <div class="card list-group-item list-group-item-action justify-content-between align-items-center">
            <div class="card-body">
                <a href="/search?search-query=${s.city}&latitude=${s.latitude}&longitude=${s.longitude}&type=${type}&distance=${s.max_distance}&min_price=${s.min_price}&max-price=${s.max_price}">
                    <h3 class="row col-12 justify-content-center">${s.type} a massimo ${s.max_distance} km da ${s.city}</h3>
                    <h5 class="row col-12 justify-content-center">prezzo compreso tra €${s.min_price} ed €${s.max_price}</h5>
                </a>
                <div class="container justify-content-center">
                    <a id="unsave-search" class="btn btn-danger" href="javascript:;" onclick="setSelected(${s.id});">Elimina</a>
                </div>
            </div>
        </div>
    </c:forEach>
</ul>