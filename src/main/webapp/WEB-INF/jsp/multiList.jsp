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
<h1>multiView11</h1>
</div>
<hr>
<table>
<c:choose>
	<c:when test="${empty phones}">
	<tr><td>id</td><td>name</td><td>date</td><td>number</td></tr>
		<c:forEach items="${users}" var="e">
			<tr><td>${e.id}</td><td>${e.name}</td><td>${e.enrollDate}</td><td>
				<c:forEach items="${e.phone}" var="p">
					${p.number} 
				</c:forEach>
				</td>
			</tr>		
		</c:forEach>
	</c:when>
	<c:otherwise>
		<tr><td>id</td><td>number</td><td>date</td><td>user name</td></tr>
		<c:forEach items="${phones}" var="e">
			<tr><td>${e.seq}</td><td>${e.number}</td><td>${e.enrollDate}</td><td>${e.user.name}</td>
			</tr>		
		</c:forEach>
	</c:otherwise>
</c:choose>
</table>
<hr>
<div id="tail">
<a href="/">전체사용자 리스트</a> <a href="/phoneList.html/0">전화기 리스트</a>
</div>
</body>
</html>