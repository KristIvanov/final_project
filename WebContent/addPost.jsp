<%@page import="controller.AddPostServlet"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="controller.RegisterServlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Add new post</title>
</head>
<body>
<jsp:include page="header.jsp" />
<br>
<br>
<c:if test="${sessionScope.username !=null }">
<h2>Please add a new post</h2>
<h5 id = "error"><% out.println(AddPostServlet.getErrorMsg());  %></h5>
<form action="addPost" method="post" enctype="multipart/form-data">
Post name: <input type="text" value="${ postname }" name="postname" required></br>
Post description:<textarea name="postdescription" value="${postdescription }" cols="89" rows="10" placeholder="Add description"  required></textarea></br>
Destination name: <input type="text" value="${ destinationname }" name="destinationname" required></br>
Longitude: <input type="text" value="${ longitude }" name="longitude" required></br>
latitude: <input type="text" value="${latitude}" name="latitude" required></br>
Enter key words separated by spaces: <input type="text" value="${ hashtags }" name="hashtags" ></br>
Categories: 
<select name = "category">
                <option value="beaches">Beaches</option>
                <option value="nature">Nature</option>
                <option value="mountain">Mountain</option>
                <option value="forest">Forest</option>
                </select>
  <label for="photo"> Select pictures :  </label>
            <input type="file" name="photo" size="50" placeholder="Upload Your Image" required/><br><br>
<input type="submit" value = "Add post"></br>
</form>
</c:if>
<c:if test="${sessionScope.username ==null }">
<%session.setAttribute("url", "addPost.jsp"); %>
<jsp:forward page="login.jsp"></jsp:forward>
</c:if>
</body>
</html>