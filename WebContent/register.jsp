<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="controller.RegisterServlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
</head>
<body>
<jsp:include page="header.jsp" />
<br>
<br>

<h2>Please register a new account</h2>
<h5 id = "error"><% out.println(RegisterServlet.getErrorMsg());  %></h5>
<form action="register" method="post">
Username: <input type="text" placeholder="enter username" name="username"></br>
Email: <input type="text" placeholder="enter email" name="email"></br>
First Name: <input type="text" placeholder="enter first name" name="firstname"></br>
Family Name: <input type="text" placeholder="enter family name" name="lastname"></br>
Password: <input type="password" placeholder="enter password" name="password"></br>
Confirm password: <input type="password" placeholder="re-enter password" name="reenteredPass"></br>
<input type="submit" value = "Register"></br>
</form>
<a href="login.jsp">Already a registered user? Login here.</a>
</body>
</html>