<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>

<html>
    <head>
		<%@include file="includes/import.jsp" %>
			<title>Rentaly</title>
	</head>
    <body>

    <%@include file="includes/header.jsp" %>
    <form method='POST' enctype='multipart/form-data' action='/prova/upload'>
        <sec:csrfInput />

        <input type='file' id='file' name='file' multiple>
        <input type='submit'>
    </form>
    </body>
</html>";