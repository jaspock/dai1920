package es.ua.dlsi.dai.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import es.ua.dlsi.dai.dao.ProductoDao;
import es.ua.dlsi.dai.model.Producto;

@WebServlet(name="productoController",urlPatterns={"/ProductoController"})
public class ProductoController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/producto.jsp";
    private static String LIST_PRODUCT = "/listProducto.jsp";
    private ProductoDao dao;

    public ProductoController() {
        super();
        dao = new ProductoDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward="";
        String action = request.getParameter("action");

        /*
         * Los servicios web implementados no son REST porque hemos usado formularios de HTML
         * para las peticiones que solo soportan GET y POST: https://stackoverflow.com/a/166501
         */

        if (action.equalsIgnoreCase("delete")){
            int productoId = Integer.parseInt(request.getParameter("productoId"));
            dao.deleteProducto(productoId);
            forward = LIST_PRODUCT;
            request.setAttribute("productos", dao.getAllProductos());
        } else if (action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_EDIT;
            int productoId = Integer.parseInt(request.getParameter("productoId"));
            Producto producto = dao.getProductoById(productoId);
            request.setAttribute("producto", producto);
        } else if (action.equalsIgnoreCase("listProducto")){
            forward = LIST_PRODUCT;
            request.setAttribute("productos", dao.getAllProductos());
        } else {
            forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Producto producto = new Producto();
        producto.setName(request.getParameter("name"));

        try {
        	producto.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String productoid = request.getParameter("productoid");
        if(productoid == null || productoid.isEmpty())
        {
            dao.addProducto(producto);
        }
        else
        {
            producto.setProductoid(Integer.parseInt(productoid));
            dao.updateProducto(producto);
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_PRODUCT);
        request.setAttribute("productos", dao.getAllProductos());
        view.forward(request, response);
    }
}
