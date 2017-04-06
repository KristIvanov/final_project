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
<form action="updateInfo" method="post">

Username: <input type="text" value="${ username }" name="newUsername"></br>
Email: <input type="text" value="${ email }" name="newEmail"></br>
First Name: <input type="text" value="${ firstname }" name="newFirstname"></br>
Last Name: <input type="text" value="${ lastname }" name="newLastname"></br>
Confirm Password: <input type="password" value="enter password" name="confirmPassword"></br>
<input type="submit" value = "UpdateInfo"></br>
</form>
</body>
</html>