<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="container">
	<h1>할 일 추가 폼 입니다.</h1>
	<form action="insert.do" method="post">
		<input type="text" name="content" placeholder="할 일 입력..."/>
		<button type="submit">추가</button>
	</form>
</div>
</body>
</html>