package es.ua.dlsi.dai;
 
// POJO para los productos del carrito
public class Producto {
	private String nombre;
	private int unidades;
	
	public Producto () {
		nombre= "";
		unidades= 0;
	}
	
	public Producto(String nombre,int cantidad) {
		this.nombre= nombre;
		this.unidades= cantidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int cantidad) {
		this.unidades = cantidad;
	}
	

}
