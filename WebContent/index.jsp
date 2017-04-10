<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <%@ page errorPage="errorPage.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TravelBook</title>
</head>
<body>

	<jsp:include page="header.jsp" />
	<br>
	<br>
	
<form action="quickSearch" method="get">
<input type="text" placeholder="Search TravelBook" name="searchFor" required></br>

<input type="submit" value = "Quick Search"></br>
</form>
</body>
</html>