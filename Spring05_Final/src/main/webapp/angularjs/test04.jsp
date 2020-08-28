<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/angularjs/test04.jsp</title>
<link rel="stylesheet" href="../resources/css/bootstrap.css" />
<!-- angularjs 로딩 시키기 -->
<script src="${pageContext.request.contextPath}/resources/js/angular.min.js"></script>
</head>
<!-- body에서 일어나는 일은 angular로 관리를 하겠다라는 의미 -->
<body ng-app>
<div class="container">
	<h1>form 검증</h1>
	<!-- novalidate는 웹브라우저가 자체 유효성 검증을 하지 못하도록 하는 설정 -->
	<form name="myForm" action="insert.jsp" method="post" novalidate>
		<!-- 입력한 문자열을 id라는 모델명으로 관리, 반드시 입력해야한다. -->
		<input type="text" name="id" ng-model="id" ng-required="true" />
		<p ng-show="myForm.id.$invalid && myForm.id.$dirty" style="color:red;">아이디는 반드시 입력 해라!</p>
		<p ng-show="myForm.id.$valid || myForm.id.$pristine" style="color:red;">아이디는 반드시 입력 해라2!</p>
		<button type="submit" ng-disabled="myForm.id.$invalid">제출</button>
	</form>
	<p>입력한 아이디 : <strong>{{id}}</strong></p>
	<p>아이디 유효한지 여부 : <strong>{{myForm.id.$valid}}</strong></p>
	<p>아이디 유효하지 않은지 여부 : <strong>{{myForm.id.$invalid}}</strong></p>
	<p>아이디가 청결(순결)한지 여부 : <strong>{{myForm.id.$pristine}}</strong></p>
	<p>아이디가 더렵혀졌는지 여부 : <strong>{{myForm.id.$dirty}}</strong></p>
</div>
<a href="test05.jsp">다음예제</a>
</body>
</html>