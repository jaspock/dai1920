package es.ua.dlsi.dai;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;

@WebServlet(name="cartServlet",urlPatterns={"/search"})
public class CartServlet extends HttpServlet {

    private static final String DB_DRIVER = "org.hsqldb.jdbc.JDBCDriver";
    // Sin 'shutdown=true', HSQLDB no cierra la base de datos al hacer con.close()
    private static final String DB_CONNECTION = "jdbc:hsqldb:carrito_db;shutdown=true";
    private static final String DB_USER = "user";
    private static final String DB_PASSWORD = "";

    public Connection connectToDatabase() throws ServletException, ClassNotFoundException, SQLException {
        /* Establecemos la conexión con la base de datos y rellenamos la tabla 'productos' con algunos
           valores si la tabla no existe. */

        Class.forName(DB_DRIVER);
        // Usamos una base de datos almacenada en ficheros de texto (no adecuada para producción):
        Connection con= DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);;

        ResultSet resultSet= null;
        DatabaseMetaData metadata = con.getMetaData();
        resultSet= metadata.getTables(null, null, "PRODUCTOS", null);
        // Si la tabla 'productos' no existe, la creamos y rellenamos.
        if(!resultSet.next()) {
            con.createStatement().executeUpdate("create table productos (name varchar(100),quantity int)");
            con.createStatement().executeUpdate("insert into productos (name, quantity) VALUES ('leche',2);");
            con.createStatement().executeUpdate("insert into productos (name, quantity) VALUES ('queso',1);");
            con.createStatement().executeUpdate("insert into productos (name, quantity) VALUES ('leche',5);");
            con.createStatement().executeUpdate("insert into productos (name, quantity) VALUES ('galletas',2);");
            con.createStatement().executeUpdate("insert into productos (name, quantity) VALUES ('tomates',12);");
        }            
        resultSet.close();

        return con;
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        String q = request.getParameter("q");
        String output = "";
        int error= HttpServletResponse.SC_OK;

        Connection con= null;
        try {
            con= connectToDatabase();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            error= error= HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            output = "<p>¡Error en la base de datos!</p>";
        }

        if (con != null) {
            try {
              output = selectRecordsFromTable(q,con);
            } catch (Exception e) {
                System.out.println(e.getMessage()); 
                error= error= HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
                output = "<p>¡Error en la base de datos!</p>";
            }
            try {
                con.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                error= HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
                output = "<p>¡Error en la base de datos!</p>";
            }
        }

        response.setStatus(error);
        PrintWriter out = response.getWriter();
        out.println("<!doctype html><html><head><meta charset='utf-8'><title>Carrito de la compra</title></head><body>");
        out.println(output);
        out.println("</body></html>");
    }


    private String selectRecordsFromTable(String q,Connection con) throws SQLException {

        PreparedStatement preparedStatement = null;
        String result = "";
        String selectSQL = "SELECT name, quantity FROM productos WHERE name = ?";

        preparedStatement = con.prepareStatement(selectSQL);
        preparedStatement.setString(1, q);
        // Ejecuta la instrucción select de SQL:
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            String name = rs.getString("name");
            String quantity = rs.getString("quantity");
            result += "<p>" + name;
            result += ": " + quantity + " unidades</p>";
        }

        preparedStatement.close();
        return (result.length() > 0 ? result : "<p>¡No se encontraron productos!</p>");
    }

}

