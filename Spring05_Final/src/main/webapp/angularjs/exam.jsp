<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html ng-app="acornApp">
<head>
<meta charset="UTF-8">
<title>exam</title>
<script src="${pageContext.request.contextPath}/resources/js/angular.min.js"></script>
<script>
var acornApp=angular.module("acornApp",[]);
acornApp.controller("acornCtrl", function($scope){
	$scope.btnClick=function(){
		
		$scope.text="버튼을 눌렀네요?";
	};
});
</script>
</head>
<body ng-controller="acornCtrl">
<button ng-click="btnClick()">눌러주세요</button>
<p>{{text}}</p>
</body>
</html>