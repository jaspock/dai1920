package es.ua.dlsi.dai;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import javax.ws.rs.NotFoundException;

@Path("productos")
public class ProductosREST {

    private static List<Producto> carrito= new ArrayList<Producto>();
    static {
    	carrito.add(new Producto("galletas",2));
    	carrito.add(new Producto("tomates",12));
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Producto getProductos(@PathParam("id") String nombre) {
    	for (int i=0;i<carrito.size();i++) {
    		if(carrito.get(i).getNombre().equals(nombre)) {
    			return carrito.get(i);
    		}
    	}
    	throw new NotFoundException();
    }

    @GET
    @Produces("application/json")
    public List<Producto> getProducto() {
        return carrito;
    }

    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public Producto deleteProducto(@PathParam("id") String nombre) {
    	for (int i=0;i<carrito.size();i++) {
    		if(carrito.get(i).getNombre().equals(nombre)) {
    			Producto p= carrito.get(i);
    			carrito.remove(p);
    			return p;
    		}
    	}
    	throw new NotFoundException();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Producto postProducto(Producto p) {
    	for (int i=0;i<carrito.size();i++) {
    		if(carrito.get(i).getNombre().equals(p.getNombre())) {
          /* TODO: return ConflictException as described here:
             https://zcox.wordpress.com/2009/06/22/use-exceptions-to-send-error-responses-in-jersey/ */
    			return null;
    		}
    	}
    	carrito.add(p);
    	return p;
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Producto putProducto(Producto p) {
    	for (int i=0;i<carrito.size();i++) {
    		if(carrito.get(i).getNombre().equals(p.getNombre())) {
    			carrito.get(i).setUnidades(p.getUnidades());
    			return p;
    		}
    	}
    	throw new NotFoundException();
    }

}
