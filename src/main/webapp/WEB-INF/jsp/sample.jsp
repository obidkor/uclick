<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
Samples : ${samples } <br />

Sample.name : ${sample.name } <br />
findSampleByName : ${findSampleByName} <br />
findSampleByName.id : ${findSampleByName.get(0).id} <br />
findSampleByName.name : ${findSampleByName.get(0).name} <br />
findSampleByName.number : ${findSampleByName.get(0).number} <br />

</body>
</html>