<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ page import="java.util.List"%> --%>
<!DOCTYPE html>
<html>
	<head>
		<title>A Raffa piace</title>

	</head>
<%-- 	<c:if test="${not empty listUsers}"> --%>
	<p>provprov</p>
	<ul>
	<c:forEach items="${listUsers}" var="user">
		<li>Utente: <c:out value="${user.username}"/></li>
	</c:forEach>
	</ul>
<%-- 	</c:if> --%>
</html>