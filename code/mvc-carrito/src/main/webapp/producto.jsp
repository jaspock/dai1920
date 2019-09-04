<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Nuevo producto</title>
  </head>
<body>
    <form method="POST" action='ProductoController' name="frmAddProduct">
        <p>
          <label for="identificador">Identificador:</label>
          <input type="text" readonly="readonly" name="productoid" id="identificador"
            value="<c:out value="${producto.productoid}" />" />
        </p>
        <p>
          <label for="nombre">Nombre:</label>
          <input type="text" name="name" id="nombre"
            value="<c:out value="${producto.name}" />" />
        </p>
        <p>
          <label for="cantidad">Cantidad:</label>
          <input type="text" name="quantity" id="cantidad"
            value="<c:out value="${producto.quantity}" />" />
        </p>
        <p>
          <button type="submit">Enviar</button>
        </p>
    </form>
</body>
</html>
