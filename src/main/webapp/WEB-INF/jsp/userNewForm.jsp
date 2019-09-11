<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link href="/resources/css/NewFile.css" rel="stylesheet">
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">

</style>
</head>
<body>
<h1 class="a">New User</h1>
<form class="userSaveForm" method="post" action="userSave.html">
<Table>
<tr>
<td>name</td><td><input type="text"  name="name" value=""></td>
</tr>
</Table>
<hr>
<h2>전화번호</h2>
<ul class="phoneList"> 
</ul>
<input type="button" class="submitBtn"value="enroll">
</form>
<script src="<c:url value="/resources/js/userNewForm.js"/>"></script>
</body>
</html>