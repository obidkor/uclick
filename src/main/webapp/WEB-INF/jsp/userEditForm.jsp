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
<h1>New User</h1>
</div>
<Table>
<form method="post" action="userSave.html">
<tr>
<input type = "hidden" name="id" value="${thisUser.getId()}">
<td>name</td><td><input type="text" name="name" value="${thisUser.getName()}"></td>
</tr>
</Table>
<input type="submit" value="update">
</form>
</body>
</html>