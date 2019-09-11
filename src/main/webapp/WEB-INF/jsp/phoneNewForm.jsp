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
<h1>New Phone</h1>
<Table>
<form method="post" class="userSaveForm"action="phoneSave.html">
<tr>
<td>number</td><td><input type="text" name="number" value=""></td>
<input type="hidden" name="id" value="${user.getId()}">
</tr>
</Table>
<ul class="phoneList">
</ul>
<input type="submit" value="enroll">
</form>
</body>
</html>