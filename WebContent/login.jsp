<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="controller.LoginServlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Log in</title>
</head>
<body>
<jsp:include page="header.jsp" />
<br>
<br>
<h2>Please login</h2>
<h5 id = "error"><% out.println(LoginServlet.getErrorMsg());  %></h5>
<form action="login" method="post">
Email: <input type="text" placeholder="enter email" name="email" required="required"></br>
Password: <input type="password" placeholder="enter password" name="pass" required="required"></br>
<input type="submit" value = "Login"></br>
</form>
<a href="register.jsp">Don`t have an account? Register here.</a>
</body>
</html>