<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Muestra productos</title>
  </head>
<body>
    <table border="1">
        <thead>
            <tr>
                <th>Identificador</th>
                <th>Nombre</th>
                <th>Cantidad</th>
                <th colspan=2>Acción</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${productos}" var="producto">
                <tr>
                    <td><c:out value="${producto.productoid}" /></td>
                    <td><c:out value="${producto.name}" /></td>
                    <td><c:out value="${producto.quantity}" /></td>
                    <td><a href="ProductoController?action=edit&productoId=<c:out value="${producto.productoid}"/>">Actualizar</a></td>
                    <td><a href="ProductoController?action=delete&productoId=<c:out value="${producto.productoid}"/>">Borrar</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <p><a href="ProductoController?action=insert">Añade productos</a></p>
</body>
</html>
