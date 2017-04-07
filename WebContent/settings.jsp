<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="header.jsp" />
<br>
<br>
<c:if test="${sessionScope.username !=null }">
<a href="updateInfo.jsp">Update your profile settings</a> <br>
<a href="addPost.jsp">Add a new post</a>

<br>
<br>
<form action="logout" method = "post"> <input type = "submit" value= "Log out" > </form>
</c:if>
<c:if test="${sessionScope.username ==null }">
<%session.setAttribute("url", "settings.jsp"); %>
<jsp:forward page="login.jsp"></jsp:forward>
</c:if>
</body>
</html>