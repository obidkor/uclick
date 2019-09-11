<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Insert title here</title>
</head>
<body>
<h1>New User</h1>
<form class="userSaveForm" method="post" action="userSave.html">
<Table>
<tr>
<td>name</td><td><input type="text" name="name" value=""></td>
</tr>
</Table>
<hr>
<h2>전화번호aaaaaa</h2>
<ul class="phoneList"> 
</ul>
<input type="button" class="submitBtn"value="enroll">
</form>
<script src="<c:url value='/resources/js/userNewForm.js'/>"></script>
<script type="text/javascript" th:src="@{/resources/js/a.js}" />
</body>
</html>