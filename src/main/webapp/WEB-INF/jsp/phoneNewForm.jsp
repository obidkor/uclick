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
		<h1>New Phone</h1>
	</div>
	<form method="post" class="userSaveForm" action="phoneSave.html">
		<table class="phoneList">
			<tr>
				<td>number</td>
				<td><input type="text" name="number" value=""></td>
				<input type="hidden" name="id" value="${user.getId()}">
			</tr>
		</table>
		<button type="button" class="addBtn">전화기 추가</button>
		<input type="button" class="submitBtn" value="enroll">
	</form>
	<script src="<c:url value="/resources/js/userNewForm.js"/>"></script>
</body>
</html>