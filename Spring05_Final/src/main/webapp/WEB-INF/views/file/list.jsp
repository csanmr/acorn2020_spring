<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/file/list.jsp</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css" />
<style>
	.page-display a{
		text-decoration: none;
	}

	.page-display ul li{
		float: left; /* 가로 쌓이게 */
		list-style-type: none; /* disc 사라지게 */
		margin-right:10px; /* 오른쪽 마진 */
	}
	
	.page-display ul li.active{ /* li요소이면서 active클래스를 가지고 있는 요소 */
		text-decoration: underline;
		font-weight: bold;
	}
	.page-display ul li.active a{
		color: red;
	}
</style>
</head>
<body>
	<div class="container">
	<a href="private/upload_form.do">파일 업로드</a>
		<h1>파일 목록입니다.</h1>
		<table class="table table-striped table-sm">
			<thead class="thead-dark">
				<tr>
					<th>번호</th>
					<th>작성자</th>
					<th>제목</th>
					<th>파일명</th>
					<th>파일크기</th>
					<th>등록일</th>
					<th>삭제</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="tmp" items="${list }">
				<tr>
					<td>${tmp.num }</td>
					<td>${tmp.writer }</td>
					<td>${tmp.title }</td>
					<td><a href="download.do?num=${tmp.num }">${tmp.orgFileName }</a></td>
					<td> <fmt:formatNumber value="${tmp.fileSize }" pattern="#,###" />byte</td>
					<td>${tmp.regdate }</td>
					<td>
						<c:if test="${tmp.writer eq id }">
							<a href="private/delete.do?num=${tmp.num }">삭제</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
			<%-- 
				<%for(FileDto tmp:list){ %>
					<tr>
						<td><%=tmp.getNum() %></td>
						<td><%=tmp.getWriter() %></td>
						<td><%=tmp.getTitle() %></td>
						<td><a href="download.jsp?num=<%=tmp.getNum()%>"><%=tmp.getOrgFileName() %></a></td>
						<td><%=tmp.getFileSize() %></td>
						<td><%=tmp.getRegdate() %></td>
						<td>
							<%if(tmp.getWriter().equals(id)){ %>
							<a href="private/delete.jsp?num=<%=tmp.getNum()%>">삭제</a>
							<%} %>
						</td>
					</tr>
				<%} %>
			--%>
			</tbody>
		</table>
		<div class="page-display">
			<ul class="pagination pagination-sm">
			<c:if test="${startPageNum ne 1 }">
				<li class="page-item"><a class="page-link" href="list.do?pageNum=${startPageNum-1 }&condition=${condition }&keyword=${encodedK}">Prev</a></li>
			</c:if>
			<c:forEach var="i" begin="${startPageNum }" end="${endPageNum }" step="1">
				<c:choose>
					<c:when test="${i eq pagenum }">
						<li class="page-item active"><a class="page-link" href="list.do?pageNum=${i }&condition=${condition }&keyword=${encodedK}">${i }</a></li>
					</c:when>
					<c:otherwise>
						<li class="page-item"><a class="page-link" href="list.do?pageNum=${i }&condition=${condition }&keyword=${encodedK}">${i }</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${endPageNum lt totalPageCount }">
				<li class="page-item"><a class="page-link" href="list.do?PageNum=${endPageNum+1 }">Next</a></li>
			</c:if>
			
			<%-- 
			<%if(startPageNum!=1){ %>
				<li class="page-item"><a class="page-link" href="list.jsp?pageNum=<%=startPageNum-1%>&condition=<%=condition %>&keyword=<%=encodedK%>">Prev</a></li>
			<%} %>
			
				<%for(int i=startPageNum; i<=endPageNum; i++){ %>
					<%if(i==pageNum) { %>
					<li class="page-item active"><a class="page-link" href="list.jsp?pageNum=<%=i%>&condition=<%=condition %>&keyword=<%=encodedK%>"><%=i %></a></li>
					<%}else{ %>
					<li class="page-item"><a class="page-link" href="list.jsp?pageNum=<%=i%>&condition=<%=condition %>&keyword=<%=encodedK%>"><%=i %></a></li>
					<%} %>
				<%} %>
				
				<%if(endPageNum < totalPageCount){ %>
					<li class="page-item"><a class="page-link" href="list.jsp?PageNum=<%=endPageNum+1%>">Next</a></li>
				<%} %>
			--%>
			</ul>
		</div>
		<hr style="clear:left" />
		<form action="list.do" method="get">
			<label for="condition">검색조건</label>
			<select name="condition" id="condition">
				<option value="title_filename" <c:if test="${condition eq 'title_filename' }">selected</c:if>>제목+파일명</option>
				<option value="title" <c:if test="${condition eq 'title_title' }">selected</c:if>>제목</option>
				<option value="writer"<c:if test="${condition eq 'title_writer' }">selected</c:if>>작성자</option>
			
			<%-- 
				<option value="title_filename"<%if(condition.equals("title_filename")){ %>selected<%} %>>제목+파일명</option>
				<option value="title" <%if(condition.equals("title")){ %>selected<%} %>>제목</option>
				<option value="writer"<%if(condition.equals("writer")){ %>selected<%} %>>작성자</option>
			--%>	
			</select>
			<input value="${keyword }" type="text" name="keyword" placeholder="검색어..." />
			<button type="submit">검색</button>
		</form>
	</div>
</body>
</html>