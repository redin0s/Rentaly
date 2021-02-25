<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:forEach items="${images}" var="img" varStatus="loop">
    <div class="carousel-item <c:if test='${loop.index == 0}'>active</c:if>">
        <img class="d-block w-100" src="/${img}" alt="First slide">
    </div>
</c:forEach>