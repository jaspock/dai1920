package es.ua.dlsi.dai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import es.ua.dlsi.dai.model.Producto;
import es.ua.dlsi.dai.util.DbUtil;

/* A data access object (DAO) is an object that provides an abstract interface to some type of database. */

public class ProductoDao {

    private Connection connection;

    public ProductoDao() {
        connection = DbUtil.getConnection();
    }

    public void addProducto(Producto producto) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into productos(name,quantity) values (?, ?)");
            // Parameters start with 1
            preparedStatement.setString(1, producto.getName());
            preparedStatement.setInt(2, producto.getQuantity());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProducto(int productoId) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from productos where productoid=?");
            // Parameters start with 1
            preparedStatement.setInt(1, productoId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProducto(Producto producto) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update productos set name=?, quantity=?" +
                            "where productoid=?");
            // Parameters start with 1
            preparedStatement.setString(1, producto.getName());
            preparedStatement.setInt(2, producto.getQuantity());
            preparedStatement.setInt(3, producto.getProductoid());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Producto> getAllProductos() {
        List<Producto> productos = new ArrayList<Producto>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from productos");
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setProductoid(rs.getInt("productoid"));
                producto.setName(rs.getString("name"));
                producto.setQuantity(rs.getInt("quantity"));
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    public Producto getProductoById(int productoId) {
        Producto producto = new Producto();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from productos where productoid=?");
            preparedStatement.setInt(1, productoId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                producto.setProductoid(rs.getInt("productoid"));
                producto.setName(rs.getString("name"));
                producto.setQuantity(rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return producto;
    }
}
