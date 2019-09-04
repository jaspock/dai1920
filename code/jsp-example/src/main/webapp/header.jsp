<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <title>${pageTitle} - ejemplo de página web escrita con JSP</title>
  </head>
<body>

<% if(request.getAttribute("pageHeading") != null) { %>
  <h1>${pageHeading}</h1>
<% } %>

<div>

