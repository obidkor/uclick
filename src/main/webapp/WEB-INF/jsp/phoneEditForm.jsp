<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link href="/resources/css/userList.css" rel="stylesheet">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="title">
		<h1>update Phone</h1>
	</div>
	<hr>
	<Table>
		<form method="post" action="phoneUpdate.html">
			<input type="hidden" name="seq" value="${thisPhone.seq}">
			<tr>
				<td>number</td>
				<td><input type="text" name="number"
					value="${thisPhone.number}"></td>
			</tr>
	</Table>
	<input type="submit" value="update">
	</form>
</body>
</html>