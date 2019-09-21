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
<h1>Phone List</h1>
</div>
<hr>
<form method="get" action="/phoneList.html/0">
<input type="text" name="value" placeholder="핸드폰 번호 입력"><input type="submit" value="검색">
</form>
<table>
<tr>
<td>id</td><td>number</td><td>date</td><td>user</td>
</tr>
<c:choose>
<c:when test="${empty phones}">
<tr><td colspan="4">등록된 번호가 없습니다.</td></tr>
</c:when>
<c:otherwise>
	<c:forEach items="${phones.content}" var="e">
		<tr>
		<td>${e.seq}</td><td>${e.number}</td><td>${e.enrollDate}</td><td>${e.user.name}</td>
		</tr>
	</c:forEach>
</c:otherwise>
</c:choose>
</table>
<div id="page">
	<c:if test="${!phones.isFirst()}">
		<button type="button" onclick="location.href='./${page-2}?value=${value}'">&lt;</button>
	</c:if>
	<c:forEach begin="${startRange }" end="${endRange }" var="e">
         <button type="button" onclick="location.href='./${e-1}?value=${value}'">${e }</button>
    </c:forEach>
    <c:if test="${!phones.isLast()}">
    <button type="button" onclick="location.href='./${page}?value=${value}'">&gt;</button>
    </c:if>
</div>

<hr>
<div id="tail">
<a href="/">전체사용자 리스트</a>   <a href="/phoneList.html/0">전화기 리스트</a>
</div>
</body>
</html>