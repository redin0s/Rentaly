<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<ul class="list-group">
    <c:forEach items="${searches}" var="s">
        <div class="card list-group-item list-group-item-action justify-content-between align-items-center">
            <div class="card-body">
                <a class="row" href="#">
                    <h3>${s.type} a massimo ${s.max_distance} km da ${s.city}</h3>
                    <br>
                    <h5>prezzo compreso tra €${s.min_price} ed €${s.max_price}</h5>
                </a>
            </div>
        </div>
    </c:forEach>
</ul>