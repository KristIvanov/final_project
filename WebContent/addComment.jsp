<%@page import="controller.AddCommentServlet"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<h5 id = "error"><% out.println(AddCommentServlet.getErrorMsg());  %></h5>
<form action="addComment" method="post">
Text:<textarea name="commentText" cols="40" rows="7" placeholder="Type your comment here.."  required></textarea></br>
</form>
</body>
</html>