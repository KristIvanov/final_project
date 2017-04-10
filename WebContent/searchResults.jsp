<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Results</title>
</head>
<body>
<h1>Posts tagged with: <c:out value="${sessionScope.searchFor}"></c:out></h1>
<c:if test="${sessionScope.resultsByTag==null || sessionScope.resultsByTag.isEmpty()}">
	<br><br><h2 align="center">No results</h2>
</c:if>

<c:forEach var="post" items="${sessionScope.resultsByTag}">

	<!-- linka kym profile page na user-a nqmam ideq dali trqbva da e taka -->
	<a href = "?userviewed= <c:out value="${post.author.username}"/> " >${ post.author.username }</a><br>
	
	
	<c:out value="${ post.author.first_name }"></c:out>
             <p>first name</p>
    <c:out value="${ post.author.last_name }"></c:out>
             <p>last name</p><br>
             
			<c:out value="${ post.date }"></c:out>
             <p>date</p><br>
			<c:out value="${ post.category }"></c:out>
             <p>category</p><br>
	
			<c:out value="${ post.postName }"></c:out>
             <p>name</p><br>
	
			<c:out value="${ post.description }"></c:out>
             <p>desc</p><br>
            
			<c:out value="${ post.likers.size }"></c:out>
             <p>likes</p><br>
           
             <c:forEach var="Comment" items="${post.comments}"> 
					<c:out value="${ comment }"></c:out><br>
                	<p>comments</p><br>
             </c:forEach>
                
</c:forEach>

</body>
</html>