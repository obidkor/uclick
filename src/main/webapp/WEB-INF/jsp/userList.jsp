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
<h1>전체 데이터</h1>
<hr>
<form method="post" action="/0">
<select name="search">
    <option value="1">번호 검색</option>
    <option value="2">이름 검색</option>
</select>
<input type="text" name="value" placeholder="검색어 입력"><input type="submit" value="검색">
</form>
<table>
<tr>
	<td><p align=center>id</p></td>
	<td><p align=center>name</p></td>
	<td><p align=center>date</p></td>
	<td><p align=center>update</p></td>
	<td><p align=center>delete</p></td>
</tr>
<c:choose>
	<c:when test="${search==1}">
		<c:forEach items="${users.content}" var="e">
		<tr>
			<form method="post">
			<input type="hidden" name="id" value="${e.id}">
			<td><a href="oneUser.html?id=${e.id}">${e.id}</a></td>
			<td>${e.name}</td>
			<td>${e.enrollDate}</td>
			<td><input type="submit" formaction="userEditForm.html" value="update this user"></td>
			<td><input type=button  value="delete" onClick="location.href='userDelete.html?id=${e.id}'"></td>
			</form>	
		</tr>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<c:forEach items="${users.content}" var="e">
		<tr>
			<form method="post">
			<input type="hidden" name="id" value="${e.id}">
			<td><a href="./oneUser.html?id=${e.id}">${e.id}</a></td>
			<td>${e.name}</td>
			<td>${e.enrollDate}</td>
			<td><input type="submit" formaction="./userEditForm.html" value="update this user"></td>
			<td><input type=button  value="delete" onClick="location.href='userDelete.html?id=${e.id}'"></td>
			</form>	
		</tr>
		</c:forEach>
	</c:otherwise>
</c:choose>
</table>
	<c:if test="${!users.isFirst()}">
		<button type="button" onclick="location.href='./${page-2}'">&lt;</button>
	</c:if>
	<c:forEach begin="${startRange }" end="${endRange }" var="e">
         <button type="button" onclick="location.href='./${e-1 }'">${e }</button>
    </c:forEach>
    <c:if test="${!users.isLast()}">
    <button type="button" onclick="location.href='./${page}'">&gt;</button>
    </c:if>
<input type="button" value="사용자 추가" onclick="location.href='userNewForm.html'">
현재페이지 : ${page}
시작페이지 : ${startRange}
끝페이이지 : ${endRange}

<hr>
<a href="/">전체사용자 리스트</a> <a href="/phoneList.html/0">전화기 리스트</a>
</body>
</html>