<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- Ejemplo de declaración -->
<%!
 private static int counter = 0;
%>

<!-- Ejemplo de scriptlet -->
<%
request.setAttribute("pageTitle", "Contador");
request.setAttribute("pageHeading", "Contador de visitas");
%>

<jsp:include page="header.jsp"></jsp:include>

<!-- Ejemplo basado en el código de https://github.com/pH-7/SimpleJspWebsite -->

<%
  String visitor = Integer.toString(++counter);
%>

<!-- Ejemplo de expresión -->
<p>El número de visitas es <%= visitor + "." %></p>

<p>Prueba a recargar la página.</p>

<jsp:include page="footer.jsp"></jsp:include>

