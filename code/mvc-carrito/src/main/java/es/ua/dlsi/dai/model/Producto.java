package es.ua.dlsi.dai.model;

 /* A plain old Java object (POJO) is an ordinary Java object,
    not bound by any special restriction and not requiring any class path. */

public class Producto {
	private int productoid;
	private String name;
	private int quantity;

	/* Los nombres de los getters y setters han de ser {get,set} seguidos del nombre del atributo
	 * con solo la primera letra en mayúscula. El getter getProductoid , por ejemplo, es invocado
	 * mediante reflexión cuando en listProducto.jsp hacemos ${producto.productoid}, por lo que
	 * es importante no cambiar el formato del nombre del método.
	 */

	public int getProductoid() {
		return productoid;
	}
	public void setProductoid(int id) {
		this.productoid = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "Producto [productoid=" + productoid + ", name=" + name + ", quantity=" + quantity + "]";
	}

}
