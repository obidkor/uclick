<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<link href="/resources/css/userList.css" rel="stylesheet">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="title">
		<h1>oneview</h1>
	</div>
	<hr>
	<h2>User Inform</h2>
	<table>
		<tr>
			<td>id</td>
			<td>name</td>
			<td>date</td>
		</tr>
		<tr>
			<td>${user.getId()}</td>
			<td>${user.getName()}</td>
			<td><fmt:formatDate value="${user.getEnrollDate()}"
					pattern="YYYY.MM.dd HH:mm" /></td>
		</tr>
	</table>
	<hr>
	<h2>PhoneList</h2>
	<table>
		<tr>
			<td>id</td>
			<td>number</td>
			<td>date</td>
			<td>update</td>
			<td>delete</td>
		</tr>
		<c:choose>
			<c:when test="${empty user.getPhone()}">
				<tr>
					<td colspan="5">등록된 번호가 없음.</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${user.getPhone()}" var="e">
					<tr>
						<form method="post">
							<input type="hidden" name="seq" value="${e.seq}">
							<td>${e.seq}</td>
							<td>${e.number}</td>
							<td>
							<fmt:formatDate value="${e.enrollDate}" pattern="YYYY.MM.dd HH:mm" />
							</td>
							<td><input type="submit" formaction="./phoneEditForm.html"
								value="update this phone"></td>
							<td><input type=button value="delete this phone"
								onClick="location.href='./phoneDelete.html?uid=${user.getId()}&seq=${e.seq}'"></td>
						</form>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</table>
	<input type="button" value="add phone belong to this user"
		onclick="location.href='phoneNewForm.html?id=${user.getId()}'">
	<hr>
	<div id="tail">
		<a href="/">전체사용자 리스트</a> <a href="/phoneList.html/0">전화기 리스트</a>
	</div>
</body>
</html>